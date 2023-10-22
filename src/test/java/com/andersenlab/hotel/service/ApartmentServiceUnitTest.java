package com.andersenlab.hotel.service;

import com.andersenlab.hotel.model.Apartment;
import com.andersenlab.hotel.model.ApartmentStatus;
import com.andersenlab.hotel.repository.ApartmentSort;
import com.andersenlab.hotel.repository.inmemory.InMemoryApartmentRepository;
import com.andersenlab.hotel.service.impl.ApartmentService;
import com.andersenlab.hotel.usecase.exception.ApartmentNotfoundException;
import com.andersenlab.hotel.usecase.exception.ApartmentWithSameIdExists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ApartmentServiceUnitTest {
    private ApartmentService target;
    private InMemoryApartmentRepository repo;

    private Apartment apartmentAvailable;
    private Apartment apartmentUnavailable;
    private UUID apartmentAvailableId;
    private UUID apartmentUnavailableId;
    private UUID id;

    @BeforeEach
    void setUp() {
        repo = mock(InMemoryApartmentRepository.class);
        target = new ApartmentService(repo);
        apartmentUnavailableId = UUID.fromString("00000000-0000-0000-0000-000000000001");
        apartmentAvailableId = UUID.fromString("00000000-0000-0000-0000-000000000002");
        id = UUID.fromString("00000000-0000-0000-0000-abc000000005");
        apartmentAvailable = new Apartment(apartmentAvailableId, BigDecimal.ONE, BigInteger.ONE,
                true, ApartmentStatus.AVAILABLE);

        apartmentUnavailable = new Apartment(apartmentUnavailableId, BigDecimal.TWO, BigInteger.TWO,
                false, ApartmentStatus.RESERVED);
    }

    @Test
    void delete_NotExistingId_ShouldThrowsApartmentNotFoundException() {
        when(repo.has(any(UUID.class))).thenReturn(false);

        assertThatThrownBy(() ->
                target.delete(id)
        ).isInstanceOf(ApartmentNotfoundException.class);
    }

    @Test
    void delete_ExistingId_ShouldCallRepositoryMethod() {
        when(repo.has(any(UUID.class))).thenReturn(true);

        target.delete(id);

        verify(repo).delete(any());
    }

    @Test
    void has_AnyUuid_ShouldCallRepositoryMethod() {
        target.has(id);

        verify(repo).has(any());
    }

    @Test
    void getById_ExistingId_ShouldCallRepositoryMethod() {
        when(repo.getById(apartmentAvailableId)).thenReturn(Optional.of(apartmentAvailable));

        target.getById(apartmentAvailable.getId());

        verify(repo).getById(apartmentAvailable.getId());
    }

    @Test
    void getById_NotExistingId_ShouldThrowsApartmentNotfoundException() {
        when(repo.getById(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                target.getById(id)
        ).isInstanceOf(ApartmentNotfoundException.class);
        verify(repo).getById(id);
    }

    @Test
    void adjust_ExistingIdAndAvailable_ShouldCallRepositoryMethods_GetByIdAndUpdate() {
        final BigDecimal newPrice = BigDecimal.ONE;
        ArgumentCaptor<Apartment> captor = ArgumentCaptor.forClass(Apartment.class);
        when(repo.getById(apartmentAvailableId)).thenReturn(Optional.of(apartmentAvailable));

        target.adjust(apartmentAvailable.getId(), newPrice);

        verify(repo).getById(apartmentAvailable.getId());
        verify(repo).update(captor.capture());
        assertThat(captor.getValue().getId()).isEqualTo(apartmentAvailable.getId());
        assertThat(captor.getValue().getPrice()).isEqualTo(newPrice);
        assertThat(captor.getValue().getCapacity()).isEqualTo(apartmentAvailable.getCapacity());
        assertThat(captor.getValue().getStatus()).isEqualTo(apartmentAvailable.getStatus());
        assertThat(captor.getValue().isAvailability()).isEqualTo(apartmentAvailable.isAvailability());
    }

    @Test
    void adjust_ExistingIdAndUnavailable_ShouldCallRepositoryMethod_ThenThrowsApartmentNotfoundException() {
        final BigDecimal newPrice = BigDecimal.ONE;

        when(repo.getById(apartmentUnavailableId)).thenReturn(Optional.of(apartmentUnavailable));

        assertThatThrownBy(() ->
                target.adjust(apartmentUnavailableId, newPrice)
        ).isInstanceOf(ApartmentNotfoundException.class);
        verify(repo).getById(apartmentUnavailable.getId());
    }

    @Test
    void adjust_NotExistingId_ShouldCallRepositoryMethod_ThenThrowsApartmentNotfoundException(){
        final BigDecimal newPrice = BigDecimal.ONE;

        when(repo.getById(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                target.adjust(apartmentUnavailableId, newPrice)
        ).isInstanceOf(ApartmentNotfoundException.class);
        verify(repo).getById(apartmentUnavailable.getId());
    }

    @Test
    void list_WithValidSortParam_ShouldCallRepositoryMethod() {
        ArgumentCaptor<ApartmentSort> captor = ArgumentCaptor.forClass(ApartmentSort.class);
        ApartmentSort sort = ApartmentSort.ID;

        target.list(sort);

        verify(repo).findAllSorted(captor.capture());
        assertThat(captor.getValue()).isEqualTo(sort);
    }

    @Test
    void save_WithNonExistingId_ShouldCallRepositoryMethods() {
        ArgumentCaptor<Apartment> captor = ArgumentCaptor.forClass(Apartment.class);
        when(repo.has(any())).thenReturn(false);

        target.save(apartmentAvailable);

        verify(repo).has(apartmentAvailable.getId());
        verify(repo).save(captor.capture());
        assertThat(captor.getValue()).isEqualTo(apartmentAvailable);
    }

    @Test
    void save_WithExistingId_ShouldThrowApartmentWithSameIdExists() {
        when(repo.has(any())).thenReturn(true);

        assertThatThrownBy(() ->
                target.save(apartmentAvailable)
        ).isInstanceOf(ApartmentWithSameIdExists.class);
        verify(repo).has(apartmentAvailable.getId());
    }

    @Test
    void update_WithExistingId_ShouldCallRepositoryMethods() {
        ArgumentCaptor<Apartment> captor = ArgumentCaptor.forClass(Apartment.class);
        when(repo.has(any())).thenReturn(true);

        target.update(apartmentAvailable);

        verify(repo).has(apartmentAvailable.getId());
        verify(repo).update(captor.capture());
        assertThat(captor.getValue()).isEqualTo(apartmentAvailable);
    }

    @Test
    void save_WithNonExistingId_ShouldThrowApartmentNotfoundException() {
        when(repo.has(any())).thenReturn(false);

        assertThatThrownBy(() ->
                target.update(apartmentAvailable)
        ).isInstanceOf(ApartmentNotfoundException.class);
        verify(repo).has(apartmentAvailable.getId());
    }
}
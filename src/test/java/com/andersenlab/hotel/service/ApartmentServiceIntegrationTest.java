package com.andersenlab.hotel.service;

import com.andersenlab.hotel.model.Apartment;
import com.andersenlab.hotel.model.ApartmentEntity;
import com.andersenlab.hotel.model.ApartmentStatus;
import com.andersenlab.hotel.repository.ApartmentSort;
import com.andersenlab.hotel.repository.SortableCrudRepository;
import com.andersenlab.hotel.repository.inmemory.InMemoryApartmentRepository;
import com.andersenlab.hotel.service.impl.ApartmentService;
import com.andersenlab.hotel.usecase.exception.ApartmentNotfoundException;
import com.andersenlab.hotel.usecase.exception.ApartmentWithSameIdExists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

class ApartmentServiceIntegrationTest {

    private ApartmentService target;
    private SortableCrudRepository<Apartment, ApartmentSort> repo;
    private Apartment apartmentAvailable;
    private Apartment apartmentUnavailable;

    @BeforeEach
    void setUp() {
        repo = new InMemoryApartmentRepository();
        target = new ApartmentService(repo);

        apartmentAvailable = new Apartment(UUID.randomUUID(), BigDecimal.ONE, BigInteger.ONE,
                true,ApartmentStatus.AVAILABLE);

        apartmentUnavailable = new Apartment(UUID.randomUUID(), BigDecimal.TWO, BigInteger.TWO,
                false, ApartmentStatus.RESERVED);
    }

    @Test
    void delete_NotExistingId_ShouldThrowsApartmentNotFoundException() {
        final UUID id = UUID.randomUUID();

        assertThatThrownBy(() ->
                target.delete(id)
        ).isInstanceOf(ApartmentNotfoundException.class);
    }

    @Test
    void delete_ExistingId_ShouldDelete() {
        target.save(apartmentAvailable);

        target.delete(apartmentAvailable.getId());

        assertThat(target.has(apartmentAvailable.getId())).isFalse();
    }

    @Test
    void has_NotExistingId_ShouldReturnFalse() {
        assertThat(target.has(UUID.randomUUID())).isFalse();
    }

    @Test
    void has_ExistingId_ShouldReturnTrue() {
        target.save(apartmentAvailable);

        assertThat(target.has(apartmentAvailable.getId())).isTrue();
    }

    @Test
    void getById_ExistingId_ShouldReturnApartmentEntity() {
        target.save(apartmentAvailable);
        ApartmentEntity actual = target.getById(apartmentAvailable.getId());

        assertThat(actual.id()).isEqualTo(apartmentAvailable.getId());
        assertThat(actual.price()).isEqualTo(apartmentAvailable.getPrice());
        assertThat(actual.capacity()).isEqualTo(apartmentAvailable.getCapacity());
        assertThat(actual.availability()).isEqualTo(apartmentAvailable.isAvailability());
        assertThat(actual.status()).isEqualTo(apartmentAvailable.getStatus());
    }

    @Test
    void save_ApartmentWithExistingId_ShouldThrowApartmentWithSameIdExists() {
        target.save(apartmentAvailable);

        assertThatThrownBy(() ->
                target.save(apartmentAvailable)
        ).isInstanceOf(ApartmentWithSameIdExists.class);
    }

    @Test
    void save_ApartmentWithNonExistingId_ShouldSave() {
        target.save(apartmentAvailable);

        ApartmentEntity actual = target.getById(apartmentAvailable.getId());

        assertThat(actual.id()).isEqualTo(apartmentAvailable.getId());
        assertThat(actual.price()).isEqualTo(apartmentAvailable.getPrice());
        assertThat(actual.capacity()).isEqualTo(apartmentAvailable.getCapacity());
        assertThat(actual.availability()).isEqualTo(apartmentAvailable.isAvailability());
        assertThat(actual.status()).isEqualTo(apartmentAvailable.getStatus());
    }
}

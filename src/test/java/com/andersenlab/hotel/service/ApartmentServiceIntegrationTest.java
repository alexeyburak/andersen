package com.andersenlab.hotel.service;

import com.andersenlab.hotel.model.Apartment;
import com.andersenlab.hotel.model.ApartmentStatus;
import com.andersenlab.hotel.repository.ApartmentSort;
import com.andersenlab.hotel.repository.inmemory.InMemoryApartmentRepository;
import com.andersenlab.hotel.service.impl.ApartmentService;
import com.andersenlab.hotel.usecase.exception.ApartmentNotfoundException;
import com.andersenlab.hotel.usecase.exception.ApartmentWithSameIdExists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class ApartmentServiceIntegrationTest {

    private ApartmentService target;
    private Apartment apartmentAvailable;
    private Apartment apartmentUnAvailable;

    @BeforeEach
    void setUp() {
        target = new ApartmentService(new InMemoryApartmentRepository());

        apartmentAvailable =
                new Apartment(UUID.fromString("00000000-0000-0000-0000-000000000001"), BigDecimal.ONE,
                        BigInteger.ONE, true, ApartmentStatus.AVAILABLE);
        apartmentUnAvailable =
                new Apartment(UUID.fromString("00000000-0000-0000-0000-000000000002"), BigDecimal.TWO,
                        BigInteger.TWO, false, ApartmentStatus.RESERVED);
    }

    @Test
    @Tag("delete")
    void deleteNoExistingId_ShouldThrowApartmentNotFoundException() {
        final UUID id = UUID.fromString("00000000-0000-0000-0000-000000000011");

        assertThatThrownBy(() ->
                target.delete(id)
        ).isInstanceOf(ApartmentNotfoundException.class);
    }

    @Test
    @Tag("delete")
    void deleteExistingId_ShouldDelete() {
        target.save(apartmentAvailable);

        target.delete(apartmentAvailable.getId());

        assertThat(target.has(apartmentAvailable.getId())).isFalse();
    }

    @Test
    @Tag("has")
    void hasNotExistingId_ShouldReturnFalse() {
        assertThat(target.has(apartmentAvailable.getId())).isFalse();
    }

    @Test
    @Tag("has")
    void hasExistingId_ShouldReturnTrue() {
        target.save(apartmentAvailable);

        assertThat(target.has(apartmentAvailable.getId())).isTrue();
    }

    @Test
    @Tag("getById")
    void getApartmentById_ReturnApartmentEntity() {
        target.save(apartmentAvailable);

        assertThatCode(() ->
                target.getById(apartmentAvailable.getId())
        ).doesNotThrowAnyException();
    }

    @Test
    @Tag("getById")
    void NotExistApartmentById_shouldThrowApartmentNotFoundException() {
        assertThatThrownBy(() ->
                target.getById(apartmentUnAvailable.getId())
        ).isInstanceOf(ApartmentNotfoundException.class);
    }

    @Test
    @Tag("save")
    void saveExistingApartment_shouldThrowApartmentWithSameIdExistException() {
        target.save(apartmentAvailable);

        assertThatThrownBy(() ->
                target.save(apartmentAvailable)
        ).isInstanceOf(ApartmentWithSameIdExists.class);
    }

    @Test
    @Tag("save")
    void saveApartment_shouldSaveApartment() {
        assertThatCode(() ->
                target.save(apartmentAvailable)
        ).doesNotThrowAnyException();
    }

    @Test
    @Tag("update")
    void updateExistingApartment_shouldNotThrowException() {
        target.save(apartmentAvailable);

        assertThatCode(() ->
                target.update(apartmentAvailable)
        ).doesNotThrowAnyException();
    }

    @Test
    @Tag("update")
    void updateNotExistingApartment_shouldThrowApartmentNotFoundException() {
        assertThatThrownBy(() ->
                target.update(apartmentUnAvailable)
        ).isInstanceOf(ApartmentNotfoundException.class);
    }

    @Test
    @Tag("update")
    void updateApartmentWithNullId_shouldThrowException() {
        apartmentAvailable.setId(null);

        assertThatThrownBy(() ->
                target.update(apartmentUnAvailable)
        ).isInstanceOf(ApartmentNotfoundException.class);
    }

    @Test
    @Tag("mapToEntity")
    void mapToEntity_shouldNotThrowAnyException() {
        assertThatCode(() ->
                target.list(ApartmentSort.ID)
        ).doesNotThrowAnyException();
    }

    @Test
    @Tag("adjust")
    void shouldAdjustApartmentPrice() {
        final BigDecimal newPrice = BigDecimal.TWO;
        target.save(apartmentAvailable);
        final UUID id = apartmentAvailable.getId();

        target.adjust(id, newPrice);

        assertThat(
                target.getById(id).price()
        ).isEqualTo(newPrice);
    }

    @Test
    @Tag("adjust")
    void adjustPrice_shouldThrowApartmentNotFoundException() {
        assertThatThrownBy(() ->
                target.adjust(UUID.fromString("00000000-0000-0000-0000-000000000011"), BigDecimal.ONE)
        ).isInstanceOf(ApartmentNotfoundException.class);
    }

    @Test
    @Tag("adjust")
    void adjustPriceNotThrowAnyException() {
        target.save(apartmentAvailable);

        assertThatCode(() ->
                target.adjust(apartmentAvailable.getId(), BigDecimal.ONE)
        ).doesNotThrowAnyException();
    }

    @Test
    @Tag("adjust")
    void notAdjustApartmentPrice_ifApartmentNotAvailable() {
        final BigDecimal newPrice = BigDecimal.ONE;
        target.save(apartmentUnAvailable);

        assertThatThrownBy(() ->
                target.adjust(apartmentUnAvailable.getId(), newPrice)
        ).isInstanceOf(ApartmentNotfoundException.class);

        assertThat(
                target.getById(apartmentUnAvailable.getId()).price()
        ).isNotEqualByComparingTo(newPrice);
    }

}

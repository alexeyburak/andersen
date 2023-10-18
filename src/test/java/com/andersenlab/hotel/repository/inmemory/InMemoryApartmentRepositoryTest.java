package com.andersenlab.hotel.repository.inmemory;

import com.andersenlab.hotel.model.Apartment;
import com.andersenlab.hotel.model.ApartmentStatus;
import com.andersenlab.hotel.repository.ApartmentSort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemoryApartmentRepositoryTest {

    private InMemoryApartmentRepository target;
    private Apartment apartment1, apartment2, apartment3, apartment4, apartment5;

    @BeforeEach
    void setUp() {
        target = new InMemoryApartmentRepository();

        apartment1 = new Apartment(UUID.randomUUID(), BigDecimal.ONE, BigInteger.ONE,
                true, ApartmentStatus.AVAILABLE);

        apartment2 = new Apartment(UUID.randomUUID(), BigDecimal.TWO, BigInteger.TWO,
                true, ApartmentStatus.RESERVED);

        apartment3 = new Apartment(UUID.fromString("9d76f43e-4ce2-40ba-90e1-37a6bc425ee1"),
                BigDecimal.TEN, BigInteger.TEN, true, ApartmentStatus.AVAILABLE);

        apartment4 = new Apartment(UUID.fromString("9d76f43e-4ce2-40ba-90e1-37a6bc425ee2"),
                BigDecimal.ONE, BigInteger.TWO, true, ApartmentStatus.AVAILABLE);

        apartment5 = new Apartment(UUID.fromString("9d76f43e-4ce2-40ba-90e1-37a6bc425ee3"),
                BigDecimal.TWO, BigInteger.ONE, false, ApartmentStatus.AVAILABLE);
    }

    @Test
    void save_SingleEntity_ShouldSaveEntityInStore() {
        target.save(apartment1);

        int actualSize = target.findAllSorted(ApartmentSort.ID).size();

        assertThat(actualSize).isEqualTo(1);
        assertTrue(target.has(apartment1.getId()));
    }

    @Test
    void save_MultipleEntity_ShouldSaveEntitiesInStore() {
        target.save(apartment2);
        target.save(apartment3);

        int actualSize = target.findAllSorted(ApartmentSort.ID).size();

        assertThat(actualSize).isEqualTo(2);
        assertTrue(target.has(apartment3.getId()));
        assertTrue(target.has(apartment2.getId()));
    }

    @Test
    void delete_ExistingEntity_ShouldDeleteEntityFromStore() {
        target.save(apartment1);

        target.delete(apartment1.getId());

        assertFalse(target.has(apartment1.getId()));
    }

    @Test
    void delete_NonExistingEntity_ShouldDeleteEntityFromStore() {
        target.delete(apartment1.getId());

        assertFalse(target.has(apartment1.getId()));
    }

    @Test
    void getById_ExistingEntity_ShouldReturnEntityFromStore() {
        target.save(apartment1);
        Optional<Apartment> actual = target.getById(apartment1.getId());

        assertTrue(actual.isPresent());
        assertThat(actual).contains(apartment1);
    }

    @Test
    void getById_ExistingEntity_ShouldReturnEmptyOptional() {
        Optional<Apartment> actual = target.getById(apartment1.getId());

        Assertions.assertTrue(actual.isEmpty());
    }

    @Test
    void findAllSorted_EntityId_ShouldSortEntitiesFromStoreById() {
        target.save(apartment3);
        target.save(apartment4);
        target.save(apartment5);

        List<Apartment> sorted = (List<Apartment>)
                target.findAllSorted(ApartmentSort.ID);

        assertThat(sorted.get(0)).isEqualTo(apartment3);
        assertThat(sorted.get(1)).isEqualTo(apartment4);
        assertThat(sorted.get(2)).isEqualTo(apartment5);
    }

    @Test
    void findAllSorted_EntityPrice_ShouldSortEntitiesFromStoreByPrice() {
        target.save(apartment3);
        target.save(apartment4);
        target.save(apartment5);

        List<Apartment> sorted = (List<Apartment>)
                target.findAllSorted(ApartmentSort.PRICE);

        assertThat(sorted.get(0)).isEqualTo(apartment4);
        assertThat(sorted.get(1)).isEqualTo(apartment5);
        assertThat(sorted.get(2)).isEqualTo(apartment3);
    }

    @Test
    void findAllSorted_EntityCapacity_ShouldSortEntitiesFromStoreByCapacity() {
        target.save(apartment3);
        target.save(apartment4);
        target.save(apartment5);

        List<Apartment> sorted = (List<Apartment>)
                target.findAllSorted(ApartmentSort.CAPACITY);

        assertThat(sorted.get(0)).isEqualTo(apartment5);
        assertThat(sorted.get(1)).isEqualTo(apartment4);
        assertThat(sorted.get(2)).isEqualTo(apartment3);
    }

    @Test
    void findAllSorted_EntityAvailability_ShouldSortEntitiesFromStoreByAvailability() {
        target.save(apartment5);
        target.save(apartment3);
        target.save(apartment4);

        List<Apartment> sorted = (List<Apartment>)
                target.findAllSorted(ApartmentSort.AVAILABILITY);

        assertFalse(sorted.get(0).isAvailability());
        assertTrue(sorted.get(1).isAvailability());
        assertTrue(sorted.get(2).isAvailability());
    }

    @Test
    void has_ExistingEntity_ShouldReturnTrue() {
        target.save(apartment1);

        assertTrue(target.has(apartment1.getId()));
    }
}

package com.andersenlab.hotel.repository.inmemory;

import com.andersenlab.hotel.model.Apartment;
import com.andersenlab.hotel.model.ApartmentStatus;
import com.andersenlab.hotel.repository.ApartmentSort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(target.has(apartment1.getId())).isTrue();
    }

    @Test
    void save_MultipleEntity_ShouldSaveEntitiesInStore() {
        target.save(apartment2);
        target.save(apartment3);

        int actualSize = target.findAllSorted(ApartmentSort.ID).size();

        assertThat(actualSize).isEqualTo(2);
        assertThat(target.has(apartment3.getId())).isTrue();
        assertThat(target.has(apartment2.getId())).isTrue();
    }

    @Test
    void delete_ExistingEntity_ShouldDeleteEntityFromStore() {
        target.save(apartment1);

        target.delete(apartment1.getId());

        assertThat(target.has(apartment1.getId())).isFalse();
    }

    @Test
    void delete_NonExistingEntity_ShouldDeleteEntityFromStore() {
        target.delete(apartment1.getId());

        assertThat(target.has(apartment1.getId())).isFalse();
    }

    @Test
    void getById_ExistingEntity_ShouldReturnEntityFromStore() {
        target.save(apartment1);
        Optional<Apartment> actual = target.getById(apartment1.getId());

        assertThat(actual).isPresent().contains(apartment1);
    }

    @Test
    void getById_ExistingEntity_ShouldReturnEmptyOptional() {
        Optional<Apartment> actual = target.getById(apartment1.getId());

        assertThat(actual).isEmpty();
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

        assertThat(sorted.get(0).isAvailability()).isFalse();
        assertThat(sorted.get(1).isAvailability()).isTrue();
        assertThat(sorted.get(2).isAvailability()).isTrue();
    }

    @Test
    void has_ExistingEntity_ShouldReturnTrue() {
        target.save(apartment1);

        assertThat(target.has(apartment1.getId())).isTrue();
    }
}

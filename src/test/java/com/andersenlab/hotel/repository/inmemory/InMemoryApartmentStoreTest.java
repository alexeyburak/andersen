package com.andersenlab.hotel.repository.inmemory;

import com.andersenlab.hotel.repository.ApartmentStore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

class InMemoryApartmentStoreTest {
    InMemoryApartmentStore target;

    ApartmentStore.ApartmentEntity apartment1, apartment2, apartment3, apartment4, apartment5;
    @BeforeEach
    void setUp() {
        target = new InMemoryApartmentStore();

        apartment1 = new ApartmentStore.
                ApartmentEntity(UUID.randomUUID(), BigDecimal.ONE, BigInteger.ONE,
                true, ApartmentStore.ApartmentStatus.AVAILABLE);

        apartment2 = new ApartmentStore.
                ApartmentEntity(UUID.randomUUID(), BigDecimal.TWO, BigInteger.TWO,
                true, ApartmentStore.ApartmentStatus.CLOSED);

        apartment3 = new ApartmentStore.
                ApartmentEntity(UUID.fromString("9d76f43e-4ce2-40ba-90e1-37a6bc425ee1"),
                BigDecimal.TEN, BigInteger.TEN, true,
                ApartmentStore.ApartmentStatus.AVAILABLE);

        apartment4 = new ApartmentStore.
                ApartmentEntity(UUID.fromString("9d76f43e-4ce2-40ba-90e1-37a6bc425ee2"),
                BigDecimal.ONE, BigInteger.TWO, true, ApartmentStore.ApartmentStatus.AVAILABLE);

        apartment5 = new ApartmentStore.
                ApartmentEntity(UUID.fromString("9d76f43e-4ce2-40ba-90e1-37a6bc425ee3"),
                BigDecimal.TWO, BigInteger.ONE, false, ApartmentStore.ApartmentStatus.AVAILABLE);
    }

    @Test
    void saveSingleTest() {
        target.save(apartment1);

        int actualSize = target.findAllSorted(ApartmentStore.Sort.ID).size();

        Assertions.assertEquals(1, actualSize);
        Assertions.assertTrue(target.has(apartment1.id()));
    }

    @Test
    void saveMultipleTest() {
        target.save(apartment2);
        target.save(apartment3);

        int actualSize = target.findAllSorted(ApartmentStore.Sort.ID).size();

        Assertions.assertEquals(2, actualSize);
        Assertions.assertTrue(target.has(apartment3.id()));
        Assertions.assertTrue(target.has(apartment2.id()));
    }

    @Test
    void deleteExistingTest() {
        target.save(apartment1);
        target.delete(apartment1.id());
        Assertions.assertFalse(target.has(apartment1.id()));
    }

    @Test
    void deleteNonExistingTest() {
        target.delete(apartment1.id());
        Assertions.assertFalse(target.has(apartment1.id()));
    }

    @Test
    void getExistingTest() {
        target.save(apartment1);
        ApartmentStore.ApartmentEntity actual = target.getById(apartment1.id());
        Assertions.assertEquals(apartment1, actual);
    }

    @Test
    void getNonExistingTest() {
        ApartmentStore.ApartmentEntity actual = target.getById(apartment1.id());

        Assertions.assertNotEquals(apartment1, actual);
        Assertions.assertNull(actual);
    }

    @Test
    void sortIdTest() {
        target.save(apartment3);
        target.save(apartment4);
        target.save(apartment5);

        List<ApartmentStore.ApartmentEntity> sorted = (List<ApartmentStore.ApartmentEntity>)
                target.findAllSorted(ApartmentStore.Sort.ID);

        Assertions.assertEquals(apartment3, sorted.get(0));
        Assertions.assertEquals(apartment4, sorted.get(1));
        Assertions.assertEquals(apartment5, sorted.get(2));
    }

    @Test
    void sortPriceTest() {
        target.save(apartment3);
        target.save(apartment4);
        target.save(apartment5);

        List<ApartmentStore.ApartmentEntity> sorted = (List<ApartmentStore.ApartmentEntity>)
                target.findAllSorted(ApartmentStore.Sort.PRICE);

        Assertions.assertEquals(apartment4, sorted.get(0));
        Assertions.assertEquals(apartment5, sorted.get(1));
        Assertions.assertEquals(apartment3, sorted.get(2));
    }

    @Test
    void sortCapacityTest() {
        target.save(apartment3);
        target.save(apartment4);
        target.save(apartment5);

        List<ApartmentStore.ApartmentEntity> sorted = (List<ApartmentStore.ApartmentEntity>)
                target.findAllSorted(ApartmentStore.Sort.CAPACITY);

        Assertions.assertEquals(apartment5, sorted.get(0));
        Assertions.assertEquals(apartment4, sorted.get(1));
        Assertions.assertEquals(apartment3, sorted.get(2));
    }

    @Test
    void sortAvailabilityTest() {
        target.save(apartment5);
        target.save(apartment3);
        target.save(apartment4);

        List<ApartmentStore.ApartmentEntity> sorted = (List<ApartmentStore.ApartmentEntity>)
                target.findAllSorted(ApartmentStore.Sort.AVAILABILITY);

        Assertions.assertFalse(sorted.get(0).availability());
        Assertions.assertTrue(sorted.get(1).availability());
        Assertions.assertTrue(sorted.get(2).availability());
    }

    @Test
    void hasExistingTest() {
        target.save(apartment1);
        Assertions.assertTrue(target.has(apartment1.id()));
    }

    @Test
    void hasNonExistingTest() {
        Assertions.assertFalse(target.has(apartment1.id()));
    }
}

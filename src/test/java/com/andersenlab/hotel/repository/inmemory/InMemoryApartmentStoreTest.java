package com.andersenlab.hotel.repository.inmemory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemoryApartmentStoreTest {
//    InMemoryApartmentStore target;
//    ApartmentStore.ApartmentEntity apartment1;
//    ApartmentStore.ApartmentEntity apartment2;
//    ApartmentStore.ApartmentEntity apartment3;
//    ApartmentStore.ApartmentEntity apartment4;
//    ApartmentStore.ApartmentEntity apartment5;
//
//    @BeforeEach
//    void setUp() {
//        target = new InMemoryApartmentStore();
//
//        apartment1 = new ApartmentStore.
//                ApartmentEntity(UUID.randomUUID(), BigDecimal.ONE, BigInteger.ONE,
//                true, ApartmentStore.ApartmentStatus.AVAILABLE);
//
//        apartment2 = new ApartmentStore.
//                ApartmentEntity(UUID.randomUUID(), BigDecimal.TWO, BigInteger.TWO,
//                true, ApartmentStore.ApartmentStatus.CLOSED);
//
//        apartment3 = new ApartmentStore.
//                ApartmentEntity(UUID.fromString("9d76f43e-4ce2-40ba-90e1-37a6bc425ee1"),
//                BigDecimal.TEN, BigInteger.TEN, true,
//                ApartmentStore.ApartmentStatus.AVAILABLE);
//
//        apartment4 = new ApartmentStore.
//                ApartmentEntity(UUID.fromString("9d76f43e-4ce2-40ba-90e1-37a6bc425ee2"),
//                BigDecimal.ONE, BigInteger.TWO, true, ApartmentStore.ApartmentStatus.AVAILABLE);
//
//        apartment5 = new ApartmentStore.
//                ApartmentEntity(UUID.fromString("9d76f43e-4ce2-40ba-90e1-37a6bc425ee3"),
//                BigDecimal.TWO, BigInteger.ONE, false, ApartmentStore.ApartmentStatus.AVAILABLE);
//    }
//
//    @Test
//    void save_SingleEntity_ShouldSaveEntityInStore() {
//        target.save(apartment1);
//
//        int actualSize = target.findAllSorted(ApartmentStore.Sort.ID).size();
//
//        assertEquals(1, actualSize);
//        assertTrue(target.has(apartment1.id()));
//    }
//
//    @Test
//    void save_MultipleEntity_ShouldSaveEntitiesInStore() {
//        target.save(apartment2);
//        target.save(apartment3);
//
//        int actualSize = target.findAllSorted(ApartmentStore.Sort.ID).size();
//
//        assertEquals(2, actualSize);
//        assertTrue(target.has(apartment3.id()));
//        assertTrue(target.has(apartment2.id()));
//    }
//
//    @Test
//    void delete_ExistingEntity_ShouldDeleteEntityFromStore() {
//        target.save(apartment1);
//
//        target.delete(apartment1.id());
//
//        assertFalse(target.has(apartment1.id()));
//    }
//
//    @Test
//    void delete_NonExistingEntity_ShouldDeleteEntityFromStore() {
//        target.delete(apartment1.id());
//
//        assertFalse(target.has(apartment1.id()));
//    }
//
//    @Test
//    void getById_ExistingEntity_ShouldReturnEntityFromStore() {
//        target.save(apartment1);
//
//        ApartmentStore.ApartmentEntity actual = target.getById(apartment1.id());
//
//        assertEquals(apartment1, actual);
//    }
//
//    @Test
//    void getById_ExistingEntity_ShouldReturnNullEntity() {
//        ApartmentStore.ApartmentEntity actual = target.getById(apartment1.id());
//
//        Assertions.assertNotEquals(apartment1, actual);
//        Assertions.assertNull(actual);
//    }
//
//    @Test
//    void findAllSorted_EntityId_ShouldSortEntitiesFromStoreById() {
//        target.save(apartment3);
//        target.save(apartment4);
//        target.save(apartment5);
//
//        List<ApartmentStore.ApartmentEntity> sorted = (List<ApartmentStore.ApartmentEntity>)
//                target.findAllSorted(ApartmentStore.Sort.ID);
//
//        assertEquals(apartment3, sorted.get(0));
//        assertEquals(apartment4, sorted.get(1));
//        assertEquals(apartment5, sorted.get(2));
//    }
//
//    @Test
//    void findAllSorted_EntityPrice_ShouldSortEntitiesFromStoreByPrice() {
//        target.save(apartment3);
//        target.save(apartment4);
//        target.save(apartment5);
//
//        List<ApartmentStore.ApartmentEntity> sorted = (List<ApartmentStore.ApartmentEntity>)
//                target.findAllSorted(ApartmentStore.Sort.PRICE);
//
//        assertEquals(apartment4, sorted.get(0));
//        assertEquals(apartment5, sorted.get(1));
//        assertEquals(apartment3, sorted.get(2));
//    }
//
//    @Test
//    void findAllSorted_EntityCapacity_ShouldSortEntitiesFromStoreByCapacity() {
//        target.save(apartment3);
//        target.save(apartment4);
//        target.save(apartment5);
//
//        List<ApartmentStore.ApartmentEntity> sorted = (List<ApartmentStore.ApartmentEntity>)
//                target.findAllSorted(ApartmentStore.Sort.CAPACITY);
//
//        assertEquals(apartment5, sorted.get(0));
//        assertEquals(apartment4, sorted.get(1));
//        assertEquals(apartment3, sorted.get(2));
//    }
//
//    @Test
//    void findAllSorted_EntityAvailability_ShouldSortEntitiesFromStoreByAvailability() {
//        target.save(apartment5);
//        target.save(apartment3);
//        target.save(apartment4);
//
//        List<ApartmentStore.ApartmentEntity> sorted = (List<ApartmentStore.ApartmentEntity>)
//                target.findAllSorted(ApartmentStore.Sort.AVAILABILITY);
//
//        assertFalse(sorted.get(0).availability());
//        assertTrue(sorted.get(1).availability());
//        assertTrue(sorted.get(2).availability());
//    }
//
//    @Test
//    void has_ExistingEntity_ShouldReturnTrue() {
//        target.save(apartment1);
//
//        assertTrue(target.has(apartment1.id()));
//    }
//
//    @Test
//    void has_NonExistingEntity_ShouldReturnFalse() {
//        assertFalse(target.has(apartment1.id()));
//    }
}

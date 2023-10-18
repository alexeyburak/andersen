package com.andersenlab.hotel.repository.inmemory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemoryClientStoreTest {
//    InMemoryClientStore target;
//    ClientStore.ClientEntity client1;
//    ClientStore.ClientEntity client2;
//    ClientStore.ClientEntity client3;
//    ClientStore.ClientEntity client4;
//    ClientStore.ClientEntity client5;
//
//    @BeforeEach
//    void setUp() {
//        target = new InMemoryClientStore();
//
//        client1 = new ClientStore.ClientEntity(UUID.randomUUID(), "name-1", ClientStore.ClientStatus.NEW);
//
//        client2 = new ClientStore.ClientEntity(UUID.randomUUID(), "name-2", ClientStore.ClientStatus.BANNED);
//
//        client3 = new ClientStore.ClientEntity(UUID.fromString("9d76f43e-4ce2-40ba-90e1-37a6bc425ee1"),
//                "name-3", ClientStore.ClientStatus.BANNED);
//
//        client4 = new ClientStore.ClientEntity(UUID.fromString("9d76f43e-4ce2-40ba-90e1-37a6bc425ee2"),
//                "name-4", ClientStore.ClientStatus.NEW);
//
//        client5 = new ClientStore.ClientEntity(UUID.fromString("9d76f43e-4ce2-40ba-90e1-37a6bc425ee3"),
//                "name-5", ClientStore.ClientStatus.ADVANCED);
//    }
//
//    @Test
//    void save_SingleEntity_ShouldSaveEntityInStore() {
//        target.save(client1);
//
//        int actualSize = target.findAllSorted(ClientStore.Sort.ID).size();
//
//        assertEquals(1, actualSize);
//        assertTrue(target.has(client1.id()));
//    }
//
//    @Test
//    void save_MultipleEntity_ShouldSaveEntityInStore() {
//        target.save(client2);
//        target.save(client3);
//
//        int actualSize = target.findAllSorted(ClientStore.Sort.ID).size();
//
//        assertEquals(2, actualSize);
//        assertTrue(target.has(client2.id()));
//        assertTrue(target.has(client3.id()));
//    }
//
//    @Test
//    void delete_ExistingEntity_ShouldDeleteEntityFromStore() {
//        target.save(client1);
//
//        target.delete(client1.id());
//
//        assertFalse(target.has(client1.id()));
//    }
//
//    @Test
//    void delete_NonExistingEntity_ShouldDeleteEntityFromStore() {
//        target.delete(client1.id());
//
//        assertFalse(target.has(client1.id()));
//    }
//
//    @Test
//    void getById_ExistingEntity_ShouldReturnEntityFromStore() {
//        target.save(client1);
//
//        ClientStore.ClientEntity actual = target.getById(client1.id());
//
//        assertEquals(client1, actual);
//    }
//
//    @Test
//    void getById_NonExistingEntity_ShouldReturnNullEntity() {
//        ClientStore.ClientEntity actual = target.getById(client1.id());
//
//        Assertions.assertNotEquals(client1, actual);
//        Assertions.assertNull(actual);
//    }
//
//    @Test
//    void findAllSorted_EntityId_ShouldSortEntitiesFromStoreById() {
//        target.save(client5);
//        target.save(client4);
//        target.save(client3);
//
//        List<ClientStore.ClientEntity> sorted = (List<ClientStore.ClientEntity>)
//                target.findAllSorted(ClientStore.Sort.ID);
//
//        assertEquals(client3, sorted.get(0));
//        assertEquals(client4, sorted.get(1));
//        assertEquals(client5, sorted.get(2));
//    }
//
//    @Test
//    void findAllSorted_EntityName_ShouldSortEntitiesFromStoreByName() {
//        target.save(client5);
//        target.save(client4);
//        target.save(client3);
//
//        List<ClientStore.ClientEntity> sorted = (List<ClientStore.ClientEntity>)
//                target.findAllSorted(ClientStore.Sort.NAME);
//
//        assertEquals(client3, sorted.get(0));
//        assertEquals(client4, sorted.get(1));
//        assertEquals(client5, sorted.get(2));
//    }
//
//    @Test
//    void findAllSorted_EntityStatus_ShouldSortEntitiesFromStoreByStatus() {
//        target.save(client3);
//        target.save(client4);
//        target.save(client5);
//
//        List<ClientStore.ClientEntity> sorted = (List<ClientStore.ClientEntity>)
//                target.findAllSorted(ClientStore.Sort.STATUS);
//
//        assertEquals(ClientStore.ClientStatus.NEW, sorted.get(0).status());
//        assertEquals(ClientStore.ClientStatus.ADVANCED, sorted.get(1).status());
//        assertEquals(ClientStore.ClientStatus.BANNED, sorted.get(2).status());
//    }
//
//    @Test
//    void has_ExistingEntity_ShouldReturnTrue() {
//        target.save(client1);
//
//        assertTrue(target.has(client1.id()));
//    }
//
//    @Test
//    void has_NonExistingEntity_ShouldReturnFalse() {
//        assertFalse(target.has(client1.id()));
//    }
}

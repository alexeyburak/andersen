package com.andersenlab.hotel.repository.inmemory;

import com.andersenlab.hotel.repository.ClientStore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

class InMemoryClientStoreTest {
    InMemoryClientStore target;
    ClientStore.ClientEntity client1, client2, client3, client4, client5;
    @BeforeEach
    void setUp() {
        target = new InMemoryClientStore();

        client1 = new ClientStore.ClientEntity(UUID.randomUUID(), "name-1", ClientStore.ClientStatus.NEW);

        client2 = new ClientStore.ClientEntity(UUID.randomUUID(), "name-2", ClientStore.ClientStatus.BANNED);

        client3 = new ClientStore.ClientEntity(UUID.fromString("9d76f43e-4ce2-40ba-90e1-37a6bc425ee1"),
                "name-3", ClientStore.ClientStatus.BANNED);

        client4 = new ClientStore.ClientEntity(UUID.fromString("9d76f43e-4ce2-40ba-90e1-37a6bc425ee2"),
                "name-4", ClientStore.ClientStatus.NEW);

        client5 = new ClientStore.ClientEntity(UUID.fromString("9d76f43e-4ce2-40ba-90e1-37a6bc425ee3"),
                "name-5", ClientStore.ClientStatus.ADVANCED);
    }

    @Test
    void saveSingleTest() {
        target.save(client1);

        int actualSize = target.findAllSorted(ClientStore.Sort.ID).size();

        Assertions.assertEquals(1, actualSize);
        Assertions.assertTrue(target.has(client1.id()));
    }

    @Test
    void saveMultipleTest() {
        target.save(client2);
        target.save(client3);

        int actualSize = target.findAllSorted(ClientStore.Sort.ID).size();

        Assertions.assertEquals(2, actualSize);
        Assertions.assertTrue(target.has(client2.id()));
        Assertions.assertTrue(target.has(client3.id()));
    }

    @Test
    void deleteExistingTest() {
        target.save(client1);
        target.delete(client1.id());
        Assertions.assertFalse(target.has(client1.id()));
    }

    @Test
    void deleteNonExistingTest() {
        target.delete(client1.id());
        Assertions.assertFalse(target.has(client1.id()));
    }

    @Test
    void getExistingTest() {
        target.save(client1);
        ClientStore.ClientEntity actual = target.getById(client1.id());
        Assertions.assertEquals(client1, actual);
    }

    @Test
    void getNonExistingTest() {
        ClientStore.ClientEntity actual = target.getById(client1.id());
        Assertions.assertNotEquals(client1, actual);
        Assertions.assertNull(actual);
    }

    @Test
    void sortIdTest() {
        target.save(client5);
        target.save(client4);
        target.save(client3);

        List<ClientStore.ClientEntity> sorted = (List<ClientStore.ClientEntity>)
                target.findAllSorted(ClientStore.Sort.ID);

        Assertions.assertEquals(client3, sorted.get(0));
        Assertions.assertEquals(client4, sorted.get(1));
        Assertions.assertEquals(client5, sorted.get(2));
    }

    @Test
    void sortNameTest() {
        target.save(client5);
        target.save(client4);
        target.save(client3);

        List<ClientStore.ClientEntity> sorted = (List<ClientStore.ClientEntity>)
                target.findAllSorted(ClientStore.Sort.NAME);

        Assertions.assertEquals(client3, sorted.get(0));
        Assertions.assertEquals(client4, sorted.get(1));
        Assertions.assertEquals(client5, sorted.get(2));
    }

    @Test
    void sortStatusTest() {
        target.save(client3);
        target.save(client4);
        target.save(client5);

        List<ClientStore.ClientEntity> sorted = (List<ClientStore.ClientEntity>)
                target.findAllSorted(ClientStore.Sort.STATUS);

        Assertions.assertEquals(ClientStore.ClientStatus.NEW, sorted.get(0).status());
        Assertions.assertEquals(ClientStore.ClientStatus.ADVANCED, sorted.get(1).status());
        Assertions.assertEquals(ClientStore.ClientStatus.BANNED, sorted.get(2).status());
    }

    @Test
    void hasExistingTest() {
        target.save(client1);
        Assertions.assertTrue(target.has(client1.id()));
    }

    @Test
    void hasNonExistingTest() {
        Assertions.assertFalse(target.has(client1.id()));
    }
}

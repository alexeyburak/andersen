package com.andersenlab.hotel.repository.inmemory;


import com.andersenlab.hotel.model.Client;
import com.andersenlab.hotel.repository.ClientStore;
import com.andersenlab.hotel.repository.CrudRepository;

import java.util.*;

public final class InMemoryClientStore implements CrudRepository<Client> {
    private final Map<UUID, Client> map;
    private static InMemoryClientStore instance;


    protected InMemoryClientStore() {
        map = new HashMap<>();
    }

    public static InMemoryClientStore getInstance() {
        if (instance == null) {
            instance = new InMemoryClientStore();
        }
        return instance;
    }


    @Override
    public void save(Client client) {
        map.put(client.getId(), client);
    }


    public Collection<Client> findAllSorted(ClientStore.Sort sort) {
        return map.values().stream()
//                .sorted(sort.getComparator())
                .toList();
    }
//    TODO Enums for comparator

    @Override
    public void delete(UUID id) {
        map.remove(id);
    }

    @Override
    public boolean hasIn(UUID id) {
        return map.containsKey(id);
    }

    @Override
    public Optional<Client> getById(UUID id) {
        return Optional.ofNullable(map.get(id));
    }


    @Override
    public Collection<Client> findAll() {
        return null;
    }

}


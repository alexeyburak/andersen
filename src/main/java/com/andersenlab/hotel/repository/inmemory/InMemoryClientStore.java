package com.andersenlab.hotel.repository.inmemory;

import com.andersenlab.hotel.repository.ClientStore;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class InMemoryClientStore implements ClientStore {
    private final Map<UUID, ClientEntity> map;
    private static InMemoryClientStore instance;

    protected InMemoryClientStore(){
        map = new HashMap<>();
    }

    public static InMemoryClientStore getInstance() {
        if(instance == null){
            instance = new InMemoryClientStore();
        }
        return instance;
    }


    @Override
    public void save(ClientEntity clientEntity) {
        map.put(clientEntity.id(), clientEntity);
    }

    @Override
    public Collection<ClientEntity> findAllSorted(Sort sort) {
        return map.values().stream()
                .sorted(sort.getComparator())
                .toList();
    }

    @Override
    public void delete(UUID id) {
        map.remove(id);
    }

    @Override
    public boolean has(UUID id) {
        return map.containsKey(id);
    }

    @Override
    public ClientEntity getById(UUID id) {
        return map.get(id);
    }
}

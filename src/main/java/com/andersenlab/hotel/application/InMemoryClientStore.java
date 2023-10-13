package com.andersenlab.hotel.application;

import com.andersenlab.hotel.port.external.ClientStore;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class InMemoryClientStore implements ClientStore {

    private final Map<UUID, ClientEntity> map = new HashMap<>();
    private final Map<Sort, Comparator<ClientEntity>> comparators = Map.of(
            Sort.ID, Comparator.comparing(ClientEntity::id),
            Sort.NAME, Comparator.comparing(ClientEntity::name),
            Sort.STATUS, Comparator.comparing(ClientEntity::status)
    );

    @Override
    public void save(ClientEntity clientEntity) {
        map.put(clientEntity.id(), clientEntity);
    }

    @Override
    public Collection<ClientEntity> findAllSorted(Sort sort) {
        return map.values().stream()
                .sorted(comparators.get(sort))
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

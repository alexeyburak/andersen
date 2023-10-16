package com.andersenlab.hotel.repository.inmemory;

import com.andersenlab.hotel.repository.ApartmentStore;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public final class InMemoryApartmentStore implements ApartmentStore {
    private static InMemoryApartmentStore instance;
    private final Map<UUID, ApartmentEntity> map;

    protected InMemoryApartmentStore() {
        map = new HashMap<>();
    } //TODO ask about this when code-review

    public static InMemoryApartmentStore getInstance() {
        if (instance == null) {
            instance = new InMemoryApartmentStore();
        }
        return instance;
    }

    @Override
    public void save(ApartmentEntity apartmentEntity) {
        map.put(apartmentEntity.id(), apartmentEntity);
    }

    @Override
    public Collection<ApartmentEntity> findAllSorted(Sort sort) {
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
    public ApartmentEntity getById(UUID id) {
        return map.get(id);
    }
}

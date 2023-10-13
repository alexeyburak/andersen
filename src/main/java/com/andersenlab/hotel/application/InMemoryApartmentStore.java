package com.andersenlab.hotel.application;

import com.andersenlab.hotel.port.external.ApartmentStore;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class InMemoryApartmentStore implements ApartmentStore {

    private final Map<UUID, ApartmentEntity> map = new HashMap<>();
    private final Map<Sort, Comparator<ApartmentEntity>> comparators = Map.of(
            Sort.ID, Comparator.comparing(ApartmentEntity::id),
            Sort.PRICE, Comparator.comparing(ApartmentEntity::price),
            Sort.CAPACITY, Comparator.comparing(ApartmentEntity::capacity),
            Sort.AVAILABILITY, Comparator.comparing(ApartmentEntity::availability)
    );

    @Override
    public void save(ApartmentEntity apartmentEntity) {
        map.put(apartmentEntity.id(), apartmentEntity);
    }

    @Override
    public Collection<ApartmentEntity> findAllSorted(Sort sort) {
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
    public ApartmentEntity getById(UUID id) {
        return map.get(id);
    }
}

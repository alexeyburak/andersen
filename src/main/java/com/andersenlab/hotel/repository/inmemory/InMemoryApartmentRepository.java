package com.andersenlab.hotel.repository.inmemory;

import com.andersenlab.hotel.model.Apartment;
import com.andersenlab.hotel.repository.SortableCrudRepository;
import com.andersenlab.hotel.repository.ApartmentSort;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public final class InMemoryApartmentRepository implements SortableCrudRepository<Apartment, ApartmentSort> {

    private final Map<UUID, Apartment> apartments = new HashMap<>();

    @Override
    public void save(Apartment apartment) {
        apartments.put(apartment.getId(), apartment);
    }

    @Override
    public void update(Apartment apartment) {
        apartments.put(apartment.getId(), apartment);
    }

    @Override
    public Collection<Apartment> findAllSorted(ApartmentSort sort) {
        return apartments.values()
                .stream()
                .sorted(sort.getComparator())
                .toList();
    }

    @Override
    public void delete(UUID id) {
        apartments.remove(id);
    }

    @Override
    public boolean has(UUID id) {
        return apartments.containsKey(id);
    }

    @Override
    public Optional<Apartment> getById(UUID id) {
        return Optional.ofNullable(apartments.get(id));
    }
}

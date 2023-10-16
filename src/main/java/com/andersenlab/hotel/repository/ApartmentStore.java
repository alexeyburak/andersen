package com.andersenlab.hotel.repository;

import com.andersenlab.hotel.model.Apartment;
import com.andersenlab.hotel.model.ApartmentSort;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface ApartmentStore {

    void save(Apartment apartment);

    Collection<Apartment> findAllSorted(ApartmentSort sort);

    void delete(UUID id);

    boolean has(UUID id);

    Optional<Apartment> getById(UUID id);

    void update(Apartment apartment);
}

package com.andersenlab.hotel.service;

import com.andersenlab.hotel.model.Apartment;
import com.andersenlab.hotel.repository.inmemory.InMemoryApartmentStore;
import com.andersenlab.hotel.usecase.AdjustApartmentPriceUseCase;

import java.math.BigDecimal;
import java.util.*;

public class ApartmentService implements AdjustApartmentPriceUseCase{

    private InMemoryApartmentStore inMemoryApartmentStore;

    private static ApartmentService instance;

    public static ApartmentService getInstance() {
        if (instance == null) {
            instance = new ApartmentService();
        }
        return instance;
    }

    public void addApartment(Apartment apartment) {
        inMemoryApartmentStore.save(apartment);
    }
    public Collection<Apartment> getAllApartments() {
        return inMemoryApartmentStore.findAll();
    }

    public void delete(UUID apartment) {
        inMemoryApartmentStore.delete(apartment);
    }


    public boolean hasIn(UUID apartment) {
        return inMemoryApartmentStore.hasIn(apartment);
    }

    public Optional<Apartment> getById(UUID id) {
     return inMemoryApartmentStore.getById(id);
    }

    @Override
    public void adjust(UUID id, BigDecimal newPrice) {
        inMemoryApartmentStore.getById(id).ifPresent(a -> inMemoryApartmentStore.save(
                        new Apartment(a.getId(),newPrice, a.getCapacity(), a.isAvailability())));

    }
}

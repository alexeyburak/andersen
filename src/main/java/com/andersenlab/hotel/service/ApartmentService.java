package com.andersenlab.hotel.service;

import com.andersenlab.hotel.model.Apartment;
import com.andersenlab.hotel.repository.inmemory.InMemoryApartmentStore;
import com.andersenlab.hotel.usecase.AdjustApartmentPriceUseCase;

import java.math.BigDecimal;
import java.util.*;

public class ApartmentService implements AdjustApartmentPriceUseCase{

    private InMemoryApartmentStore inMemoryApartmentStore;

    private static ApartmentService instance;

    protected ApartmentService() {
        this.apartmentStore = InMemoryApartmentStore.getInstance();
    }

    protected ApartmentService(final ApartmentStore apartmentStore) {
        this.apartmentStore = apartmentStore;
    }

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
        ApartmentStore.ApartmentEntity entity = apartmentStore.getById(id);
        inMemoryApartmentStore.getById(id).ifPresent(a -> inMemoryApartmentStore.save(
                new Apartment(a.getId(),newPrice, a.getCapacity(), a.isAvailability())));

        LOG.info("Adjust apartment price. ID: {}", id);
    }

    @Override
    public List<ApartmentView> list(Sort sort) {
        return apartmentStore.findAllSorted(ApartmentStore.Sort.valueOf(sort.toString()))
                .stream()
                .map(Apartment::new)
                .map(Apartment::view)
                .toList();
    }

}

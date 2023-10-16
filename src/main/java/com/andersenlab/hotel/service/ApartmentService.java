package com.andersenlab.hotel.service;

import com.andersenlab.hotel.model.Apartment;
import com.andersenlab.hotel.model.ApartmentEntity;
import com.andersenlab.hotel.model.ApartmentSort;
import com.andersenlab.hotel.repository.inmemory.InMemoryApartmentStore;
import com.andersenlab.hotel.usecase.AdjustApartmentPriceUseCase;
import com.andersenlab.hotel.usecase.ListApartmentsUseCase;
import com.andersenlab.hotel.usecase.exception.ApartmentWithSameIdExists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;

public final class ApartmentService implements AdjustApartmentPriceUseCase, ListApartmentsUseCase {

    private static final Logger LOG = LoggerFactory.getLogger(ApartmentService.class);

    private Function<Apartment, ApartmentEntity> apartmentApartmentEntityFunction = (apartment ->
            new ApartmentEntity(apartment.getId(), apartment.getPrice(), apartment.getCapacity(),
                    apartment.isAvailability(), apartment.getStatus()));

    private InMemoryApartmentStore inMemoryApartmentStore;

    private static ApartmentService instance;

    protected ApartmentService() {
        this.inMemoryApartmentStore = InMemoryApartmentStore.getInstance();
    }

    protected ApartmentService(final InMemoryApartmentStore apartmentStore) {
        this.inMemoryApartmentStore = apartmentStore;
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

    public Optional<ApartmentEntity> getById(UUID id) {
        return inMemoryApartmentStore.getById(id).map(apartmentApartmentEntityFunction);
    }

    @Override
    public void adjust(UUID id, BigDecimal newPrice) {
        inMemoryApartmentStore.getById(id).ifPresent(a -> inMemoryApartmentStore.save(
                new Apartment(a.getId(),newPrice, a.getCapacity(), a.isAvailability())));

        LOG.info("Adjust apartment price. ID: {}", id);
    }

    @Override
    public List<ApartmentEntity> list(ApartmentSort sort) {
        return inMemoryApartmentStore.findAllSorted(sort)
                .stream()
                .map(apartmentApartmentEntityFunction)
                .toList();
    }

    public void save(Apartment apartment) {
        if(hasIn(apartment.getId())) {
            throw new ApartmentWithSameIdExists("Apartment with same id already exists");
        }
        inMemoryApartmentStore.save(apartment);
    }
}

package com.andersenlab.hotel.service;

import com.andersenlab.hotel.repository.inmemory.InMemoryApartmentStore;
import com.andersenlab.hotel.repository.ApartmentStore;
import com.andersenlab.hotel.usecase.AddApartmentUseCase;
import com.andersenlab.hotel.usecase.AdjustApartmentPriceUseCase;
import com.andersenlab.hotel.usecase.ListApartmentsUseCase;
import com.andersenlab.hotel.usecase.exception.ApartmentWithSameIdExists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

public final class ApartmentService
        implements AddApartmentUseCase,
        AdjustApartmentPriceUseCase,
        ListApartmentsUseCase {

    private static final Logger LOG = LoggerFactory.getLogger(ApartmentService.class);
    private final ApartmentStore apartmentStore;
    private static ApartmentService instance;

    protected ApartmentService() {
        this.apartmentStore = InMemoryApartmentStore.getInstance();
    }

    protected ApartmentService(final ApartmentStore apartmentStore) {
        this.apartmentStore = apartmentStore;
    }

    public static ApartmentService getInstance() {
        if(instance == null){
            instance = new ApartmentService();
        }
        return instance;
    }

    @Override
    public void add(UUID id, BigDecimal price, BigInteger capacity, boolean availability) {
        if (apartmentStore.has(id)) {
            throw new ApartmentWithSameIdExists();
        }
        LOG.info("Add new apartment. ID: {}", id);
        apartmentStore.save(
                new ApartmentStore.ApartmentEntity(id, price, capacity, availability, ApartmentStore.ApartmentStatus.AVAILABLE)
        );
    }

    @Override
    public void adjust(UUID id, BigDecimal newPrice) {
        ApartmentStore.ApartmentEntity entity = apartmentStore.getById(id);
        //Todo
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

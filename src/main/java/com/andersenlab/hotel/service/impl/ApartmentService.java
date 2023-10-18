package com.andersenlab.hotel.service.impl;

import com.andersenlab.hotel.model.Apartment;
import com.andersenlab.hotel.model.ApartmentEntity;
import com.andersenlab.hotel.repository.SortableCrudRepository;
import com.andersenlab.hotel.repository.ApartmentSort;
import com.andersenlab.hotel.service.CrudService;
import com.andersenlab.hotel.usecase.AdjustApartmentPriceUseCase;
import com.andersenlab.hotel.usecase.ListApartmentsUseCase;
import com.andersenlab.hotel.usecase.exception.ApartmentNotfoundException;
import com.andersenlab.hotel.usecase.exception.ApartmentWithSameIdExists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

public final class ApartmentService implements AdjustApartmentPriceUseCase,
        ListApartmentsUseCase, CrudService<Apartment, ApartmentEntity> {
    private static final Logger LOG = LoggerFactory.getLogger("service-logger");

    private final SortableCrudRepository<Apartment, ApartmentSort> store;
    private final Function<Apartment, ApartmentEntity> toEntityMapper = (apartment ->
            new ApartmentEntity(apartment.getId(), apartment.getPrice(), apartment.getCapacity(),
                    apartment.isAvailability(), apartment.getStatus())
    );

    public ApartmentService(final SortableCrudRepository<Apartment, ApartmentSort> store) {
        this.store = store;
    }

    @Override
    public void delete(UUID id) {
        if(!has(id)) {
            throw new ApartmentNotfoundException();
        }
        store.delete(id);
        LOG.info("Delete apartment. ID: {}", id);
    }

    @Override
    public boolean has(UUID id) {
        return store.has(id);
    }

    @Override
    public ApartmentEntity getById(UUID id) throws ApartmentNotfoundException {
        return store.getById(id)
                .map(toEntityMapper)
                .orElseThrow(ApartmentNotfoundException::new);
    }

    @Override
    public void adjust(UUID id, BigDecimal newPrice) {
        store.getById(id).ifPresentOrElse(
                        a -> store.save(new Apartment(a.getId(), newPrice, a.getCapacity(), a.isAvailability())),
                        ApartmentNotfoundException::new);

        LOG.info("Adjust apartment price. ID: {}", id);
    }

    @Override
    public List<ApartmentEntity> list(ApartmentSort sort) {
        return store.findAllSorted(sort)
                .stream()
                .map(toEntityMapper)
                .toList();
    }

    @Override
    public void save(Apartment apartment) {
        if(has(apartment.getId())) {
            throw new ApartmentWithSameIdExists();
        }
        UUID id = apartment.getId();

        if (has(id)) {
            throw new ApartmentWithSameIdExists();
        }

        store.save(apartment);
        LOG.info("Save apartment. ID: {}", id);
    }

    @Override
    public void update(Apartment apartment) {
        if(has(apartment.getId())) {
            throw new ApartmentNotfoundException();
        }
        store.update(apartment);
    }
}

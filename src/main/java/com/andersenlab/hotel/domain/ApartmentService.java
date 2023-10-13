package com.andersenlab.hotel.domain;

import com.andersenlab.hotel.port.external.ApartmentStore;
import com.andersenlab.hotel.port.usecase.AddApartmentUseCase;
import com.andersenlab.hotel.port.usecase.AdjustApartmentPriceUseCase;
import com.andersenlab.hotel.port.usecase.ListApartmentsUseCase;
import com.andersenlab.hotel.port.usecase.exception.ApartmentWithSameIdExists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

final class ApartmentService
        implements AddApartmentUseCase,
        AdjustApartmentPriceUseCase,
        ListApartmentsUseCase {

    private static final Logger LOG = LoggerFactory.getLogger(ApartmentService.class);
    private final ApartmentStore apartmentStore;

    ApartmentService(ApartmentStore apartmentStore) {
        this.apartmentStore = apartmentStore;
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

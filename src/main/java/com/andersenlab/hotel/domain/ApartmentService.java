package com.andersenlab.hotel.domain;

import com.andersenlab.hotel.port.external.ApartmentStore;
import com.andersenlab.hotel.port.usecase.AddApartmentUseCase;
import com.andersenlab.hotel.port.usecase.AdjustApartmentPriceUseCase;
import com.andersenlab.hotel.port.usecase.ListApartmentsUseCase;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

final class ApartmentService
        implements AddApartmentUseCase,
        AdjustApartmentPriceUseCase,
        ListApartmentsUseCase {

    private final ApartmentStore apartmentStore;

    ApartmentService(ApartmentStore apartmentStore) {
        this.apartmentStore = apartmentStore;
    }

    @Override
    public void add(UUID id, BigDecimal price, BigInteger capacity, boolean availability) {

    }

    @Override
    public void adjust(UUID id) {

    }

    @Override
    public List<ApartmentView> list(Sort sort) {
        return null;
    }

}

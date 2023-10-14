package com.andersenlab.hotel.service;

import com.andersenlab.hotel.repository.ApartmentStore;
import com.andersenlab.hotel.usecase.ListApartmentsUseCase;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;
final class Apartment {

    private final ApartmentStore.ApartmentEntity entity;

    Apartment(ApartmentStore.ApartmentEntity entity) {
        this.entity = entity;
    }

    Apartment(UUID id, BigDecimal price, BigInteger capacity, boolean availability) {
        this(new ApartmentStore.ApartmentEntity(id, price, capacity, availability,
                ApartmentStore.ApartmentStatus.AVAILABLE));
    }

    ListApartmentsUseCase.ApartmentView view() {
        return new ListApartmentsUseCase.ApartmentView(
                entity.id(),
                entity.price(),
                entity.capacity(),
                entity.availability(),
                ListApartmentsUseCase.ApartmentStatus.valueOf(entity.status().toString())
        );
    }
}

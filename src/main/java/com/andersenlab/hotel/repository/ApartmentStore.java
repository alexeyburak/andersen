package com.andersenlab.hotel.repository;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Comparator;
import java.util.UUID;

public interface ApartmentStore  {

    void save(ApartmentEntity apartmentEntity);

    Collection<ApartmentEntity> findAllSorted(Sort sort);

    void delete(UUID id);

    boolean has(UUID id);

    ApartmentEntity getById(UUID id);


    @Getter
    @AllArgsConstructor
    enum Sort {
        ID(Comparator.comparing(ApartmentEntity::id)), PRICE(Comparator.comparing(ApartmentEntity::price)),
        CAPACITY(Comparator.comparing(ApartmentEntity::capacity)),
        AVAILABILITY(Comparator.comparing(ApartmentEntity::availability));

        private Comparator<ApartmentEntity> comparator;
    }

    enum ApartmentStatus {
        AVAILABLE, CLOSED
    }

    record ApartmentEntity(UUID id, BigDecimal price, BigInteger capacity, boolean availability,
                           ApartmentStatus status) {
    }
}

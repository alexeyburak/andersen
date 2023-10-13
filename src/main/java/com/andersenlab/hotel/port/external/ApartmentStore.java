package com.andersenlab.hotel.port.external;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.UUID;

public interface ApartmentStore {

    void save(ApartmentEntity apartmentEntity);

    Collection<ApartmentEntity> findAllSorted(Sort sort);

    void delete(UUID id);

    boolean has(UUID id);

    ApartmentEntity getById(UUID id);

    enum Sort {
        ID, PRICE, CAPACITY, AVAILABILITY
    }

    enum ApartmentStatus {
        AVAILABLE, CLOSED
    }

    record ApartmentEntity(UUID id, BigDecimal price, BigInteger capacity, boolean availability,
                           ApartmentStatus status) {
    }
}

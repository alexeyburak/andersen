package com.andersenlab.hotel.usecase;

import com.andersenlab.hotel.model.ApartmentEntity;
import com.andersenlab.hotel.model.ApartmentSort;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

public interface ListApartmentsUseCase {

    List<ApartmentEntity> list(ApartmentSort sort);

    enum ApartmentStatus {
        AVAILABLE, CLOSED
    }

    record ApartmentView(UUID id, BigDecimal price, BigInteger capacity, boolean availability,
                           ApartmentStatus status) {
    }
}

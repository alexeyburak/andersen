package com.andersenlab.hotel.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;

public record ApartmentEntity(UUID id, BigDecimal price, BigInteger capacity, boolean availability,
                              ApartmentStatus status) {
}

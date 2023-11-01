package com.andersenlab.hotel.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;

@Data
@NoArgsConstructor
public final class Apartment {
    private UUID id;
    private BigDecimal price;
    private BigInteger capacity;
    private boolean availability;
    private ApartmentStatus status;

    public Apartment(UUID id, BigDecimal price, BigInteger capacity, boolean availability) {
        this(id, price, capacity, availability, ApartmentStatus.AVAILABLE);
    }

    public Apartment(UUID id) {
        this.id = id;
    }

    public Apartment(UUID id, BigDecimal price, BigInteger capacity, boolean availability, ApartmentStatus status) {
        this.id = id;
        this.price = price;
        this.capacity = capacity;
        this.availability = availability;
        this.status = status;
    }
}
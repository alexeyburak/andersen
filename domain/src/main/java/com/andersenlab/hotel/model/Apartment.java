package com.andersenlab.hotel.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.Data;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "apartment")
public final class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private BigDecimal price;
    private BigInteger capacity;
    private boolean availability;
    @Enumerated(value = EnumType.STRING)
    private ApartmentStatus status;

    public Apartment(UUID id, BigDecimal price, BigInteger capacity, boolean availability) {
        this(id, price, capacity, availability, ApartmentStatus.AVAILABLE);
    }

    public Apartment() {
    }

    public Apartment(UUID id) {
        this.id = id;
    }

    public Apartment(UUID id, BigDecimal price) {
        this.id = id;
        this.price = price;
    }

    public Apartment(UUID id, BigDecimal price, BigInteger capacity, boolean availability, ApartmentStatus status) {
        this.id = id;
        this.price = price;
        this.capacity = capacity;
        this.availability = availability;
        this.status = status;
    }

    public Apartment(BigDecimal price, BigInteger capacity, boolean availability, ApartmentStatus status) {
        this.price = price;
        this.capacity = capacity;
        this.availability = availability;
        this.status = status;
    }
}
package com.andersenlab.hotel.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import lombok.Data;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;

@Data
@Entity
public final class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private BigDecimal price;
    private BigInteger capacity;
    private boolean availability;
    @Enumerated(EnumType.STRING)
    private ApartmentStatus status;

    public Apartment(UUID id, BigDecimal price, BigInteger capacity, boolean availability) {
        this(id, price, capacity, availability, ApartmentStatus.AVAILABLE);
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

    public Apartment() {
    }
}


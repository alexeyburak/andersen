package com.andersenlab.hotel.model;

import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public final class Apartment {
    private UUID id;
    private BigDecimal price;
    private BigInteger capacity;
    private boolean availability;
    private ApartmentStatus status;
    private List<Client> clients;

    public Apartment(UUID id, BigDecimal price, BigInteger capacity, boolean availability) {
        this.id = id;
        this.price = price;
        this.capacity = capacity;
        this.availability = availability;
        this.status = ApartmentStatus.AVAILABLE;
        this.clients = new ArrayList<>();
    }

    public Apartment(UUID id, BigDecimal price, BigInteger capacity, boolean availability, ApartmentStatus status) {
        this.id = id;
        this.price = price;
        this.capacity = capacity;
        this.availability = availability;
        this.status = status;
        this.clients = new ArrayList<>();
    }
}


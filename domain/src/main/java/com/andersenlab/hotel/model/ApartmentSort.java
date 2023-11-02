package com.andersenlab.hotel.model;

import com.andersenlab.hotel.model.Apartment;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Comparator;

@Getter
@AllArgsConstructor
public enum ApartmentSort {
    ID(Comparator.comparing(Apartment::getId)),
    PRICE(Comparator.comparing(Apartment::getPrice)),
    CAPACITY(Comparator.comparing(Apartment::getCapacity)),
    AVAILABILITY(Comparator.comparing(Apartment::isAvailability));

    private final Comparator<Apartment> comparator;
}
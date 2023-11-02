package com.andersenlab.hotel.repository.jpa;

import com.andersenlab.hotel.model.ApartmentStatus;

import jakarta.persistence.AttributeConverter;
import java.util.Optional;

public class ApartmentStatusConverter implements AttributeConverter<ApartmentStatus, String> {

    @Override
    public String convertToDatabaseColumn(ApartmentStatus apartmentStatus) {
        return apartmentStatus.name();
    }

    @Override
    public ApartmentStatus convertToEntityAttribute(String s) {
        return Optional.of(ApartmentStatus.valueOf(s)).orElse(null);
    }
}

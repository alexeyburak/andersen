package com.andersenlab.hotel.usecase;

import com.andersenlab.hotel.model.ApartmentEntity;

import java.util.UUID;

public interface GetApartmentUseCase {

    ApartmentEntity getById(UUID id);
}

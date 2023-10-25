package com.andersenlab.hotel.usecase.impl;

import com.andersenlab.hotel.usecase.CheckInClientUseCase;
import com.andersenlab.hotel.usecase.exception.ApartmentChangeStatusException;

import java.util.UUID;

public class BlockedCheckIn implements CheckInClientUseCase {

    @Override
    public void checkIn(UUID clientId, UUID apartmentId) {
        throw new ApartmentChangeStatusException();
    }
}

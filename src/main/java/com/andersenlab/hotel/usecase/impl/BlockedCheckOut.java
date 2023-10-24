package com.andersenlab.hotel.usecase.impl;

import com.andersenlab.hotel.usecase.CheckOutClientUseCase;
import com.andersenlab.hotel.usecase.exception.ApartmentChangeStatusException;

import java.util.UUID;

public class BlockedCheckOut implements CheckOutClientUseCase {
    @Override
    public void checkOut(UUID clientId, UUID apartmentId) {
        throw new ApartmentChangeStatusException();
    }
}

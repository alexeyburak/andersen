package com.andersenlab.hotel.port.usecase;

import java.util.UUID;

public interface CheckOutClientUseCase {

    void checkOut(UUID clientId, UUID apartmentId);
}

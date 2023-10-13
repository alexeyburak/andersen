package com.andersenlab.hotel.port.usecase;

import java.util.UUID;

public interface CheckInClientUseCase {

    void checkIn(UUID clientId, UUID apartmentId);
}

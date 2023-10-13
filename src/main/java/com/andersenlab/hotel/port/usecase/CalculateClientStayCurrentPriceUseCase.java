package com.andersenlab.hotel.port.usecase;

import java.util.UUID;

public interface CalculateClientStayCurrentPriceUseCase {

    void calculatePrice(UUID id);
}

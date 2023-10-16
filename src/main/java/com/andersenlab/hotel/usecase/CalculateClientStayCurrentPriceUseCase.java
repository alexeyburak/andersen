package com.andersenlab.hotel.usecase;

import java.util.UUID;

public interface CalculateClientStayCurrentPriceUseCase {

    double calculatePrice(UUID id);
}

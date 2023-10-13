package com.andersenlab.hotel.port.usecase;

import java.math.BigDecimal;
import java.util.UUID;

public interface AdjustApartmentPriceUseCase {

    void adjust(UUID id, BigDecimal newPrice);
}

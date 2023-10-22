package com.andersenlab.hotel.usecase;

import java.math.BigDecimal;
import java.util.UUID;

public interface CalculateClientStayCurrentPriceUseCase {

    BigDecimal calculatePrice(UUID id);
}

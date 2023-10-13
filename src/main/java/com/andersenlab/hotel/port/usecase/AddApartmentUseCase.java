package com.andersenlab.hotel.port.usecase;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;

public interface AddApartmentUseCase {

    void add(UUID id, BigDecimal price, BigInteger capacity, boolean availability);
}

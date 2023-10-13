package com.andersenlab.hotel.port.usecase;

import java.util.UUID;

public interface CalculateClientStayCurrentPrice {

    void calculate(UUID id);
}

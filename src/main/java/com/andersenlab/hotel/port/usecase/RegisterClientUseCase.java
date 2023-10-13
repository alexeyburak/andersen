package com.andersenlab.hotel.port.usecase;

import java.util.UUID;

public interface RegisterClientUseCase {

    void register(final UUID id, final String name);
}

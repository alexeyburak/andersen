package com.andersenlab.hotel.usecase;

import java.util.UUID;

public interface RegisterClientUseCase {

    void register(final UUID id, final String name);
}

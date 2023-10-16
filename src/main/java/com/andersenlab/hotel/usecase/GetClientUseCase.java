package com.andersenlab.hotel.usecase;

import com.andersenlab.hotel.model.ClientEntity;

import java.util.UUID;

public interface GetClientUseCase {

    ClientEntity getById(UUID id);
}

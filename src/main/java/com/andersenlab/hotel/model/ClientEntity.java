package com.andersenlab.hotel.model;

import java.util.UUID;

public record ClientEntity(UUID id, String name, ClientStatus status, java.util.Set<ApartmentEntity> apartments) {}
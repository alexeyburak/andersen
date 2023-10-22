package com.andersenlab.hotel.model;

import java.util.Set;
import java.util.UUID;

public record ClientEntity(UUID id, String name, ClientStatus status, Set<ApartmentEntity> apartments) {}
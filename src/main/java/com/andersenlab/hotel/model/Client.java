package com.andersenlab.hotel.model;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class Client {
    private UUID id;
    private String name;
    private ClientStatus status;
    private Set<ApartmentEntity> apartments;

    public Client(UUID id, String name, ClientStatus status, Set<ApartmentEntity> apartments) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.apartments = apartments;
    }

    public Client(UUID id, String name, ClientStatus status) {
        this(id, name, status, new HashSet<>());
    }

    public Client() {
        this(UUID.randomUUID(), "user", ClientStatus.NEW);
    }
}

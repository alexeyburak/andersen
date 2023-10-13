package com.andersenlab.hotel.domain;

import com.andersenlab.hotel.port.external.ClientStore;
import com.andersenlab.hotel.port.usecase.ListClientsUseCase;

import java.util.UUID;

final class Client {

    private final ClientStore.ClientEntity entity;

    Client(ClientStore.ClientEntity entity) {
        this.entity = entity;
    }

    Client(UUID id, String name) {
        this(new ClientStore.ClientEntity(id, name, ClientStore.ClientStatus.NEW));
    }

    ListClientsUseCase.ClientView view() {
        return new ListClientsUseCase.ClientView(
                entity.id(),
                entity.name(),
                ListClientsUseCase.ClientStatus.valueOf(entity.status().toString())
        );
    }

}

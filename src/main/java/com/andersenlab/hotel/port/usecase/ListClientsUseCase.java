package com.andersenlab.hotel.port.usecase;

import java.util.List;
import java.util.UUID;

public interface ListClientsUseCase {

    List<ClientView> list(Sort sort);

    enum Sort {
        ID, NAME, STATUS
    }

    enum ClientStatus {
        NEW
    }

    record ClientView(UUID id, String name, ClientStatus status) {}
}

package com.andersenlab.hotel.port.external;

import java.util.Collection;
import java.util.UUID;

public interface ClientStore {

    void save(ClientEntity clientEntity);

    Collection<ClientEntity> findAllSorted(Sort sort);

    void delete(UUID id);

    boolean has(UUID id);

    ClientEntity getById(UUID id);

    enum Sort {
        ID, NAME, STATUS
    }

    enum ClientStatus {
        NEW
    }

    record ClientEntity(UUID id, String name, ClientStatus status) {}
}

package com.andersenlab.hotel.repository;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;
import java.util.Comparator;
import java.util.UUID;

public interface ClientStore {

    void save(ClientEntity clientEntity);

    Collection<ClientEntity> findAllSorted(Sort sort);

    void delete(UUID id);

    boolean has(UUID id);

    ClientEntity getById(UUID id);

    @AllArgsConstructor
    @Getter
    enum Sort {
        ID(Comparator.comparing(ClientEntity::id)),
        NAME(Comparator.comparing(ClientEntity::name)),
        STATUS(Comparator.comparing(ClientEntity::status));

        private Comparator<ClientEntity> comparator;
    }

    enum ClientStatus { //TODO add
        NEW
    }

    record ClientEntity(UUID id, String name, ClientStatus status) {}
}

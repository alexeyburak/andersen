package com.andersenlab.hotel.repository;

import com.andersenlab.hotel.model.Client;
import com.andersenlab.hotel.model.ClientSort;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface ClientStore {

    void save(Client client);

    Collection<Client> findAllSorted(ClientSort sort);

    void delete(UUID id);

    boolean has(UUID id);

    Optional<Client> getById(UUID id);

    void update(Client client);
}

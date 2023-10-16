package com.andersenlab.hotel.repository.inmemory;

import com.andersenlab.hotel.model.Client;
import com.andersenlab.hotel.model.ClientSort;
import com.andersenlab.hotel.repository.ClientStore;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public final class InMemoryClientStore implements ClientStore {

    private final Map<UUID, Client> clients = new HashMap<>();

    @Override
    public void save(Client client) {
        clients.put(client.getId(), client);
    }

    @Override
    public void update(Client client) {
        clients.put(client.getId(), client);
    }

    @Override
    public Collection<Client> findAllSorted(ClientSort sort) {
        return clients.values()
                .stream()
                .sorted(sort.getComparator())
                .toList();
    }

    @Override
    public void delete(UUID id) {
        clients.remove(id);
    }

    @Override
    public boolean has(UUID id) {
        return clients.containsKey(id);
    }

    @Override
    public Optional<Client> getById(UUID id) {
        return Optional.ofNullable(clients.get(id));
    }
}


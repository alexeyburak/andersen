package com.andersenlab.hotel.service.factory;

import com.andersenlab.hotel.repository.ClientStore;

import java.util.List;
import java.util.UUID;

public class ClientFactory {
    private ClientFactory() {}

    public static ClientStore.ClientEntity createClient(List<String> args) {
        if(args.isEmpty()) {
            return createRandomClient();
        } else {
            return createClientFromArgs(args);
        }
    }

    public static ClientStore.ClientEntity createRandomClient() {
        UUID id = UUID.randomUUID();
        return new ClientStore.ClientEntity(id, "client-" + id.toString().substring(0,4),
                ClientStore.ClientStatus.NEW);
    }

    public static ClientStore.ClientEntity createClientFromArgs(List<String> args) {
        if (args.size() != 2) {
            throw new IllegalArgumentException("Illegal number of arguments");
        }
        return new ClientStore.ClientEntity(UUID.fromString(args.get(0)), args.get(1),
                ClientStore.ClientStatus.NEW);
    }
}

package com.andersenlab.hotel.service.factory;

import com.andersenlab.hotel.application.CustomErrorMessage;
import com.andersenlab.hotel.repository.ClientStore;

import java.util.List;
import java.util.UUID;

public class ClientFactory {

    private static final int VALID_ARGUMENTS_SIZE = 2;

    private ClientFactory() {}

    public static ClientStore.ClientEntity createClient(List<String> args) {
        return args.isEmpty() ? createRandomClient() : createClientFromArgs(args);
    }

    public static ClientStore.ClientEntity createRandomClient() {
        UUID id = UUID.randomUUID();

        return new ClientStore.ClientEntity(
                id,
                "client-" + id.toString().substring(0, 4),
                ClientStore.ClientStatus.NEW
        );
    }

    public static ClientStore.ClientEntity createClientFromArgs(List<String> args) {
        validateArgs(args);

        return new ClientStore.ClientEntity(
                UUID.fromString(args.get(0)), args.get(1),
                ClientStore.ClientStatus.NEW
        );
    }

    private static void validateArgs(List<String> args) {
        if (args.size() != VALID_ARGUMENTS_SIZE) {
            throw new IllegalArgumentException(CustomErrorMessage.ILLEGAL_NUMBER_OF_ARGUMENTS.getMessage());
        }
    }
}

package com.andersenlab.hotel.factory;

import com.andersenlab.hotel.CustomErrorMessage;
import com.andersenlab.hotel.model.Client;
import com.andersenlab.hotel.model.ClientStatus;

import java.util.List;
import java.util.UUID;

public class ClientFactory {

    private static final int VALID_ARGUMENTS_SIZE = 2;

    private ClientFactory() {}

    public static Client createClient(List<String> args) {
        validateArgs(args);

        return new Client(
                UUID.fromString(args.get(0)), args.get(1), ClientStatus.NEW
        );
    }

    private static void validateArgs(List<String> args) {
        if (args.size() != VALID_ARGUMENTS_SIZE) {
            throw new IllegalArgumentException(CustomErrorMessage.ILLEGAL_NUMBER_OF_ARGUMENTS.getMessage());
        }
    }
}

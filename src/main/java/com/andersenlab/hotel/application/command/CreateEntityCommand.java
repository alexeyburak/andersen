package com.andersenlab.hotel.application.command;

import com.andersenlab.hotel.application.CustomErrorMessage;
import com.andersenlab.hotel.repository.inmemory.InMemoryApartmentStore;
import com.andersenlab.hotel.repository.inmemory.InMemoryClientStore;
import com.andersenlab.hotel.service.factory.ClientFactory;
import com.andersenlab.hotel.service.factory.ApartmentFactory;

import java.io.PrintStream;
import java.util.List;

public final class CreateEntityCommand implements Command { //TODO Change to service

    private static final String APARTMENT = "apartment";
    private static final String CLIENT = "client";

    @Override
    public void execute(PrintStream output, List<String> arguments) {
        List<String> trimmed = arguments.subList(2, arguments.size());

        switch (arguments.get(1)) {
            case APARTMENT -> InMemoryApartmentStore.getInstance().save(ApartmentFactory.createApartment(trimmed));
            case CLIENT -> InMemoryClientStore.getInstance().save(ClientFactory.createClient(trimmed));
            default -> throw new IllegalArgumentException(CustomErrorMessage.UNKNOWN_ENTITY.getMessage());
        }
    }
}

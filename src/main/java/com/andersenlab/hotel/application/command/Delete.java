package com.andersenlab.hotel.application.command;

import com.andersenlab.hotel.repository.inmemory.InMemoryApartmentStore;
import com.andersenlab.hotel.repository.inmemory.InMemoryClientStore;

import java.io.PrintStream;
import java.util.List;
import java.util.UUID;

public class Delete implements Command {//TODO Change to service
    @Override
    public void execute(PrintStream output, List<String> arguments) {
        if (arguments.size() != 3) {
            throw new IllegalArgumentException("Invalid arguments quantity");
        }
        switch (arguments.get(1)) {
            case "apartment" -> InMemoryApartmentStore.getInstance().delete(UUID.fromString(arguments.get(2)));
            case "client" -> InMemoryClientStore.getInstance().delete(UUID.fromString(arguments.get(2)));
            default -> throw new IllegalArgumentException("Unknown argument");
        }
    }
}

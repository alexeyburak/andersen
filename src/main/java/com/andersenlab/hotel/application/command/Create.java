package com.andersenlab.hotel.application.command;

import com.andersenlab.hotel.repository.inmemory.InMemoryApartmentStore;
import com.andersenlab.hotel.repository.inmemory.InMemoryClientStore;
import com.andersenlab.hotel.service.EntityFactory;

import java.io.PrintStream;
import java.util.List;

public class Create implements Command { //TODO Change to service
    @Override
    public void execute(PrintStream output, List<String> arguments) {
        List<String> trimmed = arguments.subList(2, arguments.size());

        switch (arguments.get(1)) {
            case "apartment" -> InMemoryApartmentStore.getInstance().save(EntityFactory.createApartment(trimmed));
            case "client" -> InMemoryClientStore.getInstance().save(EntityFactory.createClient(trimmed));
            default -> throw new IllegalArgumentException("Unknown entity");
        }
    }
}

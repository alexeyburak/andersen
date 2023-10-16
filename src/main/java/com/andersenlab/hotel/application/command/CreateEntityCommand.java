package com.andersenlab.hotel.application.command;

import com.andersenlab.hotel.application.CustomErrorMessage;
import com.andersenlab.hotel.service.factory.ApartmentFactory;
import com.andersenlab.hotel.service.factory.ClientFactory;
import com.andersenlab.hotel.usecase.RegisterClientUseCase;
import com.andersenlab.hotel.usecase.SaveApartmentUseCase;
import lombok.AllArgsConstructor;

import java.io.PrintStream;
import java.util.List;

@AllArgsConstructor
public final class CreateEntityCommand implements Command {

    private static final String APARTMENT = "apartment";
    private static final String CLIENT = "client";

    private final RegisterClientUseCase registerClientUseCase;
    private final SaveApartmentUseCase saveApartmentUseCase;

    @Override
    public void execute(PrintStream output, List<String> arguments) {
        List<String> trimmed = arguments.subList(2, arguments.size());

        switch (arguments.get(1)) {
            case APARTMENT -> saveApartmentUseCase.save(ApartmentFactory.createApartment(trimmed));
            case CLIENT -> registerClientUseCase.register(ClientFactory.createClient(trimmed));
            default -> throw new IllegalArgumentException(CustomErrorMessage.UNKNOWN_ENTITY.getMessage());
        }
    }
}

package com.andersenlab.hotel.application.command;

import com.andersenlab.hotel.application.CustomErrorMessage;
import com.andersenlab.hotel.service.ApartmentService;
import com.andersenlab.hotel.service.ClientService;

import java.io.PrintStream;
import java.util.List;
import java.util.UUID;

public final class GetEntityCommand implements Command, ArgumentsValidator<String> {

    private static final int VALID_ARGUMENTS_SIZE = 3;
    private static final String APARTMENT = "apartment";
    private static final String CLIENT = "client";

    @Override
    public void execute(PrintStream output, List<String> arguments) {
        validateArguments(arguments);

        switch (arguments.get(1)) {
            case APARTMENT ->
                    output.println(ApartmentService.getInstance().getById(UUID.fromString(arguments.get(2))));
            case CLIENT ->
                    output.println(ClientService.getInstance().getById(UUID.fromString(arguments.get(2))));
            default -> throw new IllegalArgumentException(CustomErrorMessage.WRONG_ARGUMENTS.getMessage());
        }
    }

    @Override
    public void validateArguments(List<String> arguments) throws IllegalArgumentException {
        if (arguments.size() != VALID_ARGUMENTS_SIZE) {
            throw new IllegalArgumentException(CustomErrorMessage.INVALID_ARGUMENTS_QUANTITY.getMessage());
        }
    }
}

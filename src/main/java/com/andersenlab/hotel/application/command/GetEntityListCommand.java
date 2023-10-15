package com.andersenlab.hotel.application.command;

import com.andersenlab.hotel.application.CustomErrorMessage;
import com.andersenlab.hotel.service.ApartmentService;
import com.andersenlab.hotel.service.ClientService;
import com.andersenlab.hotel.usecase.ListApartmentsUseCase;
import com.andersenlab.hotel.usecase.ListClientsUseCase;
import org.apache.commons.lang3.EnumUtils;

import java.io.PrintStream;
import java.util.List;

public final class GetEntityListCommand implements Command, ArgumentsValidator<String> {

    private static final int VALID_ARGUMENTS_SIZE = 3;
    private static final String APARTMENT = "apartment";
    private static final String CLIENT = "client";

    @Override
    public void execute(PrintStream output, List<String> arguments) {
        validateArguments(arguments);

        switch (arguments.get(1)) {
            case APARTMENT -> {
                ListApartmentsUseCase.Sort sort =
                        EnumUtils.getEnum(ListApartmentsUseCase.Sort.class, arguments.get(2));
                ApartmentService.getInstance().list(sort).forEach(output::println);
            }
            case CLIENT -> {
                ListClientsUseCase.Sort sort =
                        EnumUtils.getEnum(ListClientsUseCase.Sort.class, arguments.get(2));
                ClientService.getInstance().list(sort).forEach(output::println);
            }
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

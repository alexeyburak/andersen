package com.andersenlab.hotel.application.command;

import com.andersenlab.hotel.application.CustomErrorMessage;
import com.andersenlab.hotel.model.ApartmentSort;
import com.andersenlab.hotel.model.ClientSort;
import com.andersenlab.hotel.usecase.ListApartmentsUseCase;
import com.andersenlab.hotel.usecase.ListClientsUseCase;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.EnumUtils;

import java.io.PrintStream;
import java.util.List;

@AllArgsConstructor
public final class GetEntityListCommand implements Command, ArgumentsValidator<String> {

    private static final int VALID_ARGUMENTS_SIZE = 3;
    private static final String APARTMENT = "apartment";
    private static final String CLIENT = "client";

    private final ListClientsUseCase listClientsUseCase;
    private final ListApartmentsUseCase listApartmentsUseCase;

    @Override
    public void execute(PrintStream output, List<String> arguments) {
        validateArguments(arguments);

        switch (arguments.get(1)) {
            case APARTMENT -> {
                ApartmentSort sort = EnumUtils.getEnum(ApartmentSort.class, arguments.get(2).toUpperCase());
                listApartmentsUseCase.list(sort).forEach(output::println);
            }
            case CLIENT -> {
                ClientSort sort = EnumUtils.getEnum(ClientSort.class, arguments.get(2).toUpperCase());
                listClientsUseCase.list(sort).forEach(output::println);
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

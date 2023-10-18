package com.andersenlab.hotel.application.command.crud;

import com.andersenlab.hotel.application.CustomErrorMessage;
import com.andersenlab.hotel.application.command.ApplicationCommand;
import com.andersenlab.hotel.application.command.ArgumentsValidator;
import com.andersenlab.hotel.application.command.Command;
import com.andersenlab.hotel.model.Entity;
import com.andersenlab.hotel.repository.ApartmentSort;
import com.andersenlab.hotel.repository.ClientSort;
import com.andersenlab.hotel.usecase.ListApartmentsUseCase;
import com.andersenlab.hotel.usecase.ListClientsUseCase;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.EnumUtils;

import java.io.PrintStream;
import java.util.List;

@AllArgsConstructor
public final class GetEntityListCommand implements Command, ArgumentsValidator<String> {

    private static final int VALID_ARGUMENTS_SIZE = 3;
    private static final ApplicationCommand APPLICATION_COMMAND = ApplicationCommand.GET_ALL;

    private final ListClientsUseCase listClientsUseCase;
    private final ListApartmentsUseCase listApartmentsUseCase;

    @Override
    public ApplicationCommand getApplicationCommand() {
        return APPLICATION_COMMAND;
    }

    @Override
    public void execute(PrintStream output, List<String> arguments) {
        validateArguments(arguments);
        Entity chosenEntity = EnumUtils.getEnum(Entity.class, arguments.get(1).toUpperCase());
        switch (chosenEntity) {
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

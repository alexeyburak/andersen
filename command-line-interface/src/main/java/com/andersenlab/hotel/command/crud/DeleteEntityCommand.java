package com.andersenlab.hotel.command.crud;

import com.andersenlab.hotel.CustomErrorMessage;
import com.andersenlab.hotel.command.ApplicationCommand;
import com.andersenlab.hotel.command.ValidatingArgumentsCommand;
import com.andersenlab.hotel.model.Apartment;
import com.andersenlab.hotel.model.ApartmentEntity;
import com.andersenlab.hotel.model.Client;
import com.andersenlab.hotel.model.ClientEntity;
import com.andersenlab.hotel.model.Entity;
import com.andersenlab.hotel.service.CrudService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.EnumUtils;

import java.io.PrintStream;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public final class DeleteEntityCommand extends ValidatingArgumentsCommand {

    private static final ApplicationCommand APPLICATION_COMMAND = ApplicationCommand.DELETE;
    private static final int VALID_ARGUMENTS_SIZE = 3;

    private final CrudService<Client, ClientEntity> clientService;
    private final CrudService<Apartment, ApartmentEntity> apartmentService;

    @Override
    public ApplicationCommand getApplicationCommand() {
        return APPLICATION_COMMAND;
    }

    @Override
    public void process(PrintStream output, List<String> arguments) {
        Entity chosenEntity = EnumUtils.getEnum(Entity.class, arguments.get(1).toUpperCase());
        switch (chosenEntity) {
            case APARTMENT -> apartmentService.delete(UUID.fromString(arguments.get(2)));
            case CLIENT -> clientService.delete(UUID.fromString(arguments.get(2)));
            default -> throw new IllegalArgumentException(CustomErrorMessage.WRONG_ARGUMENTS.getMessage());
        }
        output.printf("%s with id:%s was deleted", chosenEntity.name(), arguments.get(2));
    }

    @Override
    public void validate(List<String> arguments) throws IllegalArgumentException {
        if (arguments.size() != VALID_ARGUMENTS_SIZE) {
            throw new IllegalArgumentException(CustomErrorMessage.INVALID_ARGUMENTS_QUANTITY.getMessage());
        }
    }
}

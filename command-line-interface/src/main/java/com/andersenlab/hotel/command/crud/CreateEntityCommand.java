package com.andersenlab.hotel.command.crud;

import com.andersenlab.hotel.CustomErrorMessage;
import com.andersenlab.hotel.command.ApplicationCommand;
import com.andersenlab.hotel.command.Command;
import com.andersenlab.hotel.factory.ApartmentFactory;
import com.andersenlab.hotel.factory.ClientFactory;
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

@AllArgsConstructor
public final class CreateEntityCommand implements Command {

    private static final ApplicationCommand APPLICATION_COMMAND = ApplicationCommand.CREATE;

    private final CrudService<Client, ClientEntity> clientService;
    private final CrudService<Apartment, ApartmentEntity> apartmentService;

    @Override
    public ApplicationCommand getApplicationCommand() {
        return APPLICATION_COMMAND;
    }

    @Override
    public void execute(PrintStream output, List<String> arguments) {
        List<String> trimmed = arguments.subList(2, arguments.size());
        Entity chosenEntity = EnumUtils.getEnum(Entity.class, arguments.get(1).toUpperCase());
        switch (chosenEntity) {
            case APARTMENT -> apartmentService.save(ApartmentFactory.createApartment(trimmed));
            case CLIENT -> clientService.save(ClientFactory.createClient(trimmed));
            default -> throw new IllegalArgumentException(CustomErrorMessage.UNKNOWN_ENTITY.getMessage());
        }
        output.printf("%s was created", chosenEntity.name());
    }
}

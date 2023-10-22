package com.andersenlab.hotel.application.command;

import com.andersenlab.hotel.application.HotelModule;

import com.andersenlab.hotel.application.command.additional.AdjustApartmentPriceCommand;
import com.andersenlab.hotel.application.command.additional.CalculateClientStayCurrentPriceCommand;
import com.andersenlab.hotel.application.command.additional.CheckInClientCommand;
import com.andersenlab.hotel.application.command.additional.CheckOutClientCommand;
import com.andersenlab.hotel.application.command.additional.ExitApplicationCommand;
import com.andersenlab.hotel.application.command.additional.HelpCommand;
import com.andersenlab.hotel.application.command.crud.CreateEntityCommand;
import com.andersenlab.hotel.application.command.crud.DeleteEntityCommand;
import com.andersenlab.hotel.application.command.crud.GetEntityCommand;
import com.andersenlab.hotel.application.command.crud.GetEntityListCommand;

import java.util.List;

public class CommandsCreator {
    private CommandsCreator() {}
    public static List<Command> getCommands(HotelModule module) {
        return List.of(
                new CreateEntityCommand(module.clientService(), module.apartmentService()),
                new DeleteEntityCommand(module.clientService(), module.apartmentService()),
                new GetEntityCommand(module.clientService(), module.apartmentService()),
                new GetEntityListCommand(module.listClientsUseCase(), module.listApartmentsUseCase()),
                new AdjustApartmentPriceCommand(module.adjustApartmentPriceUseCase()),
                new CalculateClientStayCurrentPriceCommand(module.calculateClientStayCurrentPriceUseCase()),
                new CheckInClientCommand(module.checkInClientUseCase()),
                new CheckOutClientCommand(module.checkOutClientUseCase()),
                new HelpCommand(),
                new ExitApplicationCommand()
        );
    }
    public static List<? extends Command> decorateCommands(List<Command> commandList){
        return commandList.stream().map(command ->
                new BusinessExceptionHandlingCommand(new GenericExceptionHandlingCommand(command))).toList();
    }
}

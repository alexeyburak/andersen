package com.andersenlab.hotel.application.command;

import com.andersenlab.hotel.application.HotelModule;
import com.andersenlab.hotel.application.command.additional.*;
import com.andersenlab.hotel.application.command.crud.CreateEntityCommand;
import com.andersenlab.hotel.application.command.crud.DeleteEntityCommand;
import com.andersenlab.hotel.application.command.crud.GetEntityCommand;
import com.andersenlab.hotel.application.command.crud.GetEntityListCommand;

import java.util.List;

public class CommandsCreator {
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
                new ExitApplicationCommand()
        );
    }
    //returns List<BusinessExceptionHandlingCommand>, not the List<Command>
    public static List<? extends Command> decorateCommands(List<Command> commandList){
        return commandList.stream().map(command ->
                new BusinessExceptionHandlingCommand(new GenericExceptionHandlingCommand(command))).toList();
    }
}

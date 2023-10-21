package com.andersenlab.hotel;

import com.andersenlab.hotel.application.HotelModule;
import com.andersenlab.hotel.application.command.Command;
import com.andersenlab.hotel.application.command.CommandStarter;
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
import com.andersenlab.hotel.model.Apartment;
import com.andersenlab.hotel.model.Client;
import com.andersenlab.hotel.repository.ApartmentSort;
import com.andersenlab.hotel.repository.ClientSort;
import com.andersenlab.hotel.repository.SortableCrudRepository;
import com.andersenlab.hotel.repository.inmemory.InMemoryApartmentRepository;
import com.andersenlab.hotel.repository.inmemory.InMemoryClientRepository;
import com.andersenlab.hotel.service.impl.ApartmentService;
import com.andersenlab.hotel.service.impl.ClientService;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        final HotelModule context = initContext();
        final CommandStarter starter = new CommandStarter(System.in, System.out, getCommands(context));

        starter.run();
    }

    public static HotelModule initContext() {
        final SortableCrudRepository<Apartment, ApartmentSort> apartmentRepository = new InMemoryApartmentRepository();
        final SortableCrudRepository<Client, ClientSort> clientRepository = new InMemoryClientRepository();
        final ApartmentService apartmentService = new ApartmentService(apartmentRepository);
        final ClientService clientService = new ClientService(clientRepository, apartmentService);

        return new HotelModule(clientService, apartmentService, apartmentService,
                clientService, clientService, clientService, apartmentService,
                clientService);
    }

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
}
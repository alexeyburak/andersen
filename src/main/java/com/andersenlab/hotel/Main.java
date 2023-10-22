package com.andersenlab.hotel;

import com.andersenlab.hotel.application.HotelModule;
import com.andersenlab.hotel.application.command.Command;
import com.andersenlab.hotel.application.command.CommandStarter;
import com.andersenlab.hotel.application.command.CommandsCreator;
import com.andersenlab.hotel.model.Apartment;
import com.andersenlab.hotel.model.Client;
import com.andersenlab.hotel.repository.ApartmentSort;
import com.andersenlab.hotel.repository.ClientSort;
import com.andersenlab.hotel.repository.SortableCrudRepository;
import com.andersenlab.hotel.repository.infile.InFileApartmentRepository;
import com.andersenlab.hotel.repository.infile.InFileClientRepository;
import com.andersenlab.hotel.service.impl.ApartmentService;
import com.andersenlab.hotel.service.impl.ClientService;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        final HotelModule context = initContext();

        List<? extends Command> listCommands = CommandsCreator.decorateCommands(CommandsCreator.getCommands(context));
        final CommandStarter starter = new CommandStarter(System.in, System.out, listCommands);

        starter.run();
    }

    public static HotelModule initContext() {
        final File file = new File("D://database.json");
        final SortableCrudRepository<Apartment, ApartmentSort> apartmentRepository = new InFileApartmentRepository(file);
        final SortableCrudRepository<Client, ClientSort> clientRepository = new InFileClientRepository(file);
        final ApartmentService apartmentService = new ApartmentService(apartmentRepository);
        final ClientService clientService = new ClientService(clientRepository, apartmentService);

        return new HotelModule(clientService, apartmentService, apartmentService,
                clientService, clientService, clientService, apartmentService,
                clientService);
    }

}
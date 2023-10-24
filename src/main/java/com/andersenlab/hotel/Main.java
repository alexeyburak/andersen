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
import com.andersenlab.hotel.application.propertyReaders.PropertyReaderFromFile;
import com.andersenlab.hotel.repository.infile.InFileApartmentRepository;
import com.andersenlab.hotel.repository.infile.InFileClientRepository;
import com.andersenlab.hotel.service.impl.ApartmentService;
import com.andersenlab.hotel.service.impl.ClientService;
import com.andersenlab.hotel.usecase.CheckInClientUseCase;
import com.andersenlab.hotel.usecase.CheckOutClientUseCase;
import com.andersenlab.hotel.usecase.impl.BlockedCheckIn;
import com.andersenlab.hotel.usecase.impl.BlockedCheckOut;
import lombok.SneakyThrows;

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
        PropertyReaderFromFile propertyReaderFromFile = new PropertyReaderFromFile("application.properties");
        String location = propertyReaderFromFile.readProperty("location");
        String abilityApartmentToChange = propertyReaderFromFile.readProperty("ability-apartment-to-change");

        final File file = fileLoader(location);
        final SortableCrudRepository<Apartment, ApartmentSort> apartmentRepository = new InFileApartmentRepository(file);
        final SortableCrudRepository<Client, ClientSort> clientRepository = new InFileClientRepository(file);

        final ApartmentService apartmentService = new ApartmentService(apartmentRepository);
        final ClientService clientService = new ClientService(clientRepository, apartmentService);

        CheckInClientUseCase checkInClientUseCase;
        CheckOutClientUseCase checkOutClientUseCase;

        if(Boolean.parseBoolean(abilityApartmentToChange)) {
            checkInClientUseCase = clientService;
            checkOutClientUseCase = clientService;
        } else {
            checkInClientUseCase = new BlockedCheckIn();
            checkOutClientUseCase = new BlockedCheckOut();
        }

        return new HotelModule(clientService, apartmentService, apartmentService,
                clientService, checkOutClientUseCase, checkInClientUseCase, apartmentService,
                clientService);
    }

    @SneakyThrows
    public static File fileLoader(String path) {
        final File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }
}
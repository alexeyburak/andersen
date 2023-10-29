package com.andersenlab.hotel;

import com.andersenlab.hotel.http.ServletStarter;
import com.andersenlab.hotel.model.Apartment;
import com.andersenlab.hotel.model.ApartmentSort;
import com.andersenlab.hotel.model.Client;
import com.andersenlab.hotel.model.ClientSort;
import com.andersenlab.hotel.reader.PropertyReaderFromFile;
import com.andersenlab.hotel.repository.SortableCrudRepository;
import com.andersenlab.hotel.repository.infile.InFileApartmentRepository;
import com.andersenlab.hotel.repository.jdbc.JdbcClientRepository;
import com.andersenlab.hotel.repository.jdbc.JdbcConnector;
import com.andersenlab.hotel.service.impl.ApartmentService;
import com.andersenlab.hotel.service.impl.ClientService;
import com.andersenlab.hotel.usecase.CheckInClientUseCase;
import com.andersenlab.hotel.usecase.CheckOutClientUseCase;
import com.andersenlab.hotel.usecase.impl.BlockedCheckIn;
import com.andersenlab.hotel.usecase.impl.BlockedCheckOut;
import lombok.SneakyThrows;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        final HotelModule context = initContext();
        getStarter(context).run();
    }

    public static ServletStarter getStarter(HotelModule context) {
        return ServletStarter.forModule(context);
    }

    public static HotelModule initContext() {
        PropertyReaderFromFile propertyReaderFromFile = new PropertyReaderFromFile("application.properties");
        String location = propertyReaderFromFile.readProperty("location");
        String abilityApartmentToChange = propertyReaderFromFile.readProperty("apartment.change.enabled");
        String jdbcUrl = propertyReaderFromFile.readProperty("jdbc.url");
        String jdbcUser = propertyReaderFromFile.readProperty("jdbc.user");
        String jdbcPassword = propertyReaderFromFile.readProperty("jdbc.password");
        JdbcConnector jdbc = new JdbcConnector(jdbcUrl, jdbcUser, jdbcPassword)
                .migrate();

        final File file = getFile(location);
        final SortableCrudRepository<Apartment, ApartmentSort> apartmentRepository = new InFileApartmentRepository(file);
        final SortableCrudRepository<Client, ClientSort> clientRepository = new JdbcClientRepository(jdbc);

        final ApartmentService apartmentService = new ApartmentService(apartmentRepository);
        final ClientService clientService = new ClientService(clientRepository, apartmentService);

        CheckInClientUseCase checkInClientUseCase;
        CheckOutClientUseCase checkOutClientUseCase;

        if (Boolean.parseBoolean(abilityApartmentToChange)) {
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
    public static File getFile(String path) {
        final File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }
}
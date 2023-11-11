package com.andersenlab.hotel;

import com.andersenlab.hotel.model.*;
import com.andersenlab.hotel.reader.PropertyReaderFromFile;
import com.andersenlab.hotel.repository.SortableCrudRepository;
import com.andersenlab.hotel.repository.infile.InFileClientRepository;
import com.andersenlab.hotel.repository.jdbc.ApartmentJdbcRepository;
import com.andersenlab.hotel.repository.jpa.ApartmentJpaRepository;
import com.andersenlab.hotel.service.impl.ApartmentService;
import com.andersenlab.hotel.service.impl.ClientService;
import com.andersenlab.hotel.http.ServletStarter;
import com.andersenlab.hotel.usecase.CheckInClientUseCase;
import com.andersenlab.hotel.usecase.CheckOutClientUseCase;
import com.andersenlab.hotel.usecase.impl.BlockedCheckIn;
import com.andersenlab.hotel.usecase.impl.BlockedCheckOut;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.SneakyThrows;
import java.io.File;

public class Main {

    public static void main(String[] args){

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

        final File file = getFile(location);

        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistence");
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        final SortableCrudRepository<Apartment, ApartmentSort> apartmentRepository = new ApartmentJpaRepository(entityManagerFactory, entityManager);
        final SortableCrudRepository<Client, ClientSort> clientRepository = new InFileClientRepository(file);

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
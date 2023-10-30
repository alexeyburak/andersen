package com.andersenlab.hotel.service;

import com.andersenlab.hotel.HotelModule;
import com.andersenlab.hotel.model.Apartment;
import com.andersenlab.hotel.model.ApartmentSort;
import com.andersenlab.hotel.model.Client;
import com.andersenlab.hotel.model.ClientSort;
import com.andersenlab.hotel.repository.SortableCrudRepository;
import com.andersenlab.hotel.repository.infile.InFileApartmentRepository;
import com.andersenlab.hotel.repository.infile.InFileClientRepository;
import com.andersenlab.hotel.repository.inmemory.InMemoryApartmentRepository;
import com.andersenlab.hotel.repository.inmemory.InMemoryClientRepository;
import com.andersenlab.hotel.service.impl.ApartmentService;
import com.andersenlab.hotel.service.impl.ClientService;
import com.andersenlab.hotel.usecase.CheckInClientUseCase;
import com.andersenlab.hotel.usecase.CheckOutClientUseCase;
import com.andersenlab.hotel.usecase.impl.BlockedCheckIn;
import com.andersenlab.hotel.usecase.impl.BlockedCheckOut;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;

public class ContextBuilder {

    private static final Logger LOG = LoggerFactory.getLogger(ContextBuilder.class);

    private SortableCrudRepository<Apartment, ApartmentSort> apartmentRepository;
    private SortableCrudRepository<Client, ClientSort> clientRepository;

    private ClientService clientService;
    private ApartmentService apartmentService;

    private CheckInClientUseCase checkInClientUseCase;
    private CheckOutClientUseCase checkOutClientUseCase;


    public ContextBuilder() {
        this.clientRepository = new InMemoryClientRepository();
        this.apartmentRepository = new InMemoryApartmentRepository();
    }

    @SneakyThrows
    public ContextBuilder initFile(final String filePath) {
        final File file = new File(filePath);
        if (!file.exists()) {
            file.createNewFile();

            try (FileWriter writer = new FileWriter(file)) {
                writer.write("{\"apartments\":{},\"clients\":{}}");
            }
        }
        return this.initInFileRepositories(file);
    }

    private ContextBuilder initInFileRepositories(final File file) {
        this.apartmentRepository = new InFileApartmentRepository(file);
        this.clientRepository = new InFileClientRepository(file);

        LOG.info("File initialized. In file repositories was chosen");

        return this;
    }

    public ContextBuilder initServices() {
        this.apartmentService = new ApartmentService(apartmentRepository);
        this.clientService = new ClientService(clientRepository, apartmentService);
        return this;
    }

    public ContextBuilder initCheckInCheckOut(boolean changeability) {
        if (changeability) {
            this.checkInClientUseCase = clientService;
            this.checkOutClientUseCase = clientService;

            LOG.info("Changeable apartment services was chosen");
        } else {
            this.checkInClientUseCase = new BlockedCheckIn();
            this.checkOutClientUseCase = new BlockedCheckOut();

            LOG.info("Unchangeable apartment services was chosen");
        }
        return this;
    }

    public HotelModule build() {
        return new HotelModule(clientService, apartmentService, apartmentService,
                clientService, checkOutClientUseCase, checkInClientUseCase, apartmentService,
                clientService);
    }
}

package com.andersenlab.hotel.service;

import com.andersenlab.hotel.model.Apartment;
import com.andersenlab.hotel.model.ApartmentEntity;
import com.andersenlab.hotel.model.ApartmentStatus;
import com.andersenlab.hotel.model.Client;
import com.andersenlab.hotel.model.ClientEntity;
import com.andersenlab.hotel.model.ClientSort;
import com.andersenlab.hotel.model.ClientStatus;
import com.andersenlab.hotel.repository.ClientStore;
import com.andersenlab.hotel.usecase.CalculateClientStayCurrentPriceUseCase;
import com.andersenlab.hotel.usecase.CheckInClientUseCase;
import com.andersenlab.hotel.usecase.CheckOutClientUseCase;
import com.andersenlab.hotel.usecase.DeleteClientUseCase;
import com.andersenlab.hotel.usecase.GetClientUseCase;
import com.andersenlab.hotel.usecase.ListClientsUseCase;
import com.andersenlab.hotel.usecase.RegisterClientUseCase;
import com.andersenlab.hotel.usecase.UpdateClientUseCase;
import com.andersenlab.hotel.usecase.exception.ClientNotfoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;

public final class ClientService
        implements CalculateClientStayCurrentPriceUseCase,
        CheckInClientUseCase,
        CheckOutClientUseCase,
        ListClientsUseCase,
        RegisterClientUseCase,
        GetClientUseCase,
        DeleteClientUseCase,
        UpdateClientUseCase {
    private static final Logger LOG = LoggerFactory.getLogger(ClientService.class);

    private final Function<Client, ClientEntity> toEntityMapper = (client ->
            new ClientEntity(client.getId(), client.getName(), client.getStatus(), client.getApartments())
    );
    private final ClientStore store;
    private final ApartmentService apartmentService;

    public ClientService(final ClientStore store, final ApartmentService apartmentService) {
        this.store = store;
        this.apartmentService = apartmentService;
    }

    @Override
    public double calculatePrice(UUID id) {
        LOG.info("Calculate client price. ID: {}", id);
        return getById(id)
                .apartments()
                .stream()
                .mapToDouble(apartment -> apartment.price().doubleValue())
                .sum();
    }

    @Override
    public void checkIn(UUID clientId, UUID apartmentId) {
        ClientEntity client = getById(clientId);
        ApartmentEntity apartment = apartmentService.getById(apartmentId);

        apartmentService.update(
                new Apartment(apartment.id(), apartment.price(), apartment.capacity(),
                        false, ApartmentStatus.RESERVED)
        );
        client.apartments().add(apartmentService.getById(apartmentId));
        LOG.info("Check in client. Client ID: {}, Apartment ID: {}", clientId, apartmentId);
    }

    @Override
    public List<ClientEntity> list(ClientSort sort) {
        return store.findAllSorted(ClientSort.valueOf(sort.toString()))
                .stream()
                .map(toEntityMapper)
                .toList();
    }

    @Override
    public void register(Client client) {
        UUID id = client.getId();

        store.save(
                new Client(id, client.getName(), ClientStatus.NEW)
        );
        LOG.info("Register new client. ID: {}", id);
    }

    @Override
    public void checkOut(UUID clientId, UUID apartmentId) {
        ClientEntity client = getById(clientId);
        ApartmentEntity apartment = apartmentService.getById(apartmentId);

        boolean ifClientHasApartment = client.apartments().remove(apartment);
        if (ifClientHasApartment) {
            apartmentService.update(
                    new Apartment(apartment.id(), apartment.price(), apartment.capacity(),
                            true, ApartmentStatus.AVAILABLE)
            );
        }
        LOG.info("Check out client. Client ID: {}, Apartment ID: {}", clientId, apartmentId);
    }

    @Override
    public ClientEntity getById(UUID id) {
        return store.getById(id)
                .map(toEntityMapper)
                .orElseThrow(ClientNotfoundException::new);
    }

    @Override
    public void delete(UUID id) {
        store.delete(id);
        LOG.info("Delete client. ID: {}", id);
    }

    @Override
    public void update(Client client) {
        store.update(client);
        LOG.info("Update client. ID: {}", client.getId());
    }
}

package com.andersenlab.hotel.service;

import com.andersenlab.hotel.model.*;
import com.andersenlab.hotel.repository.inmemory.InMemoryClientStore;
import com.andersenlab.hotel.usecase.CalculateClientStayCurrentPriceUseCase;
import com.andersenlab.hotel.usecase.CheckInClientUseCase;
import com.andersenlab.hotel.usecase.CheckOutClientUseCase;
import com.andersenlab.hotel.usecase.ListClientsUseCase;
import com.andersenlab.hotel.usecase.RegisterClientUseCase;
import com.andersenlab.hotel.usecase.exception.ClientIsAlreadyExistsException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

public final class ClientService
        implements CalculateClientStayCurrentPriceUseCase,
        CheckInClientUseCase,
        CheckOutClientUseCase,
        ListClientsUseCase,
        RegisterClientUseCase {

    Function<Client, ClientEntity> clientClientEntityFunction = (client ->
            new ClientEntity(client.getId(), client.getName(), client.getStatus(), client.getApartments()));
    private final InMemoryClientStore clientStore;
    private final ApartmentService apartmentService;
    private static ClientService instance;

    protected ClientService() {
        this.clientStore = InMemoryClientStore.getInstance();
        this.apartmentService = ApartmentService.getInstance();
    }

    public static ClientService getInstance() {
        if (instance == null) {
            instance = new ClientService();
        }
        return instance;
    }

    @Override
    public void calculatePrice(UUID id) {
        if (!clientStore.hasIn(id)) {
            throw new IllegalArgumentException("Wrong id");
        }
        clientStore.getById(id).ifPresent(client ->
                System.out.println("Price for all apartments: " +
                        client.getApartments().stream().mapToDouble(apartment -> apartment.price().doubleValue()).sum())
        );
    }

    @Override
    public void checkIn(UUID clientId, UUID apartmentId) {
        if (!clientStore.hasIn(clientId) || !apartmentService.hasIn(apartmentId)) {
            throw new IllegalArgumentException("Wrong id");
        }
        Optional<Client> clientOpt = clientStore.getById(clientId);
        Optional<ApartmentEntity> apartmentOpt = apartmentService.getById(apartmentId);
        if(clientOpt.isPresent() && apartmentOpt.isPresent()) {
            ApartmentEntity apartment = apartmentOpt.get();
            apartmentService.update(new Apartment(apartment.id(), apartment.price(), apartment.capacity(),
                    false, ApartmentStatus.RESERVED));
            clientOpt.ifPresent(client -> client.getApartments()
                    .add(apartmentService.getById(apartmentId).get()));
        }
    }

    @Override
    public List<ClientEntity> list(ClientSort sort) {
        return clientStore.findAllSorted(ClientSort.valueOf(sort.toString()))
                .stream()
                .map(clientClientEntityFunction)
                .toList();
    }

    @Override
    public void register(UUID id, String name) {
        if (clientStore.hasIn(id)) {
            throw new ClientIsAlreadyExistsException();
        }
        clientStore.save(new Client(id, name, ClientStatus.NEW));
    }

    @Override
    public void checkOut(UUID clientId, UUID apartmentId) {
        Client client = clientStore.getById(clientId).orElseThrow(() -> new IllegalArgumentException("Wrong client id"));
        ApartmentEntity apartment = apartmentService.getById(apartmentId).orElseThrow(() -> new IllegalArgumentException("Wrong apartmentId"));
        if (client.getApartments().remove(apartment)) {
            apartmentService.update(new Apartment(apartment.id(), apartment.price(), apartment.capacity(),
                    true, ApartmentStatus.AVAILABLE));
            System.out.println("Apartment was removed");
        } else {
            System.out.println("Apartment wasn`t removed");
        }
    }

    public void save(Client cl) {
        register(cl.getId(), cl.getName());
    }

    public Optional<ClientEntity> getById(UUID id){
        return clientStore.getById(id).map(clientClientEntityFunction);
    }

    public void delete(UUID clientId){
        clientStore.delete(clientId);
    }
}

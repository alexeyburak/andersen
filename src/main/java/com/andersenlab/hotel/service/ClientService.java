package com.andersenlab.hotel.service;

import com.andersenlab.hotel.model.Client;
import com.andersenlab.hotel.repository.inmemory.InMemoryClientStore;
import com.andersenlab.hotel.repository.ClientStore;
import com.andersenlab.hotel.usecase.CalculateClientStayCurrentPriceUseCase;
import com.andersenlab.hotel.usecase.CheckInClientUseCase;
import com.andersenlab.hotel.usecase.CheckOutClientUseCase;
import com.andersenlab.hotel.usecase.ListClientsUseCase;
import com.andersenlab.hotel.usecase.RegisterClientUseCase;
import com.andersenlab.hotel.usecase.exception.ClientIsAlreadyExistsException;

import java.util.List;
import java.util.UUID;

public final class ClientService
        implements CalculateClientStayCurrentPriceUseCase,
        CheckInClientUseCase,
        CheckOutClientUseCase,
        ListClientsUseCase,
        RegisterClientUseCase {

    private final InMemoryClientStore clientStore;
    private static ClientService instance;

    protected ClientService() {
        this.clientStore = InMemoryClientStore.getInstance();
    }

    public static ClientService getInstance() {
        if (instance == null) {
            instance = new ClientService();
        }
        return instance;
    }

    @Override
    public void calculatePrice(UUID id) {

    }

    @Override
    public void checkIn(UUID clientId, UUID apartmentId) {


    }

//    @Override
//    public void checkOut(UUID clientId, UUID apartmentId) {
//        com.andersenlab.hotel.model.Client client = clientStore.getById(clientId).orElseThrow(()->new IllegalArgumentException("Wrong client id"));
//    }

    @Override
    public List<ClientView> list(Sort sort) {
        return clientStore.findAllSorted(ClientStore.Sort.valueOf(sort.toString()))
                .stream()
                .map(Client::new)
                .map(Client::view)
                .toList();
    }

    @Override
    public void register(UUID id, String name) {
        if (clientStore.hasIn(id)) {
            throw new ClientIsAlreadyExistsException();
        }
    }

    @Override
    public void checkOut(UUID clientId, UUID apartmentId) {

    }
}

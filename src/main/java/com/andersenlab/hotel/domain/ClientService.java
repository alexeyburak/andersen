package com.andersenlab.hotel.domain;

import com.andersenlab.hotel.port.external.ClientStore;
import com.andersenlab.hotel.port.usecase.CalculateClientStayCurrentPrice;
import com.andersenlab.hotel.port.usecase.CheckInClientUseCase;
import com.andersenlab.hotel.port.usecase.CheckOutClientUseCase;
import com.andersenlab.hotel.port.usecase.ListClientsUseCase;
import com.andersenlab.hotel.port.usecase.RegisterClientUseCase;

import java.util.List;
import java.util.UUID;

final class ClientService
        implements CalculateClientStayCurrentPrice,
        CheckInClientUseCase,
        CheckOutClientUseCase,
        ListClientsUseCase,
        RegisterClientUseCase {

    private final ClientStore clientStore;

    ClientService(ClientStore clientStore) {
        this.clientStore = clientStore;
    }

    @Override
    public void calculate(UUID id) {

    }

    @Override
    public void checkIn(UUID clientId, UUID apartmentId) {

    }

    @Override
    public void checkOut(UUID clientId, UUID apartmentId) {

    }

    @Override
    public List<ClientView> list(Sort sort) {
        return null;
    }

    @Override
    public void register(UUID id, String name) {

    }

}

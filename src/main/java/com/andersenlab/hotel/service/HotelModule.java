package com.andersenlab.hotel.service;

import com.andersenlab.hotel.repository.ApartmentStore;
import com.andersenlab.hotel.repository.ClientStore;
import com.andersenlab.hotel.service.ApartmentService;
import com.andersenlab.hotel.service.ClientService;
import com.andersenlab.hotel.usecase.AddApartmentUseCase;
import com.andersenlab.hotel.usecase.AdjustApartmentPriceUseCase;
import com.andersenlab.hotel.usecase.CalculateClientStayCurrentPriceUseCase;
import com.andersenlab.hotel.usecase.CheckInClientUseCase;
import com.andersenlab.hotel.usecase.CheckOutClientUseCase;
import com.andersenlab.hotel.usecase.ListApartmentsUseCase;
import com.andersenlab.hotel.usecase.ListClientsUseCase;
import com.andersenlab.hotel.usecase.RegisterClientUseCase;

public final class HotelModule { //TODO

    private ClientService clientService;
    private ApartmentService apartmentService;

    public HotelModule(ClientStore clientStore, ApartmentStore apartmentStore) {
        //this.clientService = new ClientService(clientStore);
        //this.apartmentService = new ApartmentService(apartmentStore);
    }

    public AddApartmentUseCase addApartmentUseCase() {
        return apartmentService;
    }

    public AdjustApartmentPriceUseCase adjustApartmentPriceUseCase() {
        return apartmentService;
    }

    public ListApartmentsUseCase listApartmentsUseCase() {
        return apartmentService;
    }

    public CalculateClientStayCurrentPriceUseCase calcClientStayCurrentPrice() {
        return clientService;
    }

    public CheckInClientUseCase checkInClientUseCase() {
        return clientService;
    }

    public CheckOutClientUseCase checkOutClientUseCase() {
        return clientService;
    }

    public ListClientsUseCase listClientsUseCase() {
        return clientService;
    }

    public RegisterClientUseCase registerClientUseCase() {
        return clientService;
    }

}

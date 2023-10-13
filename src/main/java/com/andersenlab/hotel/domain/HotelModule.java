package com.andersenlab.hotel.domain;

import com.andersenlab.hotel.port.external.ApartmentStore;
import com.andersenlab.hotel.port.external.ClientStore;
import com.andersenlab.hotel.port.usecase.AddApartmentUseCase;
import com.andersenlab.hotel.port.usecase.AdjustApartmentPriceUseCase;
import com.andersenlab.hotel.port.usecase.CalculateClientStayCurrentPrice;
import com.andersenlab.hotel.port.usecase.CheckInClientUseCase;
import com.andersenlab.hotel.port.usecase.CheckOutClientUseCase;
import com.andersenlab.hotel.port.usecase.ListApartmentsUseCase;
import com.andersenlab.hotel.port.usecase.ListClientsUseCase;
import com.andersenlab.hotel.port.usecase.RegisterClientUseCase;

public final class HotelModule {

    private final ClientService clientService;
    private final ApartmentService apartmentService;

    public HotelModule(ClientStore clientStore, ApartmentStore apartmentStore) {
        this.clientService = new ClientService(clientStore);
        this.apartmentService = new ApartmentService(apartmentStore);
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

    public CalculateClientStayCurrentPrice calcClientStayCurrentPrice() {
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

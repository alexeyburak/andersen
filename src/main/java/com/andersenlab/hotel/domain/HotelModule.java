package com.andersenlab.hotel.domain;

import com.andersenlab.hotel.port.external.ApartmentStore;
import com.andersenlab.hotel.port.external.ClientStore;

public final class HotelModule {

    private final ClientService clientService;
    private final ApartmentService apartmentService;

    public HotelModule(ClientStore clientStore, ApartmentStore apartmentStore) {
        this.clientService = new ClientService(clientStore);
        this.apartmentService = new ApartmentService(apartmentStore);
    }

}

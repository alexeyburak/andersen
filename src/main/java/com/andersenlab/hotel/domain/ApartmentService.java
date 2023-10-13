package com.andersenlab.hotel.domain;

import com.andersenlab.hotel.port.external.ApartmentStore;

final class ApartmentService {

    private final ApartmentStore apartmentStore;

    ApartmentService(ApartmentStore apartmentStore) {
        this.apartmentStore = apartmentStore;
    }

}

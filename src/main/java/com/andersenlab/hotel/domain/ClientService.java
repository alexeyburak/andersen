package com.andersenlab.hotel.domain;

import com.andersenlab.hotel.port.external.ClientStore;

final class ClientService {

    private final ClientStore clientStore;

    ClientService(ClientStore clientStore) {
        this.clientStore = clientStore;
    }

}

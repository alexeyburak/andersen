package com.andersenlab.hotel;

import com.andersenlab.hotel.repository.inmemory.InMemoryApartmentStore;
import com.andersenlab.hotel.repository.inmemory.InMemoryClientStore;
import com.andersenlab.hotel.application.command.CommandUtils;
import com.andersenlab.hotel.service.HotelModule;

public class Main {

    public static void main(String[] args) {
        CommandUtils.run();
        /*new TextInterface(
                System.in,
                System.out,
                new ArrayList<>()
        ).run();*/
    }

    private static HotelModule module() {
        return new HotelModule(
                new InMemoryClientStore(),
                new InMemoryApartmentStore()
        );
    }

}
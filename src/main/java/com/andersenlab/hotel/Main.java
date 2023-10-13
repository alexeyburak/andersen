package com.andersenlab.hotel;

import com.andersenlab.hotel.application.InMemoryApartmentStore;
import com.andersenlab.hotel.application.InMemoryClientStore;
import com.andersenlab.hotel.application.TextInterface;
import com.andersenlab.hotel.domain.HotelModule;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        new TextInterface(
                System.in,
                System.out,
                new ArrayList<>()
        ).run();
    }

    private static HotelModule module() {
        return new HotelModule(
                new InMemoryClientStore(),
                new InMemoryApartmentStore()
        );
    }

}
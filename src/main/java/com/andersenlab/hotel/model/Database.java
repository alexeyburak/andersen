package com.andersenlab.hotel.model;

import java.util.Map;
import java.util.UUID;

public record Database(Map<UUID, Apartment> apartments, Map<UUID, Client> clients) {
}

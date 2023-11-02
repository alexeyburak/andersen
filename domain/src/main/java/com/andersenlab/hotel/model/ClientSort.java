package com.andersenlab.hotel.model;

import com.andersenlab.hotel.model.Client;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Comparator;

@AllArgsConstructor
@Getter
public enum ClientSort {
    ID(Comparator.comparing(Client::getId)),
    NAME(Comparator.comparing(Client::getName)),
    STATUS(Comparator.comparing(Client::getStatus));

    private final Comparator<Client> comparator;
}

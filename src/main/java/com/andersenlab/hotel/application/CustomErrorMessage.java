package com.andersenlab.hotel.application;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CustomErrorMessage {
    APARTMENT_IS_ALREADY_EXISTS("Apartment is already exists"),
    CLIENT_IS_ALREADY_EXISTS("Client is already exists"),
    WRONG_ARGUMENTS("Wrong arguments");

    private String message;
}

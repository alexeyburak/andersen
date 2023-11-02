package com.andersenlab.hotel;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CustomErrorMessage {

    APARTMENT_IS_ALREADY_EXISTS("Apartment is already exists"),
    CLIENT_IS_ALREADY_EXISTS("Client is already exists"),
    CLIENT_NOT_FOUND("Client was not found"),
    CLIENT_BANNED("Client banned"),
    APARTMENT_NOT_FOUND("Apartment was not found"),
    APARTMENT_RESERVED("Apartment is already reserved"),
    UNKNOWN_ENTITY("Unknown entity"),
    WRONG_ARGUMENTS("Wrong arguments"),
    INVALID_ARGUMENTS_QUANTITY("Invalid arguments quantity"),
    ILLEGAL_PRICE("Illegal price"),
    ILLEGAL_CAPACITY("Illegal capacity"),
    ILLEGAL_AVAILABILITY("Illegal availability"),
    ILLEGAL_STATUS("Illegal status"),
    ILLEGAL_NUMBER_OF_ARGUMENTS("Illegal number of arguments"),
    APARTMENT_STATUS_CHANGED("Apartment status can not be changed");

    private final String message;
}

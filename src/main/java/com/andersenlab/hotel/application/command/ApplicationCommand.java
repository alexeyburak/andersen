package com.andersenlab.hotel.application.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ApplicationCommand {

    CREATE("Create new entity"),
    DELETE("Delete existing entity"),
    GET("Get entity by id"),
    GET_ALL("Print all"),
    CHECK_IN("Check in client"),
    CHECK_OUT("Check out client"),
    ADJUST("Adjust apartment price"),
    CALCULATE_PRICE("Calculate client stay price"),
    HELP("Help"),
    EXIT("Exit");

    private final String desc;
}

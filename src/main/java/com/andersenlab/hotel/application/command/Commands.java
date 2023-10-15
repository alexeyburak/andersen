package com.andersenlab.hotel.application.command;

import com.andersenlab.hotel.service.ApartmentService;
import com.andersenlab.hotel.service.ClientService;
import lombok.Getter;

@Getter
public enum Commands {
    CREATE(new Create(), "Create new entity"), DELETE(new Delete(), "Delete existing entity"),
    GET(new Get(), "Get entity by id"), GET_ALL(new GetAll(), "Print all"),
    CHECK_IN(new CheckIn(ClientService.getInstance()),"Check in client"),
    CHECK_OUT(new CheckOut(ClientService.getInstance()),"Check out client"),
    ADJUST(new Adjust(ApartmentService.getInstance()), "Adjust apartment price"),
    CALCULATE_PRICE(new CalculatePrice(ClientService.getInstance()),"Calculate client stay price"),
    EXIT(new Exit(), "Exit");

    private final Command command;
    private final String desc;

    Commands(Command command, String desc) {
        this.command = command;
        this.desc = desc;
    }
}

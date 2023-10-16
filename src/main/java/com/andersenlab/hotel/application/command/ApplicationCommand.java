package com.andersenlab.hotel.application.command;

import com.andersenlab.hotel.service.ApartmentService;
import lombok.Getter;

@Getter
public enum ApplicationCommand {

//    CREATE(new CreateEntityCommand(), "Create new entity"),
//    DELETE(new DeleteEntityCommand(), "Delete existing entity"),
//    GET(new GetEntityCommand(), "Get entity by id"),
//    GET_ALL(new GetEntityListCommand(), "Print all"),
//    CHECK_IN(new CheckInClientCommand(), "Check in client"
//    ),
//    CHECK_OUT(new CheckOutClientCommand(), "Check out client"
//    ),
//    ADJUST(
//            new AdjustApartmentPriceCommand(), "Adjust apartment price"
//    ),
//    CALCULATE_PRICE(
//            new CalculateClientStayCurrentPriceCommand(), "Calculate client stay price"
//    ),
    EXIT(new ExitApplicationCommand(), "Exit");

    private final Command command;
    private final String desc;

    ApplicationCommand(Command command, String desc) {
        this.command = command;
        this.desc = desc;
    }
}

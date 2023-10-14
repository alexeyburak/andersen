package com.andersenlab.hotel.application.command;

import java.io.PrintStream;
import java.util.List;

public class Create implements Command {
    @Override
    public void printDescription(PrintStream output) {
        output.println("Description");
    }

    //create order 12
    @Override
    public void execute(PrintStream output, List<String> arguments) {
        switch (arguments.get(1)) {
            case "apartment"-> output.println("Creating apartment");
            //TODO AddApartmentUseCase!!!!
            case "client" -> output.println("Creating client");
            default -> throw new IllegalArgumentException("Unknown argument");
        }
    }
}

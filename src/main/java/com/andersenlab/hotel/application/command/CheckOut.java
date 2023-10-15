package com.andersenlab.hotel.application.command;

import com.andersenlab.hotel.usecase.CheckOutClientUseCase;
import lombok.AllArgsConstructor;

import java.io.PrintStream;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class CheckOut implements Command {
    private final CheckOutClientUseCase useCase;

    @Override
    public void execute(PrintStream output, List<String> arguments) {
        useCase.checkOut(UUID.fromString(arguments.get(1)), UUID.fromString(arguments.get(2)));
        output.println("Client checked out");
    }
}

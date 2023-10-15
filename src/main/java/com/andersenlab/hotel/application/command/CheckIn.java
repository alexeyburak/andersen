package com.andersenlab.hotel.application.command;

import com.andersenlab.hotel.usecase.CheckInClientUseCase;
import lombok.AllArgsConstructor;

import java.io.PrintStream;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class CheckIn implements Command {
    private final CheckInClientUseCase useCase;

    @Override
    public void execute(PrintStream output, List<String> arguments) {
        useCase.checkIn(UUID.fromString(arguments.get(1)), UUID.fromString(arguments.get(2)));
        output.println("Client checked in");
    }
}

package com.andersenlab.hotel.application.command;

import com.andersenlab.hotel.usecase.CheckOutClientUseCase;
import lombok.AllArgsConstructor;

import java.io.PrintStream;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public final class CheckOutClientCommand implements Command {

    private static final String CHECK_OUT_MESSAGE = "Client was checked out";

    private final CheckOutClientUseCase useCase;

    @Override
    public void execute(PrintStream output, List<String> arguments) {
        UUID clientId = UUID.fromString(arguments.get(1));
        UUID apartmentId = UUID.fromString(arguments.get(2));

        useCase.checkOut(clientId, apartmentId);

        output.println(CHECK_OUT_MESSAGE);
    }
}

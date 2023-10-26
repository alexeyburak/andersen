package com.andersenlab.hotel.command.additional;

import com.andersenlab.hotel.command.ApplicationCommand;
import com.andersenlab.hotel.command.Command;
import com.andersenlab.hotel.usecase.CheckInClientUseCase;
import lombok.AllArgsConstructor;

import java.io.PrintStream;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public final class CheckInClientCommand implements Command {

    private static final String CHECK_IN_MESSAGE = "Client was checked in";
    private static final ApplicationCommand APPLICATION_COMMAND = ApplicationCommand.CHECK_IN;

    private final CheckInClientUseCase useCase;

    @Override
    public ApplicationCommand getApplicationCommand() {
        return APPLICATION_COMMAND;
    }

    @Override
    public void execute(PrintStream output, List<String> arguments) {
        UUID clientId = UUID.fromString(arguments.get(1));
        UUID apartmentId = UUID.fromString(arguments.get(2));

        useCase.checkIn(clientId, apartmentId);

        output.println(CHECK_IN_MESSAGE);
    }
}

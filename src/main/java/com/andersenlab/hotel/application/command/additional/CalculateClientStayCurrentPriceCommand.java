package com.andersenlab.hotel.application.command.additional;

import com.andersenlab.hotel.application.command.ApplicationCommand;
import com.andersenlab.hotel.application.command.Command;
import com.andersenlab.hotel.usecase.CalculateClientStayCurrentPriceUseCase;
import lombok.AllArgsConstructor;

import java.io.PrintStream;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public final class CalculateClientStayCurrentPriceCommand implements Command {

    private static final ApplicationCommand APPLICATION_COMMAND = ApplicationCommand.CALCULATE_PRICE;

    private final CalculateClientStayCurrentPriceUseCase useCase;

    @Override
    public ApplicationCommand getApplicationCommand() {
        return APPLICATION_COMMAND;
    }

    @Override
    public void execute(PrintStream output, List<String> arguments) {
        double price = useCase.calculatePrice(UUID.fromString(arguments.get(1)));

        output.println("Price for all apartments: " + price);
    }
}

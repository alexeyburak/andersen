package com.andersenlab.hotel.application.command;

import com.andersenlab.hotel.usecase.CalculateClientStayCurrentPriceUseCase;
import lombok.AllArgsConstructor;

import java.io.PrintStream;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public final class CalculateClientStayCurrentPriceCommand implements Command {

    private final CalculateClientStayCurrentPriceUseCase useCase;

    @Override
    public void execute(PrintStream output, List<String> arguments) {
        useCase.calculatePrice(UUID.fromString(arguments.get(1)));
    }
}

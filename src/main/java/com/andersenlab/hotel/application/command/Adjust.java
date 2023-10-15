package com.andersenlab.hotel.application.command;

import com.andersenlab.hotel.usecase.AdjustApartmentPriceUseCase;
import lombok.AllArgsConstructor;

import java.io.PrintStream;
import java.util.List;

@AllArgsConstructor
public class Adjust implements Command {
    private final AdjustApartmentPriceUseCase useCase;

    @Override
    public void execute(PrintStream output, List<String> arguments) {

    }
}

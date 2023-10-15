package com.andersenlab.hotel.application.command;

import com.andersenlab.hotel.usecase.AdjustApartmentPriceUseCase;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class Adjust implements Command {
    private final AdjustApartmentPriceUseCase useCase;

    @Override
    public void execute(PrintStream output, List<String> arguments) {
        if(!NumberUtils.isParsable(arguments.get(2))){
            throw new IllegalArgumentException("Wrong price");
        }
        useCase.adjust(UUID.fromString(arguments.get(1)),
                BigDecimal.valueOf(NumberUtils.toLong(arguments.get(2))));
    }
}

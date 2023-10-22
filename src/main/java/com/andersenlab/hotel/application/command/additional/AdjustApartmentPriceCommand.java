package com.andersenlab.hotel.application.command.additional;

import com.andersenlab.hotel.application.command.ApplicationCommand;
import com.andersenlab.hotel.application.command.ValidatingArgumentsCommand;
import com.andersenlab.hotel.usecase.AdjustApartmentPriceUseCase;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public final class AdjustApartmentPriceCommand extends ValidatingArgumentsCommand {

    private static final ApplicationCommand APPLICATION_COMMAND = ApplicationCommand.ADJUST;

    private final AdjustApartmentPriceUseCase useCase;

    @Override
    public ApplicationCommand getApplicationCommand() {
        return APPLICATION_COMMAND;
    }

    @Override
    public void process(PrintStream output, List<String> arguments) {
        UUID apartmentId = UUID.fromString(arguments.get(1));
        BigDecimal newPrice = BigDecimal.valueOf(NumberUtils.toLong(arguments.get(2)));

        useCase.adjust(apartmentId, newPrice);
        output.println("Apartment price was adjusted");
    }

    @Override
    public void validate(List<String> arguments) throws IllegalArgumentException {
        if (!NumberUtils.isParsable(arguments.get(2))) {
            throw new IllegalArgumentException("Illegal price");
        }
    }
}

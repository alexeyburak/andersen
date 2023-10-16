package com.andersenlab.hotel.application.command;

import com.andersenlab.hotel.application.CustomErrorMessage;
import com.andersenlab.hotel.usecase.AdjustApartmentPriceUseCase;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public final class AdjustApartmentPriceCommand implements Command, ArgumentsValidator<String> {

    private final AdjustApartmentPriceUseCase useCase;

    @Override
    public void execute(PrintStream output, List<String> arguments) {
        validateArguments(arguments);

        UUID apartmentId = UUID.fromString(arguments.get(1));
        BigDecimal newPrice = BigDecimal.valueOf(NumberUtils.toLong(arguments.get(2)));

        useCase.adjust(apartmentId, newPrice);
    }

    @Override
    public void validateArguments(List<String> arguments) throws IllegalArgumentException {
        if (!NumberUtils.isParsable(arguments.get(2))) {
            throw new IllegalArgumentException(CustomErrorMessage.ILLEGAL_PRICE.getMessage());
        }
    }
}

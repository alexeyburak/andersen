package com.andersenlab.hotel.application.command;

import com.andersenlab.hotel.port.usecase.exception.ApartmentWithSameIdExists;
import com.andersenlab.hotel.port.usecase.exception.ClientIsAlreadyExistsException;

import java.io.PrintStream;
import java.util.List;

import static com.andersenlab.hotel.application.ErrorMessage.APARTMENT_IS_ALREADY_EXISTS;
import static com.andersenlab.hotel.application.ErrorMessage.CLIENT_IS_ALREADY_EXISTS;


public final class BusinessExceptionHandlingCommand implements Command {

    private final Command original;

    public BusinessExceptionHandlingCommand(Command original) {
        this.original = original;
    }

    @Override
    public void printDescription(PrintStream output) {
        original.printDescription(output);
    }

    @Override
    public void execute(PrintStream output, List<String> arguments) {
        try {
            original.execute(output, arguments);
        } catch (ApartmentWithSameIdExists e) {
            output.println(APARTMENT_IS_ALREADY_EXISTS);
        } catch (ClientIsAlreadyExistsException e) {
            output.println(CLIENT_IS_ALREADY_EXISTS);
        }
    }

}

package com.andersenlab.hotel.application.command;

import java.io.PrintStream;
import java.util.List;

import static com.andersenlab.hotel.application.ErrorMessage.WRONG_ARGUMENTS;

public final class GenericExceptionHandlingCommand implements Command {

    private final Command original;

    public GenericExceptionHandlingCommand(Command original) {
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
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            output.println(WRONG_ARGUMENTS);
        }
    }

}

package com.andersenlab.hotel.command;

import java.io.PrintStream;
import java.util.List;

public final class GenericExceptionHandlingCommand implements Command {

    private final Command original;

    public GenericExceptionHandlingCommand(Command original) {
        this.original = original;
    }

    @Override
    public ApplicationCommand getApplicationCommand() {
        return original.getApplicationCommand();
    }

    @Override
    public void execute(PrintStream output, List<String> arguments) {
        try {
            original.execute(output, arguments);
        } catch (IllegalArgumentException e) {
            output.println(e.getMessage());
        }
    }
}

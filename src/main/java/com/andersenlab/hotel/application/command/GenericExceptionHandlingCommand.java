package com.andersenlab.hotel.application.command;

import com.andersenlab.hotel.application.CustomErrorMessage;

import java.io.PrintStream;
import java.util.List;

public final class GenericExceptionHandlingCommand implements Command {

    private static final ApplicationCommand applicationCommand = ApplicationCommand.GENERIC_EXCEPTION_HANDLER;

    private final Command original;

    public GenericExceptionHandlingCommand(Command original) {
        this.original = original;
    }

    @Override
    public ApplicationCommand getApplicationCommand() {
        return applicationCommand;
    }

    @Override
    public void execute(PrintStream output, List<String> arguments) {
        try {
            original.execute(output, arguments);
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            output.println(CustomErrorMessage.WRONG_ARGUMENTS.getMessage());
        }
    }
}
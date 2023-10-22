package com.andersenlab.hotel.application.command;

import java.io.PrintStream;
import java.util.List;

public abstract class ValidatingArgumentsCommand implements Command {

    @Override
    public void execute(PrintStream output, List<String> arguments) {
        validate(arguments);
        process(output, arguments);
    }

    protected abstract void validate(List<String> arguments) throws IllegalArgumentException;
    protected abstract void process(PrintStream output, List<String> arguments) throws IllegalArgumentException;
}

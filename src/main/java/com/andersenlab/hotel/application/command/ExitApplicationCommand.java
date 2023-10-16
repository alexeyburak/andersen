package com.andersenlab.hotel.application.command;

import java.io.PrintStream;
import java.util.List;

public final class ExitApplicationCommand implements Command {

    private static final String EXIT_MESSAGE = "Goodbye, have a nice day!";

    @Override
    public void execute(PrintStream output, List<String> arguments) {
        output.println(EXIT_MESSAGE);
    }
}

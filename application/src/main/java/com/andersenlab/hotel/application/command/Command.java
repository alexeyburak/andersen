package com.andersenlab.hotel.application.command;

import java.io.PrintStream;
import java.util.List;

public interface Command {
    ApplicationCommand getApplicationCommand();

    void execute(PrintStream output, List<String> arguments);
}

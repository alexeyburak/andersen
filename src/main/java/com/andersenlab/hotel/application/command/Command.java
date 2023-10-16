package com.andersenlab.hotel.application.command;

import java.io.PrintStream;
import java.util.List;

@FunctionalInterface
public interface Command {
    void execute(PrintStream output, List<String> arguments);
}

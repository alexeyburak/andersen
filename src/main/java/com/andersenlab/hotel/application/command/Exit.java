package com.andersenlab.hotel.application.command;

import java.io.PrintStream;
import java.util.List;

public class Exit implements Command{

    @Override
    public void execute(PrintStream output, List<String> arguments) {
        output.println("Goodbye, have a nice day!");
    }
}

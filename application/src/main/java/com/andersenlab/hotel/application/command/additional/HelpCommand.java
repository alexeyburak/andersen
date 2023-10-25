package com.andersenlab.hotel.application.command.additional;

import com.andersenlab.hotel.application.command.ApplicationCommand;
import com.andersenlab.hotel.application.command.Command;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

public class HelpCommand implements Command {
    @Override
    public ApplicationCommand getApplicationCommand() {
        return ApplicationCommand.HELP;
    }

    @Override
    public void execute(PrintStream output, List<String> arguments) {
        Arrays.stream(ApplicationCommand.values()).forEach(command -> output.printf(
                "To action - %s, write - %s%n", command.getDesc(), command.toString().toLowerCase()
        ));
    }
}

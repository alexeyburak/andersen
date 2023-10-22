package com.andersenlab.hotel.application.command;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.EnumUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public final class CommandStarter {

    private final InputStream inputStream;
    private final PrintStream printStream;

    private final List<? extends Command> commands;//instead of List<Command>

    @SneakyThrows
    public void run() {
        final BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8)
        );

        ApplicationCommand chosenCommand;
        do {
            printMenu();
            String userInput = reader.readLine();
            List<String> args = Arrays.stream(userInput.split(" ")).toList();
            chosenCommand = EnumUtils.getEnum(ApplicationCommand.class, args.get(0).toUpperCase());
            getCommand(chosenCommand).ifPresent(command -> command.execute(printStream, args) );
        } while (chosenCommand != ApplicationCommand.EXIT);
    }

    //returns Optional<? extends Command> instead of Optional<Command>
    private Optional<? extends Command> getCommand(ApplicationCommand chosenCommand) {
        return commands.stream().filter(command -> command.getApplicationCommand().equals(chosenCommand)).findFirst();
    }

    private void printMenu() {
        printStream.println("""
                ---------
                Welcome! command format [command model additional_args]
                example: create apartment
                List of available commands below:
                """);
        commands.forEach(command -> printStream.printf(
                "To action - %s, write - %s%n",
                command.getApplicationCommand().getDesc(),
                command.getApplicationCommand().toString().toLowerCase()
        ));
    }


}

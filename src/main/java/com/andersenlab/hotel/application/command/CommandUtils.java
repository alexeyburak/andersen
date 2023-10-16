package com.andersenlab.hotel.application.command;

import lombok.SneakyThrows;
import org.apache.commons.lang3.EnumUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public final class CommandUtils {

    private CommandUtils() {
    }

    @SneakyThrows
    public static void run(final InputStream inputStream, final PrintStream printStream) {
        final BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8)
        );
        final List<ApplicationCommand> allCommands = Arrays.stream(ApplicationCommand.values()).toList();

        ApplicationCommand chosenComand;
        do {
            printStream.println("""
                            ---------
                            Welcome! command format [command model additional_args]
                            example: create apartment
                            List of available commands below:
                    """);
            allCommands.forEach(command ->
                    printStream.printf("To action - %s, write - %s%n", command.getDesc(), command.name().toLowerCase())
            );

            String userInput = reader.readLine();
            List<String> args = Arrays.stream(userInput.split(" ")).toList();
            chosenComand = EnumUtils.getEnum(ApplicationCommand.class, args.get(0).toUpperCase());

            execute(chosenComand.getCommand(), printStream, args);
        } while (chosenComand != ApplicationCommand.EXIT);
    }

    private static void execute(Command command, PrintStream printStream, List<String> args) {
        new BusinessExceptionHandlingCommand(
                new GenericExceptionHandlingCommand(command)
        ).execute(printStream, args);
    }
}

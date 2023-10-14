package com.andersenlab.hotel.application.command;

import lombok.SneakyThrows;
import org.apache.commons.lang3.EnumUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class CommandUtils {
    private CommandUtils() {}

    @SneakyThrows
    public static void run() {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); //TODO maybe use in try with resources
        final List<Commands> allCommands = Arrays.stream(Commands.values()).toList();
        Commands chosenComand;
        do {
            System.out.println(
                    """
                    ---------
                    Welcome! command format [command model additional_args]
                    example: create apartment
                    List of available commands below:"""
            );
            allCommands.forEach(command ->
                    System.out.printf("To action - %s, write - %s%n", command.getDesc(),
                            command.name().toLowerCase()));
            String userInput = reader.readLine();
            List<String> args = Arrays.stream(userInput.split(" ")).toList();
            chosenComand = EnumUtils.getEnum(Commands.class, args.get(0).toUpperCase());
            chosenComand.getCommand().execute(System.out, args);
        } while (chosenComand != Commands.EXIT);
    }

    private static boolean isArgsValid(String args) { //TODO
        return false;
    }
}

package com.andersenlab.hotel;

import com.andersenlab.hotel.application.command.CommandUtils;

public class Main {
    public static void main(String[] args) {
        CommandUtils.run(
                System.in,
                System.out
        );
    }
}
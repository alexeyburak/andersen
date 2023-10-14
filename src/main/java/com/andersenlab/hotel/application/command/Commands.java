package com.andersenlab.hotel.application.command;

import lombok.Getter;

@Getter
public enum Commands {
    CREATE(new Create(), "Create"), UPDATE(new Create(), "Update"),
    DELETE(new Create(), "Delete"), GET(new Create(), "Get"),
    EXIT(new Exit(), "Exit");

    private Command command;
    private String desc;

    Commands(Command command, String desc) {
        this.command = command;
        this.desc = desc;
    }
}

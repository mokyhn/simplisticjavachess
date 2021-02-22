package com.simplisticjavachess.telnet;

public class Command {

    private final CommandEnum command;
    private final String argument;

    public Command(CommandEnum command, String argument) {
        this.command = command;
        this.argument = argument;
    }

    public CommandEnum getCommand() {
        return this.command;
    }

    public String getArgument() {
        return this.argument;
    }
}

package com.simplisticjavachess.telnet;

import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Command other = (Command) obj;
        return command == other.command &&
                Objects.equals(argument, other.argument);
    }

    @Override
    public int hashCode() {
        return Objects.hash(command, argument);
    }

    @Override
    public String toString() {
        return command.toString() + " " + argument;
    }
}

package com.simplisticjavachess.board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Morten Kühnrich
 */
public class ComposedCommand implements Command
{
    private final Command[] commands;
    
    public ComposedCommand(Command... commands)
    {
        this.commands = commands;
    }
    
    public List<Command> getCommands()
    {
        return new ArrayList<>(Arrays.asList(commands));
    }
}

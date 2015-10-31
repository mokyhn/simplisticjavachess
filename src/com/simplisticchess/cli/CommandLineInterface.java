package com.simplisticchess.cli;

import com.simplisticchess.Utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Morten Kühnrich
 */
public class CommandLineInterface
{
    private final List<Command> commands;
    
    private String promptText;
    
    public CommandLineInterface()
    {
        commands = new ArrayList<Command>();
        promptText = "> ";
    }
    
    public void registerCommand(Command command)
    {
        commands.add(command);
    }
    
    public void setPromtText(String promptText)
    {
        this.promptText = promptText;
    }
    
    private void executeCommand(LinkedList<String> commandAndArgs)
    {
        if (!commandAndArgs.isEmpty())
        {       
            for (Command command : commands)
            {      
                if (command.isApplicable(commandAndArgs.getFirst()))
                {
                    commandAndArgs.pollFirst();
                        command.execute(commandAndArgs.toArray(new String[] {}));
                    }
                }

            }
        }

    public void start()
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true)
        {
            System.out.print("\n" + promptText);
            try
            {
                String stringInput = reader.readLine();
                executeCommand(new LinkedList<String>(Arrays.asList(stringInput.trim().toLowerCase().split("\\s+"))));
            } 
            catch (IOException ex)
            {
                ex.printStackTrace();
            }

        }
    }
    
    public void stop()
    {
    }
    
    public static void main(String param[])
    {
        CommandLineInterface cli = new CommandLineInterface();
        cli.start();
    }
    
}

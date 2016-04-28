package com.simplisticchess.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
        promptText = "";
    }
    
    public void registerCommand(Command command)
    {
        commands.add(command);
    }
    
    public void setPromtText(String promptText)
    {
        this.promptText = promptText;
    }

    private void displayHelp() 
    {
        for (Command command : commands) 
        {
            if (command.helpCommand() != null && command.helpExplanation() != null) 
            {
                System.out.println(command.helpCommand() + " - " + command.helpExplanation());
            }
        }
    
    }
    
    private void executeCommand(String[] commandAndArgs) throws Exception
    {
        if (commandAndArgs.length > 0)
        {       
            for (Command command : commands)
            {      
                if (command.isApplicable(commandAndArgs[0]))
                {
                    command.execute(commandAndArgs);
                    break;
                }
            }
        }
    }

    public void start() throws Exception
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true)
        {
            System.out.print("\n" + promptText);

            String stringInput = reader.readLine().trim().toLowerCase();
            if (stringInput.equals("help")) {
                displayHelp();
            }
            executeCommand(stringInput.trim().toLowerCase().split("\\s+"));          
        }
    }
    
    public void stop()
    {
    }
    
   
    
}

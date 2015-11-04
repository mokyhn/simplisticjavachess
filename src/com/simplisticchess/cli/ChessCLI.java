package com.simplisticchess.cli;

/**
 *
 * @author mku
 */
public class ChessCLI
{
    private CommandLineInterface cli = new CommandLineInterface();
    
    public ChessCLI()
    {
        cli.registerCommand(new CommandQuit());
    }
    
    public CommandLineInterface getCLI() 
    {
        return cli;
    }
    
    public static void main(String param[])
    {
        ChessCLI cli = new ChessCLI();
        cli.getCLI().start();
    }
}


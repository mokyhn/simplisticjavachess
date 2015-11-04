package com.simplisticchess.cli;

/**
 *
 * @author Morten Kühnrich
 */
public class CommandQuit implements Command
{

    public boolean isApplicable(String str)
    {
        return str.startsWith("quit") || str.matches("q") || str.matches("bye") || str.startsWith("exit");
    }

    public void execute(String[] arguments)
    {
        System.out.print("\nGoodbye\n\n");
        System.exit(0);
    }

    public String help()
    {
        return "quit, q, bye, exit - Exits the chess program";
    }
}

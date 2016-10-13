package com.simplisticjavachess.cli.cecpcommands;

import com.simplisticjavachess.cli.Command;

/**
 *
 * @author Morten Kühnrich
 */
@ChessEngineCommunicationProtocol
public class CommandQuit implements Command
{

    public boolean isApplicable(String str)
    {
        return str.startsWith("quit") || str.matches("q") || str.matches("bye") || str.startsWith("exit");
    }

    public void execute(String[] arguments)
    {
        System.exit(0);
    }

    public String helpCommand()
    {
        return "quit, q, bye, exit";
    }
    
    public String helpExplanation()
    {
        return "Exits the chess program";
    }
}

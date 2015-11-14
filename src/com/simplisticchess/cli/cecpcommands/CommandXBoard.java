package com.simplisticchess.cli.cecpcommands;

import com.simplisticchess.cli.Command;

/**
 *
 * @author Morten Kühnrich
 */
@ChessEngineCommunicationProtocol
public class CommandXBoard implements Command
{

    public boolean isApplicable(String str)
    {
        return str.startsWith("xboard");
    }

    public void execute(String[] arguments)
    {
        System.out.println();
    }

    public String helpCommand()
    {
        return null;
    }
    
    public String helpExplanation()
    {
        return null;
    }
}

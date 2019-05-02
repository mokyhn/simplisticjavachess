package com.simplisticjavachess.cli.cecpcommands;

import com.simplisticjavachess.game.ChessGame;
import com.simplisticjavachess.cli.Command;

/**
 *
 * @author Morten Kühnrich
 */
@ChessEngineCommunicationProtocol
public class CommandSd implements Command
{
    private final ChessGame chessGame;

    public CommandSd(ChessGame chessGame) 
    {
        this.chessGame = chessGame;
    }
    
    public boolean isApplicable(String str)
    {
        return str.startsWith("sd");
    }

    public void execute(String[] arguments)
    {
        if (arguments.length == 2) 
        {
            chessGame.setSd(Integer.parseInt(arguments[1]));
        }
    }

    public String helpCommand()
    {
        return "sd number";
    }
    
    public String helpExplanation()
    {
        return "Set search ply depth";
    }
}

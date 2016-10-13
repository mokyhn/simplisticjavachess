package com.simplisticjavachess.cli.cecpcommands;

import com.simplisticjavachess.ChessGame;
import com.simplisticjavachess.cli.Command;

/**
 *
 * @author Morten Kühnrich
 */

@ChessEngineCommunicationProtocol
public class CommandNew implements Command
{
    private final ChessGame chessGame;
    
    public CommandNew(ChessGame chessGame) 
    {
        this.chessGame = chessGame;
    }

    public boolean isApplicable(String str)
    {
        return str.matches("new");
    }

    public void execute(String[] arguments)
    {
        chessGame.newgame();
    }

    public String helpCommand()
    {
        return "new";
    }

    public String helpExplanation()
    {
        return "Start over";
    }
}

package com.simplisticchess.cli;

import com.simplisticchess.ChessGame;

/**
 *
 * @author Morten Kühnrich
 */

@ChessEngineCommunicationProtocol
public class CommandUndo implements Command
{

    private final ChessGame chessGame;
    
    public CommandUndo(ChessGame chessGame) 
    {
        this.chessGame = chessGame;
    }
    
    public boolean isApplicable(String str)
    {
        return str.matches("undo");
    }

    public void execute(String[] arguments)
    {
        chessGame.undo();
    }

    public String helpCommand()
    {
        return "undo";
    }

    public String helpExplanation()
    {
        return "Undo the last move";
    }
    
}

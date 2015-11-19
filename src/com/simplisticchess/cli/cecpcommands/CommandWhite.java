package com.simplisticchess.cli.cecpcommands;

import com.simplisticchess.ChessGame;
import com.simplisticchess.cli.Command;

/**
 *
 * @author Morten Kühnrich
 */
public class CommandWhite implements Command
{

    private final ChessGame chessGame;
    
    public CommandWhite(ChessGame chessGame)
    {
        this.chessGame = chessGame;
    }

    public boolean isApplicable(String str)
    {
        return str.matches("white");
    }

    public void execute(String[] arguments)
    {
        chessGame.white();
    }

    public String helpCommand()
    {
        return "white";
    }

    public String helpExplanation()
    {
        return "Set white to be in move";
    }
    
}
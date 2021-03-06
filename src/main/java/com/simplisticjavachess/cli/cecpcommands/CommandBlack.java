package com.simplisticjavachess.cli.cecpcommands;

import com.simplisticjavachess.game.ChessGame;
import com.simplisticjavachess.cli.Command;

/**
 *
 * @author Morten Kühnrich
 */
public class CommandBlack implements Command
{

    private final ChessGame chessGame;
    
    public CommandBlack(ChessGame chessGame)
    {
        this.chessGame = chessGame;
    }

    public boolean isApplicable(String str)
    {
        return str.matches("black");
    }

    public void execute(String[] arguments)
    {

    }

    public String helpCommand()
    {
        return "black";
    }

    public String helpExplanation()
    {
        return "Set black to be in move";
    }
    
}

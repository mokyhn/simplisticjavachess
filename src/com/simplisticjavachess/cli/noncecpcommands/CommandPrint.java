package com.simplisticjavachess.cli.noncecpcommands;

import com.simplisticjavachess.game.ChessGame;
import com.simplisticjavachess.cli.Command;

/**
 *
 * @author Morten Kühnrich
 */
public class CommandPrint implements Command
{
    private final ChessGame chessGame;
    
    public CommandPrint(ChessGame chessGame) 
    {
        this.chessGame = chessGame;
    }

    public boolean isApplicable(String str)
    {
        return str.matches("print") || str.matches("p");
    }

    public void execute(String[] arguments)
    {
        chessGame.print();
    }

    public String helpCommand()
    {
        return "print, p";
    }

    public String helpExplanation()
    {
        return "Pretty print the current position";
    }
    
}

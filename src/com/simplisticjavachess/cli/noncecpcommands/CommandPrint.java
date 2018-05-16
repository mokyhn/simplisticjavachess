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

    @Override
    public boolean isApplicable(String str)
    {
        return str.matches("print") || str.matches("p");
    }

    @Override
    public void execute(String[] arguments)
    {
        chessGame.print();
    }

    @Override
    public String helpCommand()
    {
        return "print, p";
    }

    @Override
    public String helpExplanation()
    {
        return "Pretty print the current position";
    }
    
}

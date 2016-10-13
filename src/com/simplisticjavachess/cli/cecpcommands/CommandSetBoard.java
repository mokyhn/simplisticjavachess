package com.simplisticjavachess.cli.cecpcommands;

import com.simplisticjavachess.ChessGame;
import com.simplisticjavachess.cli.Command;

/**
 *
 * @author Morten Kühnrich
 */
public class CommandSetBoard implements Command
{

    private final ChessGame chessGame;
    
    public CommandSetBoard(ChessGame chessGame)
    {
        this.chessGame = chessGame;
    }

    public boolean isApplicable(String str)
    {
        return str.matches("setboard");
    }

    public void execute(String[] arguments)
    {
        if (arguments.length == 2) 
        {
            chessGame.setBoard(arguments[1]);
        }
        
    }

    public String helpCommand()
    {
        return "setboard FEN";
    }

    public String helpExplanation()
    {
        return "Set position in Forsythe-Edwards notation";
    }
    
}

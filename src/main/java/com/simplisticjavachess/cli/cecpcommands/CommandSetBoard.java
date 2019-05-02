package com.simplisticjavachess.cli.cecpcommands;

import com.simplisticjavachess.game.ChessGame;
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

    @Override
    public boolean isApplicable(String str)
    {
        return str.matches("setboard");
    }

    @Override
    public void execute(String[] arguments)
    {
        if (arguments.length == 2) 
        {
            chessGame.setBoard(arguments[1]);
        }
        
    }

    @Override
    public String helpCommand()
    {
        return "setboard FEN";
    }

    @Override
    public String helpExplanation()
    {
        return "Set position in Forsythe-Edwards notation";
    }
    
}

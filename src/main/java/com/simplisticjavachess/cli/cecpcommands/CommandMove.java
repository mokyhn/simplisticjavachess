package com.simplisticjavachess.cli.cecpcommands;

import com.simplisticjavachess.game.ChessGame;
import com.simplisticjavachess.cli.Command;

/**
 *
 * @author Morten Kühnrich
 */
public class CommandMove implements Command
{

    private final ChessGame chessGame;
    
    public CommandMove(ChessGame chessGame)
    {
        this.chessGame = chessGame;
    }

    public boolean isApplicable(String str)
    {
        str = str.toLowerCase();
        String moveRegex = "[a-h][1-8][a-h][1-8][nkbqr]?";
        return str.matches(moveRegex);
    }

    public void execute(String[] arguments)
    {
        try 
        {
            String moveStr = arguments[0].toLowerCase();
            chessGame.move(moveStr);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        } 
    }

    public String helpCommand()
    {
        return "a8d8, d7c8Q, d7c8N, d7c8R, d7c8B";
    }

    public String helpExplanation()
    {
        return "Perform a move ";
    }
    
}

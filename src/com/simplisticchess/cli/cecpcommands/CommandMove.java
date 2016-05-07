package com.simplisticchess.cli.cecpcommands;

import com.simplisticchess.ChessGame;
import com.simplisticchess.cli.Command;
import com.simplisticchess.move.InvalidMoveException;
import com.simplisticchess.position.InvalidLocationException;

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
        String moveRegex = "[a-h][1-8][a-h][1-8][n|k|b|q|r]?";
        return str.matches(moveRegex);
    }

    public void execute(String[] arguments)
    {
        try 
        {
            chessGame.move(arguments[0]);
        }
        catch (InvalidLocationException e)
        {
            System.out.println("Invalid location given");
        }
        catch (InvalidMoveException e) 
        {
            System.out.println("Invalid move");
        } 
    }

    public String helpCommand()
    {
        return "a8d8";
    }

    public String helpExplanation()
    {
        return "Move ";
    }
    
}

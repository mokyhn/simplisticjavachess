package com.simplisticchess.cli.cecpcommands;

import com.simplisticchess.ChessGame;
import com.simplisticchess.cli.Command;

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
        chessGame.move(arguments[0]);
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

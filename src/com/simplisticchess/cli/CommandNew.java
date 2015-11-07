package com.simplisticchess.cli;

import com.simplisticchess.ChessGame;
import com.simplisticchess.board.Board;

/**
 *
 * @author Morten Kühnrich
 */
public class CommandNew implements Command
{
    private final ChessGame chessGame;
    
    public CommandNew(ChessGame chessGame) 
    {
        this.chessGame = chessGame;
    }

    public boolean isApplicable(String str)
    {
        return str.matches("new");
    }

    public void execute(String[] arguments)
    {
        chessGame.setBoard(new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"));
    }

    public String helpCommand()
    {
        return "new";
    }

    public String helpExplanation()
    {
        return "Start over";
    }
}

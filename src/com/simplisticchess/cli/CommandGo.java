package com.simplisticchess.cli;

import com.simplisticchess.ChessGame;
import com.simplisticchess.board.Board;
import com.simplisticchess.search.AbstractSearch;
import com.simplisticchess.search.AlphaBetaSearch;


/**
 *
 * @author Morten Kühnrich
 */
@ChessEngineCommunicationProtocol
public class CommandGo implements Command
{

    private final ChessGame chessGame;

    public CommandGo(ChessGame chessGame)
    {
        this.chessGame = chessGame;
    }

    public boolean isApplicable(String str)
    {
        return str.matches("go");
    }

    public void execute(String[] arguments)
    {
        AbstractSearch engine = new AlphaBetaSearch();

        engine.setPlyDepth(chessGame.getSearchDepth());
        engine.setBoard(chessGame.getBoard());
        try
        {
            engine.dosearch();
        } catch (Exception ex)
        {
            System.out.print("\nError ");
            ex.printStackTrace();
        }
        System.out.println(engine.getStatistics());
        if (engine.getStrongestMove() != null)
        {
            chessGame.getBoard().performMove(engine.getStrongestMove());
            checkForDrawOrMate(chessGame.getBoard());
            System.out.println(chessGame.getBoard().toString());
        }
    }

    private void checkForDrawOrMate(Board b)
    {
        if (b.isDraw())
        {
            System.out.println("Draw");
            //System.exit(0);
        }

        if (b.isMate())
        {
            System.out.println("Mate");
            //System.exit(0);
        }

        if (b.drawBy3RepetionsRule())
        {
            System.out.println("Draw by threefold repetition...");
            //System.exit(0);
        }

        if (b.drawBy50MoveRule())
        {
            System.out.println("Draw by 50 moves rule...");
            //System.exit(0);
        }
    }

    public String helpCommand()
    {
        return "go";
    }

    public String helpExplanation()
    {
        return "Engine plays the color that is on move";
    }

}

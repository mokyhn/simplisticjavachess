package com.simplisticchess;

import com.simplisticchess.board.Board;
import com.simplisticchess.search.AbstractSearch;
import com.simplisticchess.search.AlphaBetaSearch;

/**
 *
 * @author Morten Kühnrich
 */
public class ChessGame
{
    private Board board;
    private AbstractSearch selectedEngine;
    private int searchDepth = 5;
    
    public ChessGame()
    {
        board = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
    }

    public void setBoard(Board board)
    {
        this.board = board;
    }
    
    public Board getBoard() 
    {
        return board;
    }
    
    public int getSearchDepth()
    {
        return searchDepth;
    }

    public void setSearchDepth(int searchDepth)
    {
        this.searchDepth = searchDepth;
    }

    public AbstractSearch getSelectedEngine()
    {
        return selectedEngine;
    }

    public void setSelectedEngine(AbstractSearch selectedEngine)
    {
        this.selectedEngine = selectedEngine;
    }

    public void go()
    {
        AbstractSearch engine = new AlphaBetaSearch();

        engine.setPlyDepth(getSearchDepth());
        engine.setBoard(board);
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
            board.performMove(engine.getStrongestMove());
            checkForDrawOrMate(board);
            print();
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
    
    
    public void newgame()
    {
        board = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
    }

    public void print()
    {
        System.out.println(board.getASCIIBoard());
    }
    
    public void undo()
    {
        board.undo();
    }


 


}

/**
 *
 * @author Morten Kühnrich
 */


package com.simplisticchess;

import com.simplisticchess.move.MoveParser;
import com.simplisticchess.board.Board;
import com.simplisticchess.board.FENUtils;
import com.simplisticchess.move.Move;
import com.simplisticchess.move.InvalidMoveException;
import com.simplisticchess.movegenerator.MoveGenerator;
import com.simplisticchess.position.InvalidLocationException;
import com.simplisticchess.search.AbstractSearch;
import com.simplisticchess.search.AlphaBetaSearch;
import java.util.Iterator;

public class ChessGame
{
    private static final String INITIAL_POSITION = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq";
    
    private Board board;
    private final AbstractSearch engine;
    private final MoveGenerator moveGenerator = new MoveGenerator();
    
    public ChessGame()
    {
        board = new Board(INITIAL_POSITION);
        engine = new AlphaBetaSearch();
    }

    
    public void setBoard(String fen)
    {
        this.board = new Board(fen);
    }
    
    public void setSearchDepth(int searchDepth)
    {
        engine.setPlyDepth(searchDepth); 
    }

    public void black()
    {
        board.setBlackToMove();
    }
    
    public void newgame()
    {
        board = new Board(INITIAL_POSITION);
    }

    public void print()
    {
        System.out.println(board.getASCIIBoard());

        if (board.getGameResult() == null)
        {
            return; // Keep silent;
        }

        switch (board.getGameResult())
        {
            case DRAW:
                System.out.println("Draw");
                break;
            case STALE_MATE:
                break;
            case MATE:
                System.out.println("Mate");
                break;
            case DRAW_BY_50_MOVE_RULE:
                System.out.println("Draw by 50 moves rule...");
                break;
            case DRAW_BY_REPETITION:
                System.out.println("Draw by threefold repetition...");
                break;
            default:
        }

    }
      
    public void undo()
    {
        board.undo();
    }

    public void white()
    {
        board.setWhiteToMove();
    }

    public void setSd(int depth)
    {
        engine.setPlyDepth(depth);
    }

   public void go() throws Exception
    {
        engine.setBoard(board);
        engine.dosearch();
        System.out.println(engine.getStatistics());
        if (engine.getStrongestMove() != null)
        {
            board.doMove(engine.getStrongestMove());
            print();
        }
    }
   
    public void move(String str) throws InvalidLocationException, InvalidMoveException
    {
        Move move = MoveParser.parseMove(board, str);

        if (move == null || board.isDraw() || board.isMate()) 
        {
            throw new InvalidMoveException();
        }
        else
        {
            Iterator<Move> possibleMoves = moveGenerator.generateMoves(board);
            while (possibleMoves.hasNext())
            {
                if (move.equals(possibleMoves.next()))
                {
                    boolean result = board.doMove(move);
                    if (result)
                    {
                        break;
                    }
                    else
                    {
                        throw new InvalidMoveException();
                    }
                }
            }
        } 

       print();   
    }
    
}

/**
 *
 * @author Morten Kühnrich
 */


package com.simplisticchess;

import com.simplisticchess.move.MoveParser;
import com.simplisticchess.board.Board;
import com.simplisticchess.move.Move;
import com.simplisticchess.move.InvalidMoveException;
import com.simplisticchess.movegenerator.MoveGenerator;
import com.simplisticchess.position.InvalidLocationException;
import com.simplisticchess.search.AbstractSearch;
import com.simplisticchess.search.AlphaBetaSearch;
import java.util.Iterator;

public class ChessGame
{
    private Board board;
    private final AbstractSearch engine;
    private final MoveGenerator moveGenerator = new MoveGenerator();
    
    public ChessGame()
    {
        board = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
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
  
    public void go()
    {
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
            board.doMove(engine.getStrongestMove());
            checkForDrawOrMate(board);
            print();
        }
    }

    private void checkForDrawOrMate(Board b)
    {
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
                throw new AssertionError(board.getGameResult().name());
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

    public void white()
    {
        board.setWhiteToMove();
    }

    public void setSd(int depth)
    {
        engine.setPlyDepth(depth);
    }

    public void move(String str)
    {
        try
                {
                    Move m = MoveParser.parseMove(board, str);
                    if (!board.isDraw() || !board.isMate())
                    {
                        Iterator<Move> theMoves = moveGenerator.generateMoves(board);
                        // Check if move m is among the possible moves
                        while (theMoves.hasNext())
                        {
                            if (m.equals(theMoves.next()))
                            {
                                boolean result = board.doMove(m);
                                if (result)
                                {
                                    break;
                                } else 
                                {
                                    throw new InvalidMoveException();
                                }
                            }
                        }
                    } else
                    {
                        throw new InvalidMoveException();
                    }

                    checkForDrawOrMate(board);
                    System.out.println(board.getASCIIBoard());

                }
                catch (InvalidMoveException e)
                {
                    System.out.println("Not a valid move " + e.err);
                }
                catch (InvalidLocationException e)
                {
                    System.out.println("Invalid location entered");
                }
    }

}

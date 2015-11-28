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

/**
 *
 * @author Morten Kühnrich
 */
public class ChessGame
{
    private Board board;
    private AbstractSearch engine;
    
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
                        Iterator<Move> theMoves = MoveGenerator.generateAllMoves(board).listIterator();
                        // Check if move m is among the possible moves
                        while (theMoves.hasNext())
                        {
                            if (m.equals(theMoves.next()))
                            {
                                boolean result = board.performMove(m);
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

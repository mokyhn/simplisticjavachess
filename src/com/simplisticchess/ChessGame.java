package com.simplisticchess;

import com.simplisticchess.board.Board;
import com.simplisticchess.move.Move;
import com.simplisticchess.move.NoMoveException;
import com.simplisticchess.movegenerator.MoveGenerator;
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
    private AbstractSearch selectedEngine;
    private int searchDepth = 5;
    
    public ChessGame()
    {
        board = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        selectedEngine = new AlphaBetaSearch();
    }

    public void setBoard(Board board)
    {
        this.board = board;
    }
    
    public void setBoard(String fen)
    {
        this.board = new Board(fen);
    }
    
    public Board getBoard() 
    {
        return board;
    }
    
    public void setSearchDepth(int searchDepth)
    {
        selectedEngine.setPlyDepth(searchDepth); 
    }

    public AbstractSearch getSelectedEngine()
    {
        return selectedEngine;
    }

    public void setSelectedEngine(AbstractSearch selectedEngine)
    {
        this.selectedEngine = selectedEngine;
    }

    public void black()
    {
        board.setBlackToMove();
    }
  
    public void go()
    {
        selectedEngine.setBoard(board);
        try
        {
            selectedEngine.dosearch();
        } catch (Exception ex)
        {
            System.out.print("\nError ");
            ex.printStackTrace();
        }
        System.out.println(selectedEngine.getStatistics());
        if (selectedEngine.getStrongestMove() != null)
        {
            board.performMove(selectedEngine.getStrongestMove());
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
        selectedEngine.setPlyDepth(depth);
    }

    public void move(String str)
    {
        try
                {
                    Move m = ChessIO.parseMove(board, str);
                    if (!board.isDraw() || !board.isMate())
                    {
                        Iterator<Move> theMoves = MoveGenerator.generateAllMoves(board).listIterator();
                        // Check if move m is among the possible moves
                        while (theMoves.hasNext())
                        {
                            if (m.equal(theMoves.next()))
                            {
                                boolean result = board.performMove(m);
                                if (result)
                                {
                                    break;
                                } else 
                                {
                                    throw new NoMoveException();
                                }
                            }
                        }
                    } else
                    {
                        throw new NoMoveException();
                    }

                    checkForDrawOrMate(board);
                    System.out.println(board.getASCIIBoard());

                } catch (NoMoveException e)
                {
                    System.out.println("Not a valid move " + e.err);
                }
    }

 



}

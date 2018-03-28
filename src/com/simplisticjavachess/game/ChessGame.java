/**
 *
 * @author Morten Kühnrich
 */


package com.simplisticjavachess.game;

import com.simplisticjavachess.move.MoveParser;
import com.simplisticjavachess.board.Board;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.move.InvalidMoveException;
import com.simplisticjavachess.movegenerator.MoveGenerator;
import com.simplisticjavachess.board.InvalidLocationException;
import com.simplisticjavachess.search.Search;
import com.simplisticjavachess.search.AlphaBetaSearch;
import com.simplisticjavachess.search.SearchResult;
import java.util.Iterator;

public class ChessGame
{
    private static final String INITIAL_POSITION = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq";
    
    private Board board;
    private final Search engine;
    private final MoveGenerator moveGenerator = new MoveGenerator();
    private int searchDepth;
    
    public ChessGame()
    {
        board = Board.createFromFEN(INITIAL_POSITION);
        engine = new AlphaBetaSearch();
        searchDepth = 3;
    }

    
    public void setBoard(String fen)
    {
        this.board = Board.createFromFEN(fen);
    }

    public void black()
    {
        board.setBlackToMove();
    }
    
    public void newgame()
    {
        board = Board.createFromFEN(INITIAL_POSITION);
    }

    public void print()
    {
        System.out.println(board.asASCII());

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
        this.searchDepth = depth;
    }

   public void go() throws Exception
    {
        SearchResult searchResult = engine.search(board, searchDepth);

        if (searchResult.getMove() != null)
        {
            System.out.println(searchResult.toString());
            
            board.doMove(searchResult.getMove());
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

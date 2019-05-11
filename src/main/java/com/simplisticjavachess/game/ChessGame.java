/**
 *
 * @author Morten Kühnrich
 */


package com.simplisticjavachess.game;

import com.simplisticjavachess.evaluator.Evaluator;
import com.simplisticjavachess.move.MoveParser;
import com.simplisticjavachess.board.Board;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.movegenerator.MoveGenerator;
import com.simplisticjavachess.engine.MinMaxEngine;
import com.simplisticjavachess.engine.SearchResult;

import java.util.Iterator;

public class ChessGame
{
    private static final String INITIAL_POSITION = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq";
    
    private Board board;
    private final MoveGenerator moveGenerator = new MoveGenerator();
    private int searchDepth;
    
    public ChessGame()
    {
        board = Board.createFromFEN(INITIAL_POSITION);
        searchDepth = 3;
    }

    
    public void setBoard(String fen)
    {
        this.board = Board.createFromFEN(fen);
    }

    public void black()
    {
        board = board.setBlackToMove();
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
            return;
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

    public void white()
    {
        board = board.setWhiteToMove();
    }

    public void setSd(int depth)
    {
        this.searchDepth = depth;
    }

   public void go()
    {
        SearchResult searchResult = new MinMaxEngine(new MoveGenerator(), new Evaluator()).search(board, searchDepth);

        if (searchResult.getMoveSequence() != null)
        {
            board.doMove(searchResult.getMoveSequence().getFirst());
            System.out.println(searchResult.toString());
            print();
        }
    }
   
    public void move(String str)
    {
        Move move = MoveParser.parseMove(board, str);

        if (move == null || board.isDraw() || board.isMate()) 
        {
            throw new IllegalArgumentException("Invalid move");
        }
        else
        {
            Iterator<Move> possibleMoves = moveGenerator.generateMoves(board);
            while (possibleMoves.hasNext())
            {
                if (move.equals(possibleMoves.next()))
                {
                    boolean result = board.doMove(move).isMoveLegal();
                    if (result)
                    {
                        break;
                    }
                    else
                    {
                        throw new IllegalArgumentException("Invalid move");
                    }
                }
            }
        } 

       print();   
    }

    public void undo() {
        throw new IllegalStateException();
    }
}

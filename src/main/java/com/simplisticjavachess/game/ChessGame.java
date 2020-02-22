/**
 *
 * @author Morten Kühnrich
 */


package com.simplisticjavachess.game;

import com.simplisticjavachess.board.MoveResult;
import com.simplisticjavachess.evaluation.IntegerEvaluator;
import com.simplisticjavachess.move.MoveParser;
import com.simplisticjavachess.board.Board;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.movegenerator.MoveGenerator;
import com.simplisticjavachess.engine.MinMaxEngine;
import com.simplisticjavachess.engine.SearchResult;

import java.util.List;

import static com.simplisticjavachess.misc.IteratorUtils.toList;

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
        SearchResult searchResult = new MinMaxEngine(new MoveGenerator(), new IntegerEvaluator()).search(board, searchDepth);

        if (searchResult.getMoveSequence() != null)
        {
            MoveResult moveResult = board.doMove(searchResult.getMoveSequence().getFirst());
            board = moveResult.getBoard();
            System.out.println(searchResult.toString());
            print();
        }
    }
   
    public void move(String str)
    {
        Move move = MoveParser.parse(board, str);

        if (move == null || board.isDraw() || board.isMate())
        {
            throw new IllegalArgumentException("Invalid move");
        }
        else
        {
            List<Move> possibleMoves = toList(moveGenerator.generateMoves(board));
            if (possibleMoves.contains(move))
            {
                MoveResult result = board.doMove(move);
                if (result.isMoveLegal())
                {
                    board = result.getBoard();
                } else
                {
                    throw new IllegalArgumentException("Invalid move");
                }
            } else
            {
                throw new IllegalArgumentException("Invalid move");
            }
        } 

       print();   
    }

    public void undo() {
        throw new IllegalStateException();
    }
}

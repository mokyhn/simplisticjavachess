/**
 *
 * @author Morten Kühnrich
 */


package com.simplisticjavachess.game;

import com.simplisticjavachess.board.BoardParser;
import com.simplisticjavachess.board.IllegalMoveException;
import com.simplisticjavachess.board.Mover;
import com.simplisticjavachess.board.Position;
import com.simplisticjavachess.evaluation.IntegerEvaluator;
import com.simplisticjavachess.move.MoveParser;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.movegenerator.MainMoveGenerator;
import com.simplisticjavachess.engine.MinMaxEngine;
import com.simplisticjavachess.engine.SearchResult;
import com.simplisticjavachess.movegenerator.MoveGenerator;
import com.simplisticjavachess.movegenerator.OpeningBook;
import com.simplisticjavachess.piece.Color;

import java.util.Iterator;
import java.util.List;

import static com.simplisticjavachess.misc.IteratorUtils.toList;

public class ChessGame
{
    private static final String INITIAL_POSITION = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 0";
    
    private Position position;
    private final MoveGenerator moveGenerator = new MainMoveGenerator();
    private int searchDepth;
    
    public ChessGame()
    {
        position = BoardParser.FEN(INITIAL_POSITION);
        searchDepth = 3;
    }

    
    public void setPosition(String fen)
    {
        this.position = BoardParser.FEN(fen);
    }

    public void black()
    {
        position = position.setInMove(Color.BLACK);
    }
    
    public void newgame()
    {
        position = BoardParser.FEN(INITIAL_POSITION);
    }

    public void print()
    {
        System.out.println(position.toString());
    }

    public void white()
    {
        position = position.setInMove(Color.WHITE);
    }

    public void setSd(int depth)
    {
        this.searchDepth = depth;
    }

   public void go()
    {
        Move move = null;

        Iterator<Move> openingMove = OpeningBook.get().  generateMoves(position);
        if (openingMove.hasNext())
        {
            move = openingMove.next();
            System.out.println("Book move " + move.toString());
        }
        else
        {
            SearchResult searchResult = new MinMaxEngine().search(position, new MainMoveGenerator(), new IntegerEvaluator(), searchDepth);

            if (searchResult.getMoveSequence() != null)
            {
                move = searchResult.getMoveSequence().getFirst();
                System.out.println(searchResult.toString());
            }
        }

        if (move == null)
        {
            System.out.println("No legal moves for computer.");
        }
        else
        {
            try
            {
                position = Mover.doMove(position, move);
            }
            catch (IllegalMoveException e)
            {
                e.printStackTrace();
            }
            print();
        }
    }
   
    public void move(String str)
    {
        Move move = MoveParser.parse(position, str);

        if (move == null)
        {
            throw new IllegalArgumentException("Invalid move");
        }
        else
        {
            List<Move> possibleMoves = toList(moveGenerator.generateMoves(position));
            if (possibleMoves.contains(move))
            {
                try
                {
                    position = Mover.doMove(position, move);
                }
                catch (IllegalMoveException e)
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

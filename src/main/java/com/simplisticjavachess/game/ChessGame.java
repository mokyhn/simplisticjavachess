/**
 *
 * @author Morten Kühnrich
 */


package com.simplisticjavachess.game;

import com.simplisticjavachess.board.History;
import com.simplisticjavachess.board.IllegalMoveException;
import com.simplisticjavachess.board.Mover;
import com.simplisticjavachess.board.PositionInference;
import com.simplisticjavachess.evaluation.IntegerEvaluator;
import com.simplisticjavachess.move.MoveParser;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.movegenerator.MainMoveGenerator;
import com.simplisticjavachess.engine.MinMaxEngine;
import com.simplisticjavachess.engine.SearchResult;
import com.simplisticjavachess.movegenerator.MoveGenerator;
import com.simplisticjavachess.movegenerator.OpeningBook;

import java.util.Iterator;
import java.util.List;

import static com.simplisticjavachess.misc.IteratorUtils.toList;

public class ChessGame
{
    private static final String INITIAL_POSITION = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 0";

    private History history;
    private final MoveGenerator moveGenerator = new MainMoveGenerator();
    private int searchDepth;
    private boolean gameOver;

    public ChessGame()
    {
        history = new History(INITIAL_POSITION);
        searchDepth = 3;
        gameOver = false;
    }

    public void setPosition(String fen)
    {
        this.history = new History(fen);
    }

    public void newGame()
    {
        history = new History(INITIAL_POSITION);
        gameOver = false;
    }

    public void print()
    {
        System.out.println(history.getCurrent().toString());
    }

    public void setSd(int depth)
    {
        this.searchDepth = depth;
    }

   public void go()
    {
        Move move = null;

        if (gameOver)
        {
            return;
        }

        Iterator<Move> openingMove = OpeningBook.get().  generateMoves(history.getCurrent());
        if (openingMove.hasNext())
        {
            move = openingMove.next();
            System.out.println("Book move " + move.toString());
        }
        else
        {
            SearchResult searchResult = new MinMaxEngine().search(history.getCurrent(), new MainMoveGenerator(), new IntegerEvaluator(), searchDepth);

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
                history = history.add(Mover.doMove(history.getCurrent(), move));
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
        if (gameOver)
        {
            return;
        }

        Move move = MoveParser.parse(history.getCurrent(), str);

        if (move == null)
        {
            throw new IllegalArgumentException("Invalid move");
        }
        else
        {
            List<Move> possibleMoves = toList(moveGenerator.generateMoves(history.getCurrent()));
            if (possibleMoves.contains(move))
            {
                try
                {
                    history = history.add(Mover.doMove(history.getCurrent(), move));
                    switch (PositionInference.getGameResult(history))
                    {
                        case NO_RESULT:
                            break;
                        case DRAW:
                            System.out.println("Draw!");
                            gameOver = true;
                            break;
                        case MATE:
                            System.out.println("Mate!");
                            gameOver = true;
                            break;
                    }
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

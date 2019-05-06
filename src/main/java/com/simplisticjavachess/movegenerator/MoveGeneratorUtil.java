/**
 *
 * @author Morten Kühnrich
 */

package com.simplisticjavachess.movegenerator;

import com.simplisticjavachess.board.Board;
import com.simplisticjavachess.board.Location;
import com.simplisticjavachess.board.Vector;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.move.MoveType;
import com.simplisticjavachess.piece.Piece;

import java.util.Iterator;
import java.util.concurrent.Callable;

public class MoveGeneratorUtil
{
    public static Move genKingMove(Board board, Piece piece, Vector vector)
    {
        return genMoveAux(board, piece, vector, true);
    }


    public static Move genMove(Board board, Piece piece, Vector vector)
    {
        return genMoveAux(board, piece, vector, false);
    }

    // Used for generation of knight, bishop, rook and queen moves
    private static Move genMoveAux(Board board, Piece piece, Vector vector, boolean isKingMove)
    {
        Location from = piece.getLocation();
        Location to = vector.translocate(from);

        if (to.isValid())
        {
            if (isKingMove)
            {
                if (board.isAttacked(to))
                {
                    return null;
                }
            }
            
            Piece capturedPiece = board.getPiece(to);

            Move move = null;
            MoveType moveType;

            if (capturedPiece != null && capturedPiece.getColor() == board.inMove().opponent())
            {
                moveType = MoveType.CAPTURE;
                move = new Move(from, to, moveType, capturedPiece, board.inMove());
            } 
            else
            {
                if (board.freeSquare(to))
                {
                    moveType = MoveType.NORMALMOVE;
                    move = new Move(from, to, moveType, null, board.inMove());
                }
            }
            
            
            return move;
            
            
        }
        else
        {
            return null;
        }        
    }


	public static Iterator<Move> oneMoveIterator(Callable<Move> callable)
    {
        return new Iterator<Move>() {
            Move move;
            boolean isDone;

            @Override
            public boolean hasNext() {
                if (isDone)
                {
                    return false;
                } else
                {
                    try {
                        move = callable.call();
                    } catch (Exception e) {
                        move = null;
                    }
                    return move != null;
                }
            }

            @Override
            public Move next() {
                if (isDone)
                {
                    return null;
                }
                else
                {
                    isDone = true;
                    return move;
                }
            }
        };


    }

}

/**
 *
 * @author Morten Kühnrich
 */

package com.simplisticjavachess.movegenerator;

import com.simplisticjavachess.board.Location;
import com.simplisticjavachess.board.Position;
import com.simplisticjavachess.board.Vector;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.move.MoveType;
import com.simplisticjavachess.piece.Piece;

import java.util.Iterator;
import java.util.concurrent.Callable;

public class MoveGeneratorUtil
{
    public static Move genKingMove(Position position, Piece piece, Vector vector)
    {
        return genMoveAux(position, piece, vector, true);
    }


    public static Move genMove(Position position, Piece piece, Vector vector)
    {
        return genMoveAux(position, piece, vector, false);
    }

    // Used for generation of knight, bishop, rook and queen moves
    private static Move genMoveAux(Position position, Piece piece, Vector vector, boolean isKingMove)
    {
        Location from = piece.getLocation();
        Location to = vector.translocate(from);

        if (to.isValid())
        {
            if (isKingMove)
            {
                if (position.isAttacked(to))
                {
                    return null;
                }
            }
            
            Piece capturedPiece = position.getPiece(to);

            Move move = null;
            MoveType moveType;

            if (capturedPiece != null && capturedPiece.getColor() == position.inMove().opponent())
            {
                moveType = MoveType.CAPTURE;
                move = new Move(from, to, moveType, capturedPiece, position.inMove());
            } 
            else
            {
                if (position.freeSquare(to))
                {
                    moveType = MoveType.NORMALMOVE;
                    move = new Move(from, to, moveType, null, position.inMove());
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

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

public class MoveGeneratorUtil
{

    // Used for generation of knight, bishop, rook and queen moves
    public static Move genMove(Board board, Piece fp, Vector v)
    {
        Location from = fp.getLocation();
        Location to = v.translocate(from);

        if (to.isValid())
        {
            Piece takenPiece = board.getPiece(to);

            Move move = null;
            MoveType moveType;

            if (takenPiece != null && takenPiece.getColor() == board.inMove().opponent())
            {
                moveType = MoveType.CAPTURE;
                move = new Move(from, to, moveType, takenPiece, board.inMove());
            } 
            else
            {
                if (board.freeSquare(to))
                {
                    takenPiece = null;
                    moveType = MoveType.NORMALMOVE;
                    move = new Move(from, to, moveType, takenPiece, board.inMove());
                }
            }
            return move;
        }
        else
        {
            return null;
        }        
    }
    
}

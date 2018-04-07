/**
 *
 * @author Morten Kühnrich
 */

package com.simplisticjavachess.movegenerator;

import com.simplisticjavachess.board.Board;
import com.simplisticjavachess.board.Location;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.move.MoveType;
import com.simplisticjavachess.piece.Piece;

public class MoveGeneratorUtil
{

    // Used for generation of knight, bishop, rook and queen moves
    public static Move genMove(Board board, Piece fp, int dX, int dY)
    {
        Location from = fp.getLocation();
        
        final int tX = fp.getxPos() + dX;
        final int tY = fp.getyPos() + dY;
        
        MoveType moveType;
        assert fp.getColor() == board.inMove();
        if (tX < 0 || tX > 7 || tY < 0 || tY > 7)
        {
            return null;
        }

        Location to = new Location(tX, tY);
        Piece takenPiece = board.getPiece(to);
        
        Move move = null;
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
    
}

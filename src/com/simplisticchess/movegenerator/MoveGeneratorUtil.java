/**
 *
 * @author Morten Kühnrich
 */

package com.simplisticchess.movegenerator;

import com.simplisticchess.board.Board;
import com.simplisticchess.move.Move;
import com.simplisticchess.move.MoveType;
import com.simplisticchess.piece.Piece;
import com.simplisticchess.piece.PieceType;

public class MoveGeneratorUtil
{

    // Used for generation of knight, bishop, rook and queen moves
    public static Move genMove(Board b, Piece fp, int dX, int dY)
    {
        Piece tp;
        Move m = null;
        final int tX = fp.getxPos() + dX;
        final int tY = fp.getyPos() + dY;
        MoveType moveType;
        assert fp.getColor() == b.inMove();
        if (fp.getxPos() < 0 || fp.getxPos() > 7 || fp.getyPos() < 0 || fp.getyPos() > 7 || tX < 0 || tX > 7 || tY < 0 || tY > 7)
        {
            return null;
        }
        tp = b.getPiece(tX, tY);
        PieceType takenPiece;
        if (tp != null && tp.getColor() == b.inMove().opponent())
        {
            takenPiece = tp.getPieceType();
            moveType = MoveType.CAPTURE;
            m = new Move(fp.getxPos(), fp.getyPos(), tX, tY, moveType, takenPiece, b.inMove());
        } else
        {
            if (b.freeSquare(tX, tY))
            {
                takenPiece = null;
                moveType = MoveType.NORMALMOVE;
                m = new Move(fp.getxPos(), fp.getyPos(), tX, tY, moveType, takenPiece, b.inMove());
            }
        }
        return m;
    }
    
}

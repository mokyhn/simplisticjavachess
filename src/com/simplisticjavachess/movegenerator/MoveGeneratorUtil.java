/**
 *
 * @author Morten Kühnrich
 */

package com.simplisticjavachess.movegenerator;

import com.simplisticjavachess.board.Board;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.move.MoveType;
import com.simplisticjavachess.piece.Piece;

public class MoveGeneratorUtil
{

    // Used for generation of knight, bishop, rook and queen moves
    public static Move genMove(Board board, Piece fp, int dX, int dY)
    {
        final int tX = fp.getxPos() + dX;
        final int tY = fp.getyPos() + dY;
        MoveType moveType;
        assert fp.getColor() == board.inMove();
        if (fp.getxPos() < 0 || fp.getxPos() > 7 || fp.getyPos() < 0 || fp.getyPos() > 7 || tX < 0 || tX > 7 || tY < 0 || tY > 7)
        {
            return null;
        }
        Piece takenPiece = board.getPiece(tX, tY);

        Move move = null;
        if (takenPiece != null && takenPiece.getColor() == board.inMove().opponent())
        {
            moveType = MoveType.CAPTURE;
            move = new Move(fp.getxPos(), fp.getyPos(), tX, tY, moveType, takenPiece, board.inMove());
        } 
        else
        {
            if (board.freeSquare(tX, tY))
            {
                takenPiece = null;
                moveType = MoveType.NORMALMOVE;
                move = new Move(fp.getxPos(), fp.getyPos(), tX, tY, moveType, takenPiece, board.inMove());
            }
        }
        return move;
    }
    
}

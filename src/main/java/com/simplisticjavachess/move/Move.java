/**
 *
 * @author Morten Kühnrich
 */

package com.simplisticjavachess.move;

import com.simplisticjavachess.piece.Color;
import com.simplisticjavachess.piece.Piece;
import com.simplisticjavachess.piece.PieceType;
import com.simplisticjavachess.position.Location;

import java.util.Objects;

public final class Move
{

    private final Location from;
    private final Location to;
    
    private final MoveType moveType; 
    
    private final Piece capturedPiece; 

    private final Color whoMoves;

    public Move(Location from, Location to, MoveType type, Piece capturedPiece, Color whoMoves)
    {
        if (from.isValid() && to.isValid())
        {
            // It is not legal to capture the king
            if (capturedPiece != null && PieceType.KING.equals(capturedPiece.getType()))
            {
                throw new IllegalArgumentException();
            }

            this.from = from;
            this.to = to;
            this.moveType = type;
            this.capturedPiece = capturedPiece;
            this.whoMoves = whoMoves;
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

    public boolean aCapture()
    {
        return getMoveType().isCapture();
    }

    public PieceType promotionTo()
    {
        return getMoveType().getPromotionPiece();
    }
   
    @Override
    public boolean equals(Object object)
    {
        if (object instanceof Move) 
        {
            Move move = (Move) object;

            return this.from.equals(move.from)
                && this.to.equals(move.to)
                && this.getMoveType() == move.getMoveType()
                && Objects.equals(this.getCapturedPiece(), move.getCapturedPiece())
                && this.getWhoMoves() == move.getWhoMoves();
        }
        else
        {
            return false;
        }
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(this.from, this.to, this.moveType, this.capturedPiece, this.whoMoves);
    }

    @Override
    public String toString()
    {
        switch (moveType)
        {
            case NORMALMOVE:
                return from.toString() + "-" + to.toString();
            case CAPTURE:
                if (capturedPiece.getType() == PieceType.KING)
                {
                    return "mate";
 
                }
            
                /* Intended fall through */
            
            case CAPTURE_ENPASSANT:
                return from.toString() + "x" + to.toString();
            case CASTLE_SHORT:
                return "o-o";
            case CASTLE_LONG:
                return "o-o-o";
        }

        if (aSimplePromotion())
        {
            return from.toString() + "-" + to.toString() + "=" + promotionTo().getPieceLetter();
        }

        if (aCapturePromotion())
        {
            return from.toString() + "x" + to.toString() + "=" + promotionTo().getPieceLetter(); 
        }
     
        return null;
    }

    private boolean aSimplePromotion()
    {
        return getMoveType().isSimplePromotion();
    }

    private boolean aCapturePromotion()
    {
        return getMoveType().isCapturePromotion();
    }

    public Location getFrom()
    {
        return from;
    }
    
    public Location getTo()
    {
        return to;
    }
   
    public MoveType getMoveType()
    {
        return moveType;
    }


    public Piece getCapturedPiece()
    {
        return capturedPiece;
    }


    public Color getWhoMoves()
    {
        return whoMoves;
    }


    public int distance()
    {
        return from.distanceTo(to);
    }
}

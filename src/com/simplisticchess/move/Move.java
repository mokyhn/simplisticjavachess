package com.simplisticchess.move;

/**
 *
 * @author Morten Kühnrich
 */

import com.simplisticchess.piece.Color;
import com.simplisticchess.piece.PieceType;
import com.simplisticchess.position.Location;

public final class Move
{

    private final Location from;
    private final Location to;
    
    private final MoveType moveType; 
    private final PieceType capturedPiece; // Used for storing pieces that are taken
                              // by the piece which moves

    private final Color whoMoves;

    public Move(int fromX, int fromY, int toX, int toY, MoveType type, PieceType capturedPiece, Color whoMoves)
    {
        assert (!(fromX == toX && fromY == toY)) : "(fX, fY, tX, tY) = " + "(" + fromX + ", " + fromY + ", " + toX + "," + toY + ")";

        from = new Location(fromX, fromY);
        to = new Location(toX, toY);
        this.moveType = type;
        this.capturedPiece = capturedPiece;
        this.whoMoves = whoMoves;
    }

    public Move (Location from, Location to, MoveType type, PieceType capturedPiece, Color whoMoves)
    {
        this.from = from;
        this.to = to;
        this.moveType = type;
        this.capturedPiece = capturedPiece;
        this.whoMoves = whoMoves;        
    }
    
    public Move(Move move)
    {
        from = new Location(move.getFrom());
        to = new Location(move.getTo());
        moveType = move.moveType;
        whoMoves = move.whoMoves;
        capturedPiece = move.capturedPiece;
    }

    public boolean aCapture()
    {
        return getMoveType().isCapture();
    }

  
    public boolean aSimplePromotion()
    {
        return getMoveType().isSimplePromotion();
    }

    public boolean aCapturePromotion()
    {
        return getMoveType().isCapturePromotion();
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
            Move m = (Move) object;

            return this.from.equals(m.from)
                && this.to.equals(m.to)
                && this.getMoveType() == m.getMoveType()
                && this.getCapturedPiece() == m.getCapturedPiece()
                && this.getWhoMoves() == m.getWhoMoves();
        }
        else
        {
            return false;
        }
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 59 * hash + (this.from != null ? this.from.hashCode() : 0);
        hash = 59 * hash + (this.to != null ? this.to.hashCode() : 0);
        hash = 59 * hash + (this.moveType != null ? this.moveType.hashCode() : 0);
        hash = 59 * hash + (this.capturedPiece != null ? this.capturedPiece.hashCode() : 0);
        hash = 59 * hash + (this.whoMoves != null ? this.whoMoves.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString()
    {
        if (getMoveType() == MoveType.NORMALMOVE)
        {
            return from.toString() + "-" + to.toString();
        }

        // Normal capture moves
        if (getMoveType() == MoveType.CAPTURE_ENPASSANT || getMoveType() == MoveType.CAPTURE)
        {
            return from.toString() + "x" + to.toString();
        }

        // mate
        if (getMoveType() == MoveType.CAPTURE && getCapturedPiece() == PieceType.KING)
        {
            return "mate";
        }

        // Promotions
        if (aSimplePromotion())
        {
            return from.toString() + "-" + to.toString() + "=" + promotionTo().getPieceLetter();
        }

        if (aCapturePromotion())
        {
            return from.toString() + "x" + to.toString() + "=" + promotionTo().getPieceLetter(); 
        }

        if (getMoveType() == MoveType.CASTLE_SHORT)
        {
            return "o-o";
        }
        if (getMoveType() == MoveType.CASTLE_LONG)
        {
            return "o-o-o";
        }

        return "ERR: getMoveStr";
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


    public PieceType getCapturedPiece()
    {
        return capturedPiece;
    }


    public Color getWhoMoves()
    {
        return whoMoves;
    }


}

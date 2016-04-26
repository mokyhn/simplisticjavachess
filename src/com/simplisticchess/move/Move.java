/**
 *
 * @author Morten Kühnrich
 */

package com.simplisticchess.move;



import com.simplisticchess.piece.Color;
import com.simplisticchess.piece.PieceType;
import com.simplisticchess.position.Location;

public final class Move
{

    private final Location from;
    private final Location to;
    
    private final MoveType moveType; 
    
    // The piece captured 
    private final PieceType capturedPiece; 

    private final Color whoMoves;

    public Move(int fromX, int fromY, int toX, int toY, MoveType type, PieceType capturedPiece, Color whoMoves)
    {
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
        switch (moveType)
        {
            case NORMALMOVE:
                return from.toString() + "-" + to.toString();
            case CAPTURE:
                if (capturedPiece == PieceType.KING)
                {
                    return "mate";
 
                }
            
                /* Intented fall through */
            
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

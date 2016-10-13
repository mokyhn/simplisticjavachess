/**
 *
 * @author Morten Kühnrich
 */

package com.simplisticjavachess.move;



import com.simplisticjavachess.piece.Color;
import com.simplisticjavachess.piece.Piece;
import com.simplisticjavachess.piece.PieceType;
import com.simplisticjavachess.position.Location;

public final class Move
{

    private final Location from;
    private final Location to;
    
    private final MoveType moveType; 
    
    private final Piece capturedPiece; 

    private final Color whoMoves;

    public Move(int fromX, int fromY, int toX, int toY, MoveType type, Piece capturedPiece, Color whoMoves)
    {
        from = new Location(fromX, fromY);
        to = new Location(toX, toY);
        this.moveType = type;
        this.capturedPiece = capturedPiece;
        this.whoMoves = whoMoves;
    }

    public Move(Location from, Location to, MoveType type, Piece capturedPiece, Color whoMoves)
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
                && comparePieces(this.getCapturedPiece(), move.getCapturedPiece())
                && this.getWhoMoves() == move.getWhoMoves();
        }
        else
        {
            return false;
        }
    }
    
    private boolean comparePieces(Piece piece1, Piece piece2)
    {
        if (piece1 == null && piece2 == null)
        {
            return true;
        } 
        else if (piece1 == null && piece2 != null) 
        {
            return false;
        }
        else if (piece1 != null && piece2 == null) 
        {
            return false;
        } 
        else
        {
            return piece1.equals(piece2);
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
                if (capturedPiece.getPieceType() == PieceType.KING)
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


}

package com.simplisticjavachess.piece;

import static com.simplisticjavachess.piece.PieceType.*;
import com.simplisticjavachess.board.Location;
import java.util.Objects;

/**
 *
 * @author Morten Kühnrich
 */

public final class Piece {
    private final Location location;    
    private final PieceType pieceType;
    private final Color color;
    
    private final int hashCode;
   
    public Piece(Location location, Color color, PieceType pieceType) {
        if (location.isValid())
        {
            this.location = location;        
            this.color = color;
            this.pieceType  = pieceType;
            this.hashCode = Objects.hash(this.pieceType, this.color, this.location);
        }
        else
        {
            throw new IllegalArgumentException();
        }      
    }
   
    public Piece(Location location, char pieceLetter)  {
        this.location = location;

        switch (pieceLetter) {
            case 'p': pieceType  = PAWN;
                      color = Color.BLACK;
                      break;
            case 'r': pieceType  = ROOK;
                      color = Color.BLACK;
                      break;
            case 'n': pieceType  = KNIGHT;
                      color = Color.BLACK;
                      break;
            case 'b': pieceType  = BISHOP;
                      color = Color.BLACK;
                      break;
            case 'k': pieceType  = KING;
                      color = Color.BLACK;
                      break;
            case 'q': pieceType  = QUEEN;
                      color = Color.BLACK;
                      break;
            case 'P': pieceType  = PAWN;
                      color = Color.WHITE;
                      break;
            case 'R': pieceType  = ROOK;
                      color = Color.WHITE;
                      break;
            case 'N': pieceType  = KNIGHT;
                      color = Color.WHITE;
                      break;
            case 'B': pieceType  = BISHOP;
                      color = Color.WHITE;
                      break;
            case 'K': pieceType  = KING;
                      color = Color.WHITE;
                      break;
            case 'Q': pieceType  = QUEEN;
                      color = Color.WHITE;
                      break;

            default: 
                     throw new IllegalArgumentException("Unexpected error in Piece constructor");
            }
        
        this.hashCode = Objects.hash(this.pieceType, this.color, this.location);
     }

    /**
     * 
     * @param code - example Bd5 means a white bishop at d5, bd5 means a black bishop at d5
     * @return a piece of the color and the location specified
     */
    public static Piece fromPositionCode(String code)
    {
        String locationStr = code.substring(1,3);
        char pieceLetter = code.charAt(0);
        return new Piece(Location.parse(locationStr), pieceLetter);
    }
    
    @Override
    public boolean equals(Object object) 
    {
        if (this == object) {
            return true;
        }

        if (object instanceof Piece) 
        {
            Piece piece = (Piece) object;
            return this.color.equals(piece.color) &&
                   this.pieceType.equals(piece.pieceType) &&
                   this.location.equals(piece.location);
        }
        else 
        {
            return false;
        }
    }

    @Override
    public int hashCode()
    {
        return hashCode;
    }
       
    @Override
    public String toString() {
        return getPieceType().getPieceLetter(getColor());
    }

    public Location getLocation()
    {
        return location;
    }
    
    public int getxPos()
    {
        return location.getX();
    }


    public int getyPos()
    {
        return location.getY();
    }

    public Piece updateLocation(Location location)
    {
        return new Piece(location, this.color, this.pieceType);
    }
    
    public PieceType getPieceType()
    {
        return pieceType;
    }

    public Color getColor()
    {
        return color;
    }

    public String asString()
    {
        return pieceType.getPieceLetter(color) + location.toString();
    }

}


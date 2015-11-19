package com.simplisticchess.piece;

import static com.simplisticchess.piece.PieceType.*;
import com.simplisticchess.position.Location;

/**
 *
 * @author Morten Kühnrich
 */

public final class Piece {
    Location location;
    
    private PieceType pieceType;

    private Color color;
   
    public Piece(Location location, Color color, PieceType pieceType) {
        this.location = new Location(location);        
        this.color = color;
        this.pieceType  = pieceType;
    }

    public Piece(Piece piece) 
    {
        this(piece.getLocation(), piece.color, piece.pieceType);
    }
    
    public Piece(int x, int y, char pieceLetter)  {
        location = new Location(x, y);

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

            default: System.out.println("Unexpected error in Piece constructor");
                     System.exit(1);

            }
     }

    @Override
    public boolean equals(Object object) 
    {
        if (object instanceof Piece) 
        {
            Piece p = (Piece) object;
            return  p.getColor() == this.getColor() &&
                    p.getPieceType()  == this.getPieceType()  &&
                    p.getxPos()  == this.getxPos()  &&
                    p.getyPos()  == this.getyPos();
        }
        else 
        {
            return false;
        }
    }

    @Override
    public int hashCode()
    {
        return this.getColor().getColor() * 1000 + this.getPieceType().getType() * 100 + this.getyPos() * 10 + this.getxPos();
    }
    
    
    // TODO: This one should have it's own method which is not called toString...
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

    public void setxPos(int xPos)
    {
        location = new Location(xPos, location.getY());
    }

    public int getyPos()
    {
        return location.getY();
    }

    public void setyPos(int yPos)
    {
        location = new Location(location.getX(), yPos);
    }

    public void setLocation(Location location) {
        this.location = location;
    }
    
    public PieceType getPieceType()
    {
        return pieceType;
    }

    public void setPieceType(PieceType pieceType)
    {
        this.pieceType = pieceType;
    }

    public Color getColor()
    {
        return color;
    }

    public void setColor(Color color)
    {
        this.color = color;
    }

}


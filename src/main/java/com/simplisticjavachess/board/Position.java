/**
 * @author Morten Kühnrich
 *
 */

package com.simplisticjavachess.board;

import com.simplisticjavachess.piece.Piece;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Position
{
    private final Map<Location, Piece> piecesMap;

    Position()
    {
        piecesMap = new HashMap<>();
    }

    Position(Position position)
    {
        this.piecesMap = new HashMap<>(position.piecesMap);
    }

    void insert(Piece piece)
    {
        if (piecesMap.containsKey(piece.getLocation()))
        {
            throw new IllegalStateException("Tried to insert piece at a location at an occupied location");
        }
        else
        {
            piecesMap.put(piece.getLocation(), piece);
        }
    }
       
    public Piece getPiece(Location location)
    {
        return piecesMap.get(location);
    }
  
    Collection<Piece> getPieces()
    {
        return piecesMap.values();
    }
        
    /**
     * Remove a piece
     * @param piece - to remove
     * @return the removed piece
     */
    void remove(Piece piece)
    {    
        if (piece == null)
        {
             throw new IllegalStateException("Tried to remove a piece which was not there");
        }
        if (piecesMap.containsKey(piece.getLocation()))
        {
            piecesMap.remove(piece.getLocation());
        }
        else
        {
             throw new IllegalStateException("Tried to remove a piece which was not there");
        }

    }
   
    public void move(Piece piece, Location to)
    {
        if (piecesMap.containsKey(to))
        {
            throw new IllegalStateException();
        }
        else
        {
            remove(piece);
            Piece newPiece = piece.updateLocation(to);
            insert(newPiece);
        }
    }

    boolean freeSquare(Location location)
    {
        return freeSquare(location.getX(), location.getY());
    }
    
    boolean freeSquare(int x, int y)
    {
        return !piecesMap.containsKey(new Location(x, y));
    }

    String getPositionString()
    {
        int x, y;
        Piece p;
        String s = "\n _______________\n";

        for (y = 7; y >= 0; y--)
        {
            for (x = 0; x < 8; x++)
            {
                s = s + " ";
                p = getPiece(new Location(x, y));
                if (p == null)
                {
                    s = s + ".";
                } else
                {
                    s = s + p.toString();
                }
            }
            s = s + ("     " + (y + 1)) + "\n";
        } // end last for-loop
        s = s + " _______________\n";
        s = s + " a b c d e f g h\n";
        return s;
    }

    @Override
    public boolean equals(Object object)
    {
        if (object instanceof Position)
        {
            Position position = (Position) object;
            return this.piecesMap.equals(position.piecesMap);
        }
        else
        {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(piecesMap.values());
    }

}

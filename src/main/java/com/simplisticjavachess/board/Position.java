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

    private Position(Position position)
    {
        this.piecesMap = new HashMap<>(position.piecesMap);
    }

    /**
     *
     * @param piece to insert
     * @return the resulting position
     */
    Position insert(Piece piece)
    {
        if (piecesMap.containsKey(piece.getLocation()))
        {
            throw new IllegalStateException("Tried to insert piece at a location at an occupied location");
        }
        else
        {
            Position result = new Position(this);
            result.piecesMap.put(piece.getLocation(), piece);
            return result;
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
     * @return the resulting position
     */
    Position remove(Piece piece)
    {    
        if (piece == null)
        {
             throw new IllegalStateException("Tried to remove a piece which was not there");
        }
        if (piecesMap.containsKey(piece.getLocation()))
        {
            Position result = new Position(this);
            result.piecesMap.remove(piece.getLocation());
            return result;
        }
        else
        {
             throw new IllegalStateException("Tried to remove a piece which was not there");
        }

    }
   
    public Position move(Piece piece, Location to)
    {
        if (piecesMap.containsKey(to))
        {
            throw new IllegalStateException();
        }
        else
        {
            Position tmp = remove(piece);
            Piece newPiece = piece.updateLocation(to);
            return tmp.insert(newPiece);
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

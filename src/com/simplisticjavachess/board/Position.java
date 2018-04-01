/**
 * @author Morten Kühnrich
 *
 */

package com.simplisticjavachess.board;

import com.simplisticjavachess.piece.Piece;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Position
{
    private Map<Location, Piece> piecesMap;    
    
    private BitBoard bitBoard;
            
    public Position()
    {
        piecesMap = new HashMap<Location, Piece>();
        bitBoard = new BitBoard(); 
    }

    public Position(Position position)
    {
        this();
        
        for (Piece piece : position.getPieces())
        {
            this.insertPiece(piece);
        }
    }
 
    public final void insertPiece(Piece piece)
    {
        if (piecesMap.containsKey(piece.getLocation()))
        {
            throw new IllegalStateException("Tried to insert piece at a location at an occupied location");
        }
        else
        {
            piecesMap.put(piece.getLocation(), piece);
            bitBoard.insertPiece(piece);
        }
    }
       
    public Piece getPiece(Location location)
    {
        return piecesMap.get(location);
    }
  
    public Collection<Piece> getPieces() 
    {
        return piecesMap.values();
    }
        
    /**
     * Remove a piece from location and return the piece.
     * @param location of piece to remove
     * @return the removed piece
     */
    public Piece removePiece(Location location)
    {
        Piece p = piecesMap.get(location);
        
        if (p == null)
        {
             throw new IllegalStateException("Tried to remove a piece which was not there");
        }
        else
        {
            piecesMap.remove(location);
            bitBoard.removePiece(location);
            return p;
        }
    }
   
    public void movePiece(Location from, Location to)
    {
        Piece piece = removePiece(from);
        Piece newPiece = piece.updateLocation(to);
        insertPiece(newPiece);
    }

    public boolean freeSquare(Location location)
    {
        return freeSquare(location.getX(), location.getY());
    }
    
    public boolean freeSquare(int x, int y)
    {
        return !piecesMap.containsKey(new Location(x, y));
    }

    public String getPositionString()
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
            return bitBoard.equals(position.bitBoard);
        }
        else
        {
            return false;
        }
    }
    

    public BitBoard getBitBoard()
    {
        return bitBoard;
    }  
  
}

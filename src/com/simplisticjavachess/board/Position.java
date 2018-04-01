/**
 * @author Morten Kühnrich
 *
 */

package com.simplisticjavachess.board;

import com.simplisticjavachess.piece.Piece;
import com.simplisticjavachess.piece.PieceType;
import java.util.ArrayList;
import java.util.Collection;
//import java.util.HashMap;
//import java.util.Map;

public class Position
{
    private static final int DEFAULT_NUMBER_OF_PIECES_CAPACITY = 32;

    //private final Map<Location, Piece> pieces;    
    private ArrayList<Piece> pieces;
    private Piece[][] xyPosition;
    
    private BitBoard bitBoard;
            
    public Position()
    {
        init(DEFAULT_NUMBER_OF_PIECES_CAPACITY);   
        //pieces = new HashMap<Location, Piece>();
        //bitBoard = new BitBoard();  
    }

    public Position(Position position)
    {
        init(position.pieces.size());
        
        for (Piece piece : position.pieces)
        {
            this.pieces.add(piece);
            this.xyPosition[piece.getxPos()][piece.getyPos()] = piece;
        }        
        
        //pieces = new HashMap<Location, Piece>();
        //bitBoard = new BitBoard();  

        //for (Piece piece : pieces.values())
        //{
        //    this.insertPiece(piece);
        //}
        
        for (PieceType t : PieceType.values())
        {
            this.bitBoard.bb[0][t.getType()] = position.bitBoard.bb[0][t.getType()];
            this.bitBoard.bb[1][t.getType()] = position.bitBoard.bb[1][t.getType()];
        }
    }

    private void init(int n)
    {
        pieces = new ArrayList<Piece>(n);
        xyPosition = new Piece[8][8];
        bitBoard = new BitBoard();
    
    }    
    
    public void insertPiece(Piece piece)
    {
        if (xyPosition[piece.getxPos()][piece.getyPos()] == null)
        {
            pieces.add(piece);
            xyPosition[piece.getxPos()][piece.getyPos()] = piece;
            bitBoard.insertPiece(piece);
            //    pieces.put(piece.getLocation(), piece);
            //    bitBoard.insertPiece(piece);
        }
        else
        {
            throw new IllegalStateException("Tried to insert piece at a location at an occupied location");
        }
    }
    
    
    public Piece getPiece(Location location)
    {
        return xyPosition[location.getX()][location.getY()];
        //return pieces.get(location);
    }
  
    public Collection<Piece> getPieces() 
    {
        return pieces;
        //return pieces.values();
    }
        
    /**
     * Remove a piece from location and return the piece.
     * @param location of piece to remove
     * @return the removed piece
     */
    public Piece removePiece(Location location)
    {
        Piece p = xyPosition[location.getX()][location.getY()];
        
        if (p == null)
        {
             throw new IllegalStateException("Tried to remove a piece which was not there");
        }
        else
        {
            pieces.remove(p);
            xyPosition[location.getX()][location.getY()] = null;
            bitBoard.removePiece(location);
            //    pieces.remove(location);
            //    bitBoard.removePiece(location);
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
        return xyPosition[x][y] == null;
        //return !pieces.containsKey(new Location(x, y));
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

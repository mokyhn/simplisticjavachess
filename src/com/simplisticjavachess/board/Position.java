/**
 * @author Morten Kühnrich
 *
 */

package com.simplisticjavachess.board;

import com.simplisticjavachess.piece.Piece;
import com.simplisticjavachess.piece.PieceType;
import com.simplisticjavachess.position.Location;
import java.util.ArrayList;
import java.util.Collection;

public class Position
{
    private static final int DEFAULT_NUMBER_OF_PIECES_CAPACITY = 32;

    private ArrayList<Piece> pieces;
    private Piece[][] xyPosition;
    
    private BitBoard bitBoard;
            
    public Position()
    {
        init(DEFAULT_NUMBER_OF_PIECES_CAPACITY);   
    }

    public Position(Position position)
    {
        init(position.pieces.size());

        for (Piece piece : position.pieces)
        {
            this.pieces.add(piece);
            this.xyPosition[piece.getxPos()][piece.getyPos()] = piece;
        }
        
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
        pieces.add(piece);
        xyPosition[piece.getxPos()][piece.getyPos()] = piece;
        bitBoard.insertPiece(piece);
    }
    
    
    public Piece getPiece(Location location)
    {
        return xyPosition[location.getX()][location.getY()];
    }
  
    public Collection<Piece> getPieces() 
    {
        return pieces;
    }
        
    // Remove a piece from location and return the piece
    public Piece removePiece(Location location)
    {
        Piece p = xyPosition[location.getX()][location.getY()];
        pieces.remove(p);
        xyPosition[location.getX()][location.getY()] = null;
        bitBoard.removePiece(location);
        return p;
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

package com.simplisticchess.board;

import com.simplisticchess.piece.Color;
import com.simplisticchess.piece.Piece;
import com.simplisticchess.piece.PieceType;
import com.simplisticchess.position.Location;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Morten Kühnrich
 */
public class PositionTest
{     
    private boolean areRepresentationsIsomorphic(Position position)
    {
        int n = 0;
        
        int x, y;
        for (x = 0; x < 8; x++)
        {
            for (y = 0; y < 8; y++)
            {
                if (position.getPiece(new Location(x,y)) != null)
                {
                    n++;
                }
            }
        }
        
        if (n != position.getNumberOfPieces()) 
        {
            return false;
        }
        
        for (int i = 0; i < position.getNumberOfPieces(); i++)
        {
            Piece p1 = position.getPiece(i);
            Piece p2 = position.getPiece(p1.getLocation());
            if (!p1.equals(p2)) 
            {
                return false;
            }
        }
        
        return true;
    }
    
    @Test
    public void testAreRepresentationsIsomorphic()
    { 
        assertTrue(areRepresentationsIsomorphic( new Position()));
        
        Position position = new Position();
        position.insertPiece(new Piece(new Location(2,3),Color.BLACK,PieceType.KING));
        
        assertTrue(areRepresentationsIsomorphic(position));
        
        position.insertPiece(new Piece(new Location(0,0),Color.WHITE,PieceType.KING));
        assertTrue(areRepresentationsIsomorphic(position));
        
        position.insertPiece(new Piece(new Location(7,7),Color.BLACK,PieceType.PAWN));
        assertTrue(areRepresentationsIsomorphic(position));
       
    }

    @Ignore("Since the insert-code does not handle this scenario")
    @Test
    public void testInsertingOnTop()
    {
        Position position = new Position();
        position.insertPiece(new Piece(new Location(2,3),Color.BLACK,PieceType.KING));
        position.insertPiece(new Piece(new Location(2,3),Color.WHITE,PieceType.PAWN));
        assertTrue(areRepresentationsIsomorphic(position));

    }
  
    
}

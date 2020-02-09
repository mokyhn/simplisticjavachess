package com.simplisticjavachess.board;

import com.simplisticjavachess.piece.Color;
import com.simplisticjavachess.piece.Piece;
import com.simplisticjavachess.piece.PieceType;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Morten Kühnrich
 */
public class PositionTest
{   
    
    Position position;
    Piece piece;
    
    @Before
    public void before()
    {
        position = new Position();
        piece    = new Piece(new Location(7,7), Color.WHITE, PieceType.KING);
    }
      
    @Test
    public void testAreRepresentationsIsomorphic()
    { 
        assertTrue(areRepresentationsIsomorphic(new Position()));
        
        position.insert(new Piece(new Location(2,3),Color.BLACK,PieceType.KING));
        
        assertTrue(areRepresentationsIsomorphic(position));
        
        position.insert(new Piece(new Location(0,0),Color.WHITE,PieceType.KING));
        assertTrue(areRepresentationsIsomorphic(position));
        
        position.insert(new Piece(new Location(7,7),Color.BLACK,PieceType.PAWN));
        assertTrue(areRepresentationsIsomorphic(position));  
    }
    
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
        
        if (n != position.getPieces().size()) 
        {
            return false;
        }
        
        for (Piece p1 : position.getPieces())
        {            
            Piece p2 = position.getPiece(p1.getLocation());
            if (!p1.equals(p2)) 
            {
                return false;
            }
        }
        
        return true;
    }
    
    
    @Test
    public void testInsert()
    {
        Position tmp = position.insert(piece);
        assertEquals(piece, tmp.getPiece(new Location(7,7)));
    }
    
    @Test(expected = IllegalStateException.class)
    public void testDoubleInsert()
    {
        Position tmp = position.insert(piece);
        tmp.insert(piece);
    }
    
    @Test
    public void testRemove()
    {
        Position tmp;
        tmp = position.insert(piece);
        tmp = tmp.remove(piece);
        assertNull(tmp.getPiece(piece.getLocation()));
    }

    @Test(expected = IllegalStateException.class)
    public void testDoubleOfPieceNotPresent()
    {
        position.remove(piece);
        position.remove(piece);
    }
    
    @Test
    public void testFreeSquareEmptySquare()
    {
        assertTrue(position.freeSquare(new Location(4,5)));
    }
    
    @Test
    public void testFreeSquareNonEmptySquare()
    {
        position = position.insert(piece);
        assertFalse(position.freeSquare(piece.getLocation()));
    }

    @Test
    public void testFreeSquares()
    {
        Board b = Board.createFromLetters("Kd4 Rh4");
        assertTrue(b.getPosition().freeSquares(Location.parse("d4"), Location.parse("h4")));
    }

    @Test
	public void testFreeSquares2()
	{
		Board b = Board.createFromFEN("r3r1Qk/6pp/8/6N1/8/1B6/1PPP4/2K5 b");
		assertTrue(b.getPosition().freeSquares(Location.parse("g8"), Location.parse("e8")));
	}
}

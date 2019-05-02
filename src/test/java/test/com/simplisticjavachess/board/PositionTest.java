package com.simplisticjavachess.board;

import com.simplisticjavachess.piece.Color;
import com.simplisticjavachess.piece.Piece;
import com.simplisticjavachess.piece.PieceType;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Ignore;
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
        
        position.doCommand(new InsertCommand(new Piece(new Location(2,3),Color.BLACK,PieceType.KING)));
        
        assertTrue(areRepresentationsIsomorphic(position));
        
        position.doCommand(new InsertCommand(new Piece(new Location(0,0),Color.WHITE,PieceType.KING)));
        assertTrue(areRepresentationsIsomorphic(position));
        
        position.doCommand(new InsertCommand(new Piece(new Location(7,7),Color.BLACK,PieceType.PAWN)));
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
        position.doCommand(new InsertCommand(piece));
        assertEquals(piece, position.getPiece(new Location(7,7)));
    }
    
    @Test(expected = IllegalStateException.class)
    public void testDoubleInsert()
    {
        position.doCommand(new InsertCommand(piece));
        position.doCommand(new InsertCommand(piece));
    }
    
    @Test
    public void testRemove()
    {
        position.doCommand(new InsertCommand(piece));
        position.doCommand(new RemoveCommand(piece));
        assertNull(position.getPiece(piece.getLocation()));
    }
    
    @Ignore
    @Test(expected = IllegalStateException.class)
    public void testDoubleOfPieceNotPresent()
    {
        position.doCommand(new RemoveCommand(piece));
        position.doCommand(new RemoveCommand(piece));
    }
    
    @Test
    public void testFreeSquareEmptySquare()
    {
        assertTrue(position.freeSquare(new Location(4,5)));
    }
    
    @Test
    public void testFreeSquareNonEmptySquare()
    {
        position.doCommand(new InsertCommand(piece));
        assertFalse(position.freeSquare(piece.getLocation()));
    }
}

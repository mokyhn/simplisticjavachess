package com.simplisticjavachess.piece;

import com.simplisticjavachess.board.Location;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Morten Kühnrich
 */
public class PieceTest
{
    Piece p12 = new Piece(new Location(1, 2), Color.BLACK, PieceType.KNIGHT);
    
    @Test
    public void testEquals0()
    {
        assertTrue(p12.equals(p12));
    }

    @Test
    public void testEquals1()
    { 
        Piece p2 = new Piece(new Location(1, 2), Color.BLACK, PieceType.KNIGHT);
        assertTrue(p12.equals(p2));
    }

    @Test
    public void testNotEquals1()
    {
        Piece p2 = new Piece(new Location(1, 2), Color.WHITE, PieceType.KNIGHT);
        assertFalse(p12.equals(p2));
    }

    @Test
    public void testNotEquals2()
    {
        Piece p2 = new Piece(new Location(1, 2), Color.BLACK, PieceType.KING);
        assertFalse(p12.equals(p2));
    }
   
    @Test
    public void testNotEquals3()
    {
        Piece p2 = new Piece(new Location(1, 4), Color.BLACK, PieceType.KNIGHT);
        assertFalse(p12.equals(p2));
    }
   
    @Test
    public void testNotEquals4()
    {
        Piece p2 = new Piece(new Location(4, 2), Color.BLACK, PieceType.KNIGHT);
        assertFalse(p12.equals(p2));
    }
   
    @Test
    public void testHash1()
    {
        Piece p = new Piece(new Location(1, 1), Color.BLACK, PieceType.KNIGHT);
        assertNotEquals(p12.hashCode(), p.hashCode());
    }
    
    @Test
    public void testHash2()
    {
        Piece p = new Piece(new Location(1, 2), Color.WHITE, PieceType.KNIGHT);
        assertNotEquals(p12.hashCode(), p.hashCode());
    }
    
    @Test
    public void testHash3()
    {
        Piece p = new Piece(new Location(1, 2), Color.BLACK, PieceType.KING);
        assertNotEquals(p12.hashCode(), p.hashCode());
    }
}

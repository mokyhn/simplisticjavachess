package com.simplisticchess.piece;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Morten Kühnrich
 */
public class PieceTest
{
    @Test
    public void testEquals0()
    {
        Piece p = new Piece(1, 2, Color.BLACK, PieceType.KNIGHT);
        assertTrue(p.equals(p));
    }

    
    @Test
    public void testEquals1()
    {
        Piece p1 = new Piece(1, 2, Color.BLACK, PieceType.KNIGHT);
        Piece p2 = new Piece(1, 2, Color.BLACK, PieceType.KNIGHT);
        assertTrue(p1.equals(p2));
    }

    @Test
    public void testNotEquals1()
    {
        Piece p1 = new Piece(1, 2, Color.BLACK, PieceType.KNIGHT);
        Piece p2 = new Piece(1, 2, Color.WHITE, PieceType.KNIGHT);
        assertFalse(p1.equals(p2));
    }

    @Test
    public void testNotEquals2()
    {
        Piece p1 = new Piece(1, 2, Color.BLACK, PieceType.KNIGHT);
        Piece p2 = new Piece(1, 2, Color.BLACK, PieceType.KING);
        assertFalse(p1.equals(p2));
    }
   
    @Test
    public void testNotEquals3()
    {
        Piece p1 = new Piece(1, 2, Color.BLACK, PieceType.KNIGHT);
        Piece p2 = new Piece(1, 4, Color.BLACK, PieceType.KNIGHT);
        assertFalse(p1.equals(p2));
    }
   
    @Test
    public void testNotEquals4()
    {
        Piece p1 = new Piece(1, 2, Color.BLACK, PieceType.KNIGHT);
        Piece p2 = new Piece(4, 2, Color.BLACK, PieceType.KNIGHT);
        assertFalse(p1.equals(p2));
    }
   
    
}

package com.simplisticjavachess.piece;

import com.simplisticjavachess.piece.Color;
import com.simplisticjavachess.piece.Piece;
import com.simplisticjavachess.piece.PieceType;
import com.simplisticjavachess.position.Location;
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
        Piece p = new Piece(new Location(1, 2), Color.BLACK, PieceType.KNIGHT);
        assertTrue(p.equals(p));
    }

    
    @Test
    public void testEquals1()
    {
        Piece p1 = new Piece(new Location(1, 2), Color.BLACK, PieceType.KNIGHT);
        Piece p2 = new Piece(new Location(1, 2), Color.BLACK, PieceType.KNIGHT);
        assertTrue(p1.equals(p2));
    }

    @Test
    public void testNotEquals1()
    {
        Piece p1 = new Piece(new Location(1, 2), Color.BLACK, PieceType.KNIGHT);
        Piece p2 = new Piece(new Location(1, 2), Color.WHITE, PieceType.KNIGHT);
        assertFalse(p1.equals(p2));
    }

    @Test
    public void testNotEquals2()
    {
        Piece p1 = new Piece(new Location(1, 2), Color.BLACK, PieceType.KNIGHT);
        Piece p2 = new Piece(new Location(1, 2), Color.BLACK, PieceType.KING);
        assertFalse(p1.equals(p2));
    }
   
    @Test
    public void testNotEquals3()
    {
        Piece p1 = new Piece(new Location(1, 2), Color.BLACK, PieceType.KNIGHT);
        Piece p2 = new Piece(new Location(1, 4), Color.BLACK, PieceType.KNIGHT);
        assertFalse(p1.equals(p2));
    }
   
    @Test
    public void testNotEquals4()
    {
        Piece p1 = new Piece(new Location(1, 2), Color.BLACK, PieceType.KNIGHT);
        Piece p2 = new Piece(new Location(4, 2), Color.BLACK, PieceType.KNIGHT);
        assertFalse(p1.equals(p2));
    }
   
    
}

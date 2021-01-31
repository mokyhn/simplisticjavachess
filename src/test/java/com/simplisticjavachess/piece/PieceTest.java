package com.simplisticjavachess.piece;

import com.simplisticjavachess.position.Location;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 *
 * @author Morten Kühnrich
 */
public class PieceTest
{
    private Piece p12 = new Piece(new Location(1, 2), Color.BLACK, PieceType.KNIGHT);

    @Test
    public void testEquals1()
    { 
        Piece p2 = new Piece(new Location(1, 2), Color.BLACK, PieceType.KNIGHT);
        assertEquals(p12, p2);
    }

    @Test
    public void testNotEquals1()
    {
        Piece p2 = new Piece(new Location(1, 2), Color.WHITE, PieceType.KNIGHT);
        assertNotEquals(p12, p2);
    }

    @Test
    public void testNotEquals2()
    {
        Piece p2 = new Piece(new Location(1, 2), Color.BLACK, PieceType.KING);
        assertNotEquals(p12, p2);
    }
   
    @Test
    public void testNotEquals3()
    {
        Piece p2 = new Piece(new Location(1, 4), Color.BLACK, PieceType.KNIGHT);
        assertNotEquals(p12, p2);
    }
   
    @Test
    public void testNotEquals4()
    {
        Piece p2 = new Piece(new Location(4, 2), Color.BLACK, PieceType.KNIGHT);
        assertNotEquals(p12, p2);
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

    @Test
    public void testAllHashs()
    {
        Set<Integer> hashSet = new HashSet();

        for (Color color : Color.values()) {
            for (PieceType pieceType : PieceType.values()) {
                for (int x = 0; x < 8; x++) {
                    for (int y = 0; y < 8; y++) {
                        hashSet.add(new Piece(new Location(x, y), color, pieceType).getChessHashCode());
                    }
                }
            }
        }

        // All hash's are unique
        assertEquals(2*6*8*8, hashSet.size());
    }
}

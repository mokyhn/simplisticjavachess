package com.simplisticjavachess.board;

import com.simplisticjavachess.piece.Color;
import com.simplisticjavachess.piece.Piece;
import org.junit.Assert;

import static com.simplisticjavachess.piece.Color.WHITE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Morten Kühnrich
 */
public class PositionInferenceTest
{
    @Test
    public void testIsInCheck()
    {
        Position board = PositionIO.algebraic("Bd5 kc4 w");
        assertTrue(PositionInference.isInCheck(board, Color.BLACK));
    }
    
    @Test
    public void testAttacks()
    {
        Position board = PositionIO.algebraic("Bd5 w");
        Piece piece = Piece.parse("Bd5");

        Piece result = PositionInference.attacks(board, Location.parse("e6"), WHITE);
        Assert.assertEquals(piece, result);
        
        result = PositionInference.attacks(board, Location.parse("f7"), WHITE);
        Assert.assertEquals(piece, result);
        
        result = PositionInference.attacks(board, Location.parse("g8"), WHITE);
        Assert.assertEquals(piece, result);
    }

    
    @Test
    public void kingAttackTest()
    {
        Position position = PositionIO.algebraic("Kd5 w");
        Piece piece = Piece.parse("Kd5");

        assertNull(PositionInference.attacks(position, Location.parse("d5"), WHITE));
        assertEquals(piece, PositionInference.attacks(position, Location.parse("d6"), WHITE));
        assertEquals(piece, PositionInference.attacks(position, Location.parse("d4"), WHITE));
        assertEquals(piece, PositionInference.attacks(position, Location.parse("e5"), WHITE));
        assertEquals(piece, PositionInference.attacks(position, Location.parse("e6"), WHITE));
        assertEquals(piece, PositionInference.attacks(position, Location.parse("e4"), WHITE));
        assertEquals(piece, PositionInference.attacks(position, Location.parse("c5"), WHITE));
        assertEquals(piece, PositionInference.attacks(position, Location.parse("c4"), WHITE));
        assertEquals(piece, PositionInference.attacks(position, Location.parse("c6"), WHITE));
        assertNull(PositionInference.attacks(position, Location.parse("c3"), WHITE));
        assertNull(PositionInference.attacks(position, Location.parse("c7"), WHITE));
    }
    

    @Test
    public void knightAttackTest()
    {
        Position position = PositionIO.algebraic("Nd5 w");
        Piece piece = Piece.parse("Nd5");
        
        assertNull(PositionInference.attacks(position, Location.parse("d5"), WHITE));
        assertNull(PositionInference.attacks(position, Location.parse("d6"), WHITE));
        assertNull(PositionInference.attacks(position, Location.parse("d7"), WHITE));

        assertEquals(piece, PositionInference.attacks(position, Location.parse("e3"), WHITE));
        assertEquals(piece, PositionInference.attacks(position, Location.parse("f6"), WHITE));

    }

    @Test
    public void pawnAttackTest()
    {
        Position board = PositionIO.algebraic("Pd4 w");
        Location.parse("d4");
        Piece piece;

        piece = PositionInference.attacks(board,
                Location.parse("e5"), WHITE);
        assertEquals(Piece.parse("Pd4"), piece);
    }

//TODO: Add more tests here
    
}

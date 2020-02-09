package com.simplisticjavachess.board;

import com.simplisticjavachess.piece.Color;
import com.simplisticjavachess.piece.Piece;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
        Board board = new Board();
        board = board.insert(Piece.fromPositionCode("Bd5"));
        board = board.insert(Piece.fromPositionCode("kc4"));
        assertTrue(PositionInference.isInCheck(board.getPosition(), Color.BLACK));
    }
    
    @Test
    public void testAttacks()
    {
        Board board = new Board();
        Piece piece = Piece.fromPositionCode("Bd5");
        board = board.insert(piece);
        Piece result = PositionInference.attacks(board.getPosition(), Location.parse("e6"), Color.WHITE);
        Assert.assertEquals(piece, result);
        
        result = PositionInference.attacks(board.getPosition(), Location.parse("f7"), Color.WHITE);
        Assert.assertEquals(piece, result);
        
        result = PositionInference.attacks(board.getPosition(), Location.parse("g8"), Color.WHITE);
        Assert.assertEquals(piece, result);
    }

    
    @Test
    public void kingAttackTest()
    {
        Position position = new Position();
        Piece piece = Piece.fromPositionCode("Kd5");
        position = position.insert(piece);
        
        assertNull(PositionInference.attacks(position, Location.parse("d5"), Color.WHITE));
        assertEquals(piece, PositionInference.attacks(position, Location.parse("d6"), Color.WHITE));
        assertEquals(piece, PositionInference.attacks(position, Location.parse("d4"), Color.WHITE));
        assertEquals(piece, PositionInference.attacks(position, Location.parse("e5"), Color.WHITE));
        assertEquals(piece, PositionInference.attacks(position, Location.parse("e6"), Color.WHITE));
        assertEquals(piece, PositionInference.attacks(position, Location.parse("e4"), Color.WHITE));
        assertEquals(piece, PositionInference.attacks(position, Location.parse("c5"), Color.WHITE));
        assertEquals(piece, PositionInference.attacks(position, Location.parse("c4"), Color.WHITE));
        assertEquals(piece, PositionInference.attacks(position, Location.parse("c6"), Color.WHITE));
        assertNull(PositionInference.attacks(position, Location.parse("c3"), Color.WHITE));
        assertNull(PositionInference.attacks(position, Location.parse("c7"), Color.WHITE));
    }
    

    @Test
    public void knightAttackTest()
    {
        Position position = new Position();
        Piece piece = Piece.fromPositionCode("Nd5");
        position = position.insert(piece);
        
        assertNull(PositionInference.attacks(position, Location.parse("d5"), Color.WHITE));
        assertNull(PositionInference.attacks(position, Location.parse("d6"), Color.WHITE));
        assertNull(PositionInference.attacks(position, Location.parse("d7"), Color.WHITE));

        assertEquals(piece, PositionInference.attacks(position, Location.parse("e3"), Color.WHITE));
        assertEquals(piece, PositionInference.attacks(position, Location.parse("f6"), Color.WHITE));

    }

    @Test
    public void pawnAttackTest()
    {
        Board board = Board.createFromLetters("Pd4");
        Location.parse("d4");
        Piece piece;

        piece = PositionInference.attacks(board.getPosition(),
                Location.parse("e5"), Color.WHITE);
        assertEquals(Piece.fromPositionCode("Pd4"), piece);
    }

//TODO: Add more tests here
    
}

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
        board.insertPiece(Piece.fromPositionCode("B d5"));
        board.insertPiece(Piece.fromPositionCode("k c4"));
        boolean result = PositionInference.isInCheck(board.getPosition(), Color.BLACK);
        assertTrue(result);
    }
    
    @Test
    public void testAttacks()
    {
        Board board = new Board();
        Piece piece = Piece.fromPositionCode("B d5");
        board.insertPiece(piece);
        Piece result = PositionInference.attacks(board.getPosition(), Location.fromString("e6"), Color.BLACK);
        Assert.assertEquals(piece, result);
        
        result = PositionInference.attacks(board.getPosition(), Location.fromString("f7"), Color.BLACK);
        Assert.assertEquals(piece, result);
        
        result = PositionInference.attacks(board.getPosition(), Location.fromString("g8"), Color.BLACK);
        Assert.assertEquals(piece, result);
    }

    
    @Test
    public void kingAttackTest()
    {
        Position position = new Position();
        Piece piece = Piece.fromPositionCode("K d5");
        position.insertPiece(piece);
        
        assertNull(PositionInference.attacks(position, Location.fromString("d5"), Color.BLACK));
        assertEquals(piece, PositionInference.attacks(position, Location.fromString("d6"), Color.BLACK));
        assertEquals(piece, PositionInference.attacks(position, Location.fromString("d4"), Color.BLACK));
        assertEquals(piece, PositionInference.attacks(position, Location.fromString("e5"), Color.BLACK));
        assertEquals(piece, PositionInference.attacks(position, Location.fromString("e6"), Color.BLACK));
        assertEquals(piece, PositionInference.attacks(position, Location.fromString("e4"), Color.BLACK));
        assertEquals(piece, PositionInference.attacks(position, Location.fromString("c5"), Color.BLACK));
        assertEquals(piece, PositionInference.attacks(position, Location.fromString("c4"), Color.BLACK));
        assertEquals(piece, PositionInference.attacks(position, Location.fromString("c6"), Color.BLACK));
        assertNull(PositionInference.attacks(position, Location.fromString("c3"), Color.BLACK));
        assertNull(PositionInference.attacks(position, Location.fromString("c7"), Color.BLACK));
    }
    //TODO: Add more tests here
    
}

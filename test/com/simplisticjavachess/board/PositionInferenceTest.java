package com.simplisticjavachess.board;

import com.simplisticjavachess.piece.Color;
import com.simplisticjavachess.piece.Piece;
import org.junit.Assert;
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

    //TODO: Add more tests here
    
}

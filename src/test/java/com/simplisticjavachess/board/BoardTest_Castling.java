/**
 *
 * @author Morten Kühnrich
 */
package com.simplisticjavachess.board;

import com.simplisticjavachess.move.Moves;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

public class BoardTest_Castling
{
    private static Board WHITE_IN_MOVE_CASTLING() { return Board.createFromFEN("r3k2r/8/8/8/8/8/8/R3K2R w KQkq"); }
    private static Board WHITE_IN_MOVE_CANNOT_CASTLE() { return Board.createFromFEN("r3k2r/8/8/8/8/8/8/R3K2R w kq"); }
    private static Board AFTER_WHITE_SHORT_CASTLING() {return Board.createFromFEN("r3k2r/8/8/8/8/8/8/R4RK1 b kq"); }
    private static Board AFTER_WHITE_LONG_CASTLING() {return Board.createFromFEN("r3k2r/8/8/8/8/8/8/2KR3R b kq"); }


    private static Board BLACK_IN_MOVE_CASTLING() { return Board.createFromFEN("r3k2r/8/8/8/8/8/8/R3K2R b KQkq"); }
    private static Board BLACK_IN_MOVE_CANNOT_CASTLE() { return Board.createFromFEN("r3k2r/8/8/8/8/8/8/R3K2R b KQ"); }
    private static Board AFTER_BLACK_SHORT_CASTLING() {return Board.createFromFEN("r4rk1/8/8/8/8/8/8/R3K2R w kq"); }
    private static Board AFTER_BLACK_LONG_CASTLING() {return Board.createFromFEN("2kr3r/8/8/8/8/8/8/R3K2R w kq"); }


    @Test
    public void testWhiteShortCastling()
    {
        // Test of doMove
        Board actual = WHITE_IN_MOVE_CASTLING();
        Board expected = AFTER_WHITE_SHORT_CASTLING();
        MoveResult moveResult = actual.doMove(Moves.WHITE_SHORT_CASTLING());
        assertEquals(expected.getPosition(), moveResult.getBoard().getPosition());
    }
    
    @Test
    @Ignore("This functionality that would make this test go through is not implemented")
    public void testWhiteShortCastling_cannotCastle()
    {
        // Test of doMove
        Board actual = WHITE_IN_MOVE_CANNOT_CASTLE();
        MoveResult result = actual.doMove(Moves.WHITE_SHORT_CASTLING());
        assertFalse(result.isMoveLegal());
        assertEquals(actual, WHITE_IN_MOVE_CANNOT_CASTLE() );
    }
    
    @Test
    public void testBlackShortCastling()
    {
        // Test of doMove
        Board actual = BLACK_IN_MOVE_CASTLING();
        Board expected = AFTER_BLACK_SHORT_CASTLING();
        MoveResult moveResult = actual.doMove(Moves.BLACK_SHORT_CASTLING());
        assertEquals(expected.getPosition(), moveResult.getBoard().getPosition());
    }
      
    @Test
    @Ignore("This functionality that would make this test go through is not implemented")
    public void testBlackShortCastling_cannotCastle()
    {
        // Test of doMove
        Board actual = BLACK_IN_MOVE_CANNOT_CASTLE();
        MoveResult result = actual.doMove(Moves.BLACK_SHORT_CASTLING());
        assertFalse(result.isMoveLegal());
        assertEquals(actual, BLACK_IN_MOVE_CANNOT_CASTLE() );
    }

    @Test
    public void testLongShortCastling()
    {
        // Test of doMove
        Board actual = WHITE_IN_MOVE_CASTLING();
        Board expected = AFTER_WHITE_LONG_CASTLING();
        MoveResult moveResult = actual.doMove(Moves.WHITE_LONG_CASTLING());
        assertEquals(expected.getPosition(), moveResult.getBoard().getPosition());
    }
    
    @Test
    @Ignore("This functionality that would make this test go through is not implemented")
    public void testWhiteLongCastling_cannotCastle()
    {
        // Test of doMove
        Board actual = WHITE_IN_MOVE_CANNOT_CASTLE();
        MoveResult result = actual.doMove(Moves.WHITE_LONG_CASTLING());
        assertFalse(result.isMoveLegal());
        assertEquals(actual, WHITE_IN_MOVE_CANNOT_CASTLE() );
    }
    
    @Test
    public void testBlackLongCastling()
    {
        // Test of doMove
        Board actual = BLACK_IN_MOVE_CASTLING();
        Board expected = AFTER_BLACK_LONG_CASTLING();
        MoveResult moveResult = actual.doMove(Moves.BLACK_LONG_CASTLING());
        assertEquals(expected.getPosition(), moveResult.getBoard().getPosition());
    }
      
    @Test
    @Ignore("This functionality that would make this test go through is not implemented")
    public void testBlackLongCastling_cannotCastle()
    {
        // Test of doMove
        Board actual = BLACK_IN_MOVE_CANNOT_CASTLE();
        MoveResult result = actual.doMove(Moves.BLACK_LONG_CASTLING());
        assertFalse(result.isMoveLegal());
        assertEquals(actual, BLACK_IN_MOVE_CANNOT_CASTLE() );
    }
    
}

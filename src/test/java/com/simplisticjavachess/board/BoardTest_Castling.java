/**
 *
 * @author Morten Kühnrich
 */
package com.simplisticjavachess.board;

import com.simplisticjavachess.move.Moves;
import org.junit.Test;
import static org.junit.Assert.*;

public class BoardTest_Castling
{
    private static Position WHITE_IN_MOVE_CASTLING() { return BoardParser.FEN("r3k2r/8/8/8/8/8/8/R3K2R w KQkq - 0 0"); }
    private static Position WHITE_IN_MOVE_CANNOT_CASTLE() { return BoardParser.FEN("r3k2r/8/8/8/8/8/8/R3K2R w kq - 0 0"); }
    private static Position AFTER_WHITE_SHORT_CASTLING() {return BoardParser.FEN("r3k2r/8/8/8/8/8/8/R4RK1 b kq - 0 0"); }
    private static Position AFTER_WHITE_LONG_CASTLING() {return BoardParser.FEN("r3k2r/8/8/8/8/8/8/2KR3R b kq - 0 0"); }


    private static Position BLACK_IN_MOVE_CASTLING() { return BoardParser.FEN("r3k2r/8/8/8/8/8/8/R3K2R b KQkq - 0 0"); }
    private static Position BLACK_IN_MOVE_CANNOT_CASTLE() { return BoardParser.FEN("r3k2r/8/8/8/8/8/8/R3K2R b KQ - 0 0"); }
    private static Position AFTER_BLACK_SHORT_CASTLING() {return BoardParser.FEN("r4rk1/8/8/8/8/8/8/R3K2R w KQ - 0 0"); }
    private static Position AFTER_BLACK_LONG_CASTLING() {return BoardParser.FEN("2kr3r/8/8/8/8/8/8/R3K2R w KQ - 0 0"); }


    @Test
    public void testWhiteShortCastling()
    {
        // Test of doMove
        Position actual = WHITE_IN_MOVE_CASTLING();
        Position expected = AFTER_WHITE_SHORT_CASTLING();
        MoveResult moveResult = Mover.doMove(actual, Moves.WHITE_SHORT_CASTLING());
        assertEquals(expected, moveResult.getPosition());
    }
    
    @Test
    public void testWhiteShortCastling_cannotCastle()
    {
        Position actual = WHITE_IN_MOVE_CANNOT_CASTLE();
        MoveResult result = Mover.doMove(actual, Moves.WHITE_SHORT_CASTLING());
        assertFalse(result.isMoveLegal());
        assertEquals(actual, WHITE_IN_MOVE_CANNOT_CASTLE() );
    }
    
    @Test
    public void testBlackShortCastling()
    {
        Position actual = BLACK_IN_MOVE_CASTLING();
        Position expected = AFTER_BLACK_SHORT_CASTLING();
        MoveResult moveResult = Mover.doMove(actual, Moves.BLACK_SHORT_CASTLING());
        actual = moveResult.getPosition();
        assertEquals(expected, actual);
    }
      
    @Test
    public void testBlackShortCastling_cannotCastle()
    {
        Position actual = BLACK_IN_MOVE_CANNOT_CASTLE();
        MoveResult result = Mover.doMove(actual, Moves.BLACK_SHORT_CASTLING());
        assertFalse(result.isMoveLegal());
        assertEquals(actual, BLACK_IN_MOVE_CANNOT_CASTLE() );
    }

    @Test
    public void testLongShortCastling()
    {
        Position actual = WHITE_IN_MOVE_CASTLING();
        Position expected = AFTER_WHITE_LONG_CASTLING();
        MoveResult moveResult = Mover.doMove(actual, Moves.WHITE_LONG_CASTLING());
        assertEquals(expected, moveResult.getPosition());
    }
    
    @Test
    public void testWhiteLongCastling_cannotCastle()
    {
        Position actual = WHITE_IN_MOVE_CANNOT_CASTLE();
        MoveResult result = Mover.doMove(actual, Moves.WHITE_LONG_CASTLING());
        assertFalse(result.isMoveLegal());
        assertEquals(actual, WHITE_IN_MOVE_CANNOT_CASTLE() );
    }
    
    @Test
    public void testBlackLongCastling()
    {
        Position actual = BLACK_IN_MOVE_CASTLING();
        Position expected = AFTER_BLACK_LONG_CASTLING();
        MoveResult moveResult = Mover.doMove(actual, Moves.BLACK_LONG_CASTLING());
        assertEquals(expected, moveResult.getPosition());
    }
      
    @Test
    public void testBlackLongCastling_cannotCastle()
    {
        Position actual = BLACK_IN_MOVE_CANNOT_CASTLE();
        MoveResult result = Mover.doMove(actual, Moves.BLACK_LONG_CASTLING());
        assertFalse(result.isMoveLegal());
        assertEquals(actual, BLACK_IN_MOVE_CANNOT_CASTLE() );
    }
    
}

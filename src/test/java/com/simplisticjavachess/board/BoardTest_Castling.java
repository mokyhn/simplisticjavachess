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
    private static Board WHITE_IN_MOVE_CASTLING() { return BoardParser.FEN("r3k2r/8/8/8/8/8/8/R3K2R w KQkq"); }
    private static Board WHITE_IN_MOVE_CANNOT_CASTLE() { return BoardParser.FEN("r3k2r/8/8/8/8/8/8/R3K2R w kq"); }
    private static Board AFTER_WHITE_SHORT_CASTLING() {return BoardParser.FEN("r3k2r/8/8/8/8/8/8/R4RK1 b kq"); }
    private static Board AFTER_WHITE_LONG_CASTLING() {return BoardParser.FEN("r3k2r/8/8/8/8/8/8/2KR3R b kq"); }


    private static Board BLACK_IN_MOVE_CASTLING() { return BoardParser.FEN("r3k2r/8/8/8/8/8/8/R3K2R b KQkq"); }
    private static Board BLACK_IN_MOVE_CANNOT_CASTLE() { return BoardParser.FEN("r3k2r/8/8/8/8/8/8/R3K2R b KQ"); }
    private static Board AFTER_BLACK_SHORT_CASTLING() {return BoardParser.FEN("r4rk1/8/8/8/8/8/8/R3K2R w KQ"); }
    private static Board AFTER_BLACK_LONG_CASTLING() {return BoardParser.FEN("2kr3r/8/8/8/8/8/8/R3K2R w KQ"); }


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
    public void testWhiteShortCastling_cannotCastle()
    {
        Board actual = WHITE_IN_MOVE_CANNOT_CASTLE();
        System.out.println(actual.asASCII());
        MoveResult result = actual.doMove(Moves.WHITE_SHORT_CASTLING());
        assertFalse(result.isMoveLegal());
        assertEquals(actual, WHITE_IN_MOVE_CANNOT_CASTLE() );
    }
    
    @Test
    public void testBlackShortCastling()
    {
        Board actual = BLACK_IN_MOVE_CASTLING();
        Board expected = AFTER_BLACK_SHORT_CASTLING();
        MoveResult moveResult = actual.doMove(Moves.BLACK_SHORT_CASTLING());
        actual = moveResult.getBoard();
        assertEquals(expected.getPosition(), actual.getPosition());
    }
      
    @Test
    public void testBlackShortCastling_cannotCastle()
    {
        Board actual = BLACK_IN_MOVE_CANNOT_CASTLE();
        MoveResult result = actual.doMove(Moves.BLACK_SHORT_CASTLING());
        assertFalse(result.isMoveLegal());
        assertEquals(actual, BLACK_IN_MOVE_CANNOT_CASTLE() );
    }

    @Test
    public void testLongShortCastling()
    {
        Board actual = WHITE_IN_MOVE_CASTLING();
        Board expected = AFTER_WHITE_LONG_CASTLING();
        MoveResult moveResult = actual.doMove(Moves.WHITE_LONG_CASTLING());
        assertEquals(expected.getPosition(), moveResult.getBoard().getPosition());
    }
    
    @Test
    public void testWhiteLongCastling_cannotCastle()
    {
        Board actual = WHITE_IN_MOVE_CANNOT_CASTLE();
        System.out.println(actual.asASCII());
        MoveResult result = actual.doMove(Moves.WHITE_LONG_CASTLING());
        assertFalse(result.isMoveLegal());
        assertEquals(actual, WHITE_IN_MOVE_CANNOT_CASTLE() );
    }
    
    @Test
    public void testBlackLongCastling()
    {
        Board actual = BLACK_IN_MOVE_CASTLING();
        Board expected = AFTER_BLACK_LONG_CASTLING();
        MoveResult moveResult = actual.doMove(Moves.BLACK_LONG_CASTLING());
        assertEquals(expected.getPosition(), moveResult.getBoard().getPosition());
    }
      
    @Test
    public void testBlackLongCastling_cannotCastle()
    {
        Board actual = BLACK_IN_MOVE_CANNOT_CASTLE();
        MoveResult result = actual.doMove(Moves.BLACK_LONG_CASTLING());
        assertFalse(result.isMoveLegal());
        assertEquals(actual, BLACK_IN_MOVE_CANNOT_CASTLE() );
    }
    
}

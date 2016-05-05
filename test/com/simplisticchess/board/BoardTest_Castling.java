/**
 *
 * @author Morten Kühnrich
 */
package com.simplisticchess.board;

import com.simplisticchess.move.Moves;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

public class BoardTest_Castling
{  
    @Test
    public void testWhiteShortCastling()
    {
        // Test of doMove
        Board actual = new Board(Boards.WHITE_IN_MOVE_CASTLING());
        Board expected = Boards.AFTER_WHITE_SHORT_CASTLING();        
        actual.doMove(Moves.WHITE_SHORT_CASTLING());
        assertEquals(expected, actual);

        // Test of undo
        expected = Boards.WHITE_IN_MOVE_CASTLING();
        actual.undo();
        assertEquals(expected, actual);                
    }
    
    @Test
    @Ignore("This functionality that would make this test go through is not implemented")
    public void testWhiteShortCastling_cannotCastle()
    {
        // Test of doMove
        Board actual = new Board(Boards.WHITE_IN_MOVE_CANNOT_CASTLE());
        boolean result = actual.doMove(Moves.WHITE_SHORT_CASTLING());
        assertFalse(result);
        assertEquals(actual, Boards.WHITE_IN_MOVE_CANNOT_CASTLE() );
    }
    
    @Test
    public void testBlackShortCastling()
    {
        // Test of doMove
        Board actual = new Board(Boards.BLACK_IN_MOVE_CASTLING());
        Board expected = Boards.AFTER_BLACK_SHORT_CASTLING();        
        actual.doMove(Moves.BLACK_SHORT_CASTLING());
        assertEquals(expected, actual);

        // Test of undo
        expected = Boards.BLACK_IN_MOVE_CASTLING();
        actual.undo();
        assertEquals(expected, actual);                
    }
      
    @Test
    @Ignore("This functionality that would make this test go through is not implemented")
    public void testBlackShortCastling_cannotCastle()
    {
        // Test of doMove
        Board actual = new Board(Boards.BLACK_IN_MOVE_CANNOT_CASTLE());
        boolean result = actual.doMove(Moves.BLACK_SHORT_CASTLING());
        assertFalse(result);
        assertEquals(actual, Boards.BLACK_IN_MOVE_CANNOT_CASTLE() );
    }

        @Test
    public void testLongShortCastling()
    {
        // Test of doMove
        Board actual = new Board(Boards.WHITE_IN_MOVE_CASTLING());
        Board expected = Boards.AFTER_WHITE_LONG_CASTLING();        
        actual.doMove(Moves.WHITE_LONG_CASTLING());
        assertEquals(expected, actual);

        // Test of undo
        expected = Boards.WHITE_IN_MOVE_CASTLING();
        actual.undo();
        assertEquals(expected, actual);                
    }
    
    @Test
    @Ignore("This functionality that would make this test go through is not implemented")
    public void testWhiteLongCastling_cannotCastle()
    {
        // Test of doMove
        Board actual = new Board(Boards.WHITE_IN_MOVE_CANNOT_CASTLE());
        boolean result = actual.doMove(Moves.WHITE_LONG_CASTLING());
        assertFalse(result);
        assertEquals(actual, Boards.WHITE_IN_MOVE_CANNOT_CASTLE() );
    }
    
    @Test
    public void testBlackLongCastling()
    {
        // Test of doMove
        Board actual = new Board(Boards.BLACK_IN_MOVE_CASTLING());
        Board expected = Boards.AFTER_BLACK_LONG_CASTLING();        
        actual.doMove(Moves.BLACK_LONG_CASTLING());
        assertEquals(expected, actual);

        // Test of undo
        expected = Boards.BLACK_IN_MOVE_CASTLING();
        actual.undo();
        assertEquals(expected, actual);                
    }
      
    @Test
    @Ignore("This functionality that would make this test go through is not implemented")
    public void testBlackLongCastling_cannotCastle()
    {
        // Test of doMove
        Board actual = new Board(Boards.BLACK_IN_MOVE_CANNOT_CASTLE());
        boolean result = actual.doMove(Moves.BLACK_LONG_CASTLING());
        assertFalse(result);
        assertEquals(actual, Boards.BLACK_IN_MOVE_CANNOT_CASTLE() );
    }
    
}

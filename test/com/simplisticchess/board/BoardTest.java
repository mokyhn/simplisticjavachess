/**
 *
 * @author Morten Kühnrich
 */
package com.simplisticchess.board;

import com.simplisticchess.move.Moves;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

public class BoardTest
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
    
    //TODO: Add remaining tests for short/long /black....
    
    @Test
    @Ignore("This functionality that would make this test go through is not implemented")
    public void testWhiteShortCastling_cannotCastle()
    {
        // Test of doMove
        Board actual = new Board(Boards.WHITE_IN_MOVE_CANNOT_CASTLE());
        System.out.println(actual.getASCIIBoard());
        boolean result = actual.doMove(Moves.WHITE_SHORT_CASTLING());
        assertFalse(result);
        assertEquals(actual, Boards.WHITE_IN_MOVE_CANNOT_CASTLE() );
    }
}

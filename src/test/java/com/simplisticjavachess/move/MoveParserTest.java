package com.simplisticjavachess.move;

import com.simplisticjavachess.board.Board;
import com.simplisticjavachess.piece.Color;
import com.simplisticjavachess.board.Location;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Morten Kühnrich
 */
public class MoveParserTest
{
    
    private Board board;
    
    public MoveParserTest()
    {
        board = Board.createFromFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
    }

    /**
     * Test of parseMove method, of class MoveParser.
     */
    @Test
    public void testParseMove()
    {
        Move move = MoveParser.parseMove(board, "d2d4");  
        assertEquals(new Move(new Location("d2"), new Location("d4"), MoveType.NORMALMOVE, null, Color.WHITE), move);
    }
    
    //TODO: Add more tests here for the move parser.
}

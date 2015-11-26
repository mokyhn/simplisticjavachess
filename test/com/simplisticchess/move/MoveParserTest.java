package com.simplisticchess.move;

import com.simplisticchess.board.Board;
import com.simplisticchess.piece.Color;
import com.simplisticchess.piece.PieceType;
import com.simplisticchess.position.Location;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Morten Kühnrich
 */
public class MoveParserTest
{
    
    Board board;
    
    public MoveParserTest()
    {
        board = new  Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
    }

    /**
     * Test of parseMove method, of class MoveParser.
     */
    @Test
    public void testParseMove() throws Exception
    {
        Move move = MoveParser.parseMove(board, "d2d4");  
        assertEquals(new Move(new Location("d2"), new Location("d4"), MoveType.NORMALMOVE, null, Color.WHITE), move);
    }
    
    //TODO: Add more tests here for the move parser.
}

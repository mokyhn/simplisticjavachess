package com.simplisticjavachess.move;

import com.simplisticjavachess.move.MoveType;
import com.simplisticjavachess.move.MoveParser;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.board.Board;
import com.simplisticjavachess.piece.Color;
import com.simplisticjavachess.piece.PieceType;
import com.simplisticjavachess.position.Location;
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

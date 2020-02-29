package com.simplisticjavachess.move;

import com.simplisticjavachess.board.Board;
import com.simplisticjavachess.board.BoardParser;
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
        board = BoardParser.FEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
    }

    /**
     * Test of parseMove method, of class MoveParser.
     */
    @Test
    public void testParseMove()
    {
        Move move = MoveParser.parse(board, "d2d4");
        assertEquals(new Move(Location.parse("d2"), Location.parse("d4"), MoveType.NORMALMOVE, null, Color.WHITE), move);
    }
    
    //TODO: Add more tests here for the move parser.
}

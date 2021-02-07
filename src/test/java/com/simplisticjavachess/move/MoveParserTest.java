package com.simplisticjavachess.move;

import com.simplisticjavachess.UnitTest;
import com.simplisticjavachess.position.PositionIO;
import com.simplisticjavachess.position.Position;
import com.simplisticjavachess.piece.Color;
import com.simplisticjavachess.position.Location;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Morten Kühnrich
 */
@UnitTest
public class MoveParserTest {

    private Position position;

    public MoveParserTest() {
        position = PositionIO.FEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
    }

    /**
     * Test of parseMove method, of class MoveParser.
     */
    @Test
    public void testParseMove() {
        Move move = MoveParser.parse(position, "d2d4");
        Assert.assertEquals(new Move(Location.parse("d2"), Location.parse("d4"), MoveType.NORMALMOVE, null,
                Color.WHITE), move);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalMove() {
        Position problem = PositionIO.FEN("r1bk2nr/pppP2pp/4p3/2b3N1/2PQ1P2/P1N1B1P1/1P2BP1P/R4RK1 w Q - 1 7");
        MoveParser.parse(problem, "g5e3");
    }

    //TODO: Add more tests here for the move parser.
}

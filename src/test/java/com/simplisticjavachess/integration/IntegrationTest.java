package com.simplisticjavachess.integration;

/**
 * @author Morten Kühnrich
 */

import com.simplisticjavachess.board.BoardParser;
import com.simplisticjavachess.board.IllegalMoveException;
import com.simplisticjavachess.board.Mover;
import com.simplisticjavachess.board.Position;
import org.junit.Test;

import static com.simplisticjavachess.integration.TestSearch.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;

public class IntegrationTest
{

    @Test
    public void fiftyMoveRuleDraw() throws IllegalMoveException {
        Position position = BoardParser.FEN("k7/8/8/8/8/8/NB6/K7 w - - 49 49");
        assertFalse(position.isDrawBy50Move());
        Position drawPosition = Mover.doMove(position, "a1b1");
        assertTrue(drawPosition.isDrawBy50Move());
    }

    @Test
    public void staleMateTest()
    {
        //Stalemate
        assertMove("8/7p/7P/7P/7P/7P/4k2P/7K b - - 0 0", "", "e2f1 e2f2", 4);
    }

    
    @Test
    public void avoidingMateMinMax()
    {
        assertMove("k7/P1p5/KP6/8/8/8/1P6/8 b - - 0 0", "", "c7b6", 4);
    }

    @Ignore(value = "slow")
    @Test
    public void bratkoKopecTest()
    {
       assertMove("1k1r4/pp1b1R2/3q2pp/4p3/2B5/4Q3/PPP2B2/2K5 b", "", "d6d1", 7); //BK.01
    }
    
    @Ignore(value = "slow")
    @Test
    public void endGameTacticsTest()
    {
        assertMove("7k/ppp5/8/PPP5/8/8/8/7K w", "", "b5b6", 10);
    }
  
}

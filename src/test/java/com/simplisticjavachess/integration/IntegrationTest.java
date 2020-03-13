package com.simplisticjavachess.integration;

/**
 * @author Morten Kühnrich
 */

import org.junit.Test;

import static com.simplisticjavachess.integration.TestSearch.*;
import org.junit.Ignore;

public class IntegrationTest
{
    @Ignore
    @Test
    public void testThreefoldRepetitionDraw()
    {
        assertMove("4k3/pppppppp/8/8/8/8/PPPPPPPP/4K3 w KQkq", "e1d1 e8d8 d1e1 d8e8 e1d1 e8d8 d1e1 d8e8 e1d1 e8d8 d1e1 d8e8 ", "", 0);
    }

    @Test
    public void fiftyMoveRuleDraw()
    {
        assertMove("k7/nn6/8/8/8/8/N7/K7 w - - 0 0", "", "a2b4 a2c3 a2c1 a1b1 a1b2", 3);
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

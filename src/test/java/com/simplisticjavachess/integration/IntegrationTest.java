package com.simplisticjavachess.integration;

/**
 * @author Morten Kühnrich
 */

import org.junit.Test;

import static com.simplisticjavachess.integration.TestSearch.*;
import org.junit.Ignore;

public class IntegrationTest
{

    @Test
    public void testPawnCaptureMinMax1()
    {
        String fen = "4k3/8/8/8/8/7p/5PPP/4K3 b";
        String expected = "h3g2";
        assertMove(expected, fen, "", 1);
        assertMove(expected, fen, "",  2);
        assertMove(expected, fen, "",  3);

    }

    @Test
    public void testPawnCaptureMinMax6()
    {
        assertMove("h6h7", "4k3/8/7P/8/p7/8/P7/7K w - -", "", 6);
    }
    
    @Ignore
    @Test
    public void testThreefoldRepetitionDraw()
    {
        assertMove("", "4k3/pppppppp/8/8/8/8/PPPPPPPP/4K3 w KQkq", "e1d1 e8d8 d1e1 d8e8 e1d1 e8d8 d1e1 d8e8 e1d1 e8d8 d1e1 d8e8 ",  0);
    }

    @Test
    public void fiftyMoveRuleDraw()
    {
        assertMove("a2b4 a2c3 a2c1 a1b1 a1b2", "k7/nn6/8/8/8/8/N7/K7 w -", "",  3);
    }

    @Test
    public void kingTakes_MinMax()
    {
        assertMove("h6h7", "4k3/7p/7K/8/8/8/8/8 w", "",  5);
    }

    @Test
    public void castlingTest()
    {
       // It is not allowed to castle away from a check...
        //assertTrue(testSearch("4k3/8/8/8/8/PP1PP3/2PPP2q/R3K3 w Q", "",  3,    "e1c1")); // o-o-o
        assertMove("e1c1", "4k3/8/8/8/8/PP1PP3/2PPPP1q/R3K3 w Q", "",  3); // o-o-o: TODO: Add in check rule in movegen of castling...
    }

    @Test
    public void staleMateTest()
    {
        //Stalemate
        assertMove("e2f1 e2f2", "8/7p/7P/7P/7P/7P/4k2P/7K b", "",  4);
    }

    
    @Ignore(value="Test is correct, but engine fails. Re-enable later")
    @Test
    public void avoidingMateMinMax()
    {
        assertMove("c7b6", "k7/P1p5/KP6/8/8/8/1P6/8 b - -", "",  4);
    }

    @Ignore(value = "slow")
    @Test
    public void bratkoKopecTest()
    {
       assertMove("d6d1", "1k1r4/pp1b1R2/3q2pp/4p3/2B5/4Q3/PPP2B2/2K5 b", "",  7); //BK.01
    }
    
    @Ignore(value = "slow")
    @Test
    public void endGameTacticsTest()
    {
        assertMove("b5b6", "7k/ppp5/8/PPP5/8/8/8/7K w", "",  10);
    }
  
}

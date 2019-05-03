package com.simplisticjavachess.integration;

/**
 * @author Morten Kühnrich
 */

import org.junit.Test;
import static org.junit.Assert.*;

import static com.simplisticjavachess.integration.TestSearch.*;
import org.junit.Ignore;

public class IntegrationTest
{




    @Test
    public void testPawnPromotions() throws Exception
    {
        assertTrue(search("4k3/P7/4K3/8/8/8/8/8 w", "",  1, "a7a8Q"));
        assertTrue(search("4k3/P7/4K3/8/8/8/8/8 w", "",  2, "a7a8Q"));
        assertTrue(search("4k3/P7/4K3/8/8/8/8/8 w", "",  3, "a7a8Q"));
    }

    @Test
    public void testPawnCaptureMinMax1() throws Exception
    {
        assertTrue(search("4k3/ppppppp1/8/8/8/7p/PPPPPPPP/4K3 w", "",  1, "g2h3"));        
    }

    @Test
    public void testPawnCaptureMinMax2() throws Exception
    {
        assertTrue(search("4k3/ppppppp1/8/8/8/7p/PPPPPPPP/4K3 w", "",  2, "g2h3"));
    }    

    @Ignore(value="Test is correct, but engine fails. Re-enable later")
    @Test
    public void testPawnCaptureMinMax3() throws Exception
    {
        assertTrue(search("4k3/ppppppp1/8/8/8/7p/PPPPPPPP/4K3 w", "", 3, "g2h3"));
    }    

    @Test
    public void testPawnCaptureMinMax6() throws Exception
    {
        assertTrue(search("4k3/8/7P/8/p7/8/P7/7K w - -", "", 6, "h6h7"));
    }
    
    @Ignore
    @Test
    public void testThreefoldRepetitionDraw() throws Exception
    {
        assertTrue(search("4k3/pppppppp/8/8/8/8/PPPPPPPP/4K3 w KQkq", "e1d1 e8d8 d1e1 d8e8 e1d1 e8d8 d1e1 d8e8 e1d1 e8d8 d1e1 d8e8 ",  0, ""));
    }

    @Test
    public void fiftyMoveRuleDraw() throws Exception
    {
        assertTrue(search("k7/nn6/8/8/8/8/N7/K7 w -", "",  3, "a2b4 a2c3 a2c1 a1b1 a1b2"));
    }

    @Test
    public void kingTakes_MinMax() throws Exception
    {
        assertTrue(search("4k3/7p/7K/8/8/8/8/8 w", "",  5, "h6h7"));
    }

    @Test
    public void castlingTest() throws Exception
    {
       // It is not allowed to castle away from a check...
        //assertTrue(testSearch("4k3/8/8/8/8/PP1PP3/2PPP2q/R3K3 w Q", "",  3,    "e1c1")); // o-o-o
        assertTrue(search("4k3/8/8/8/8/PP1PP3/2PPPP1q/R3K3 w Q", "",  3, "e1c1")); // o-o-o: TODO: Add in check rule in movegen of castling...
    }

    @Test
    public void staleMateTest() throws Exception
    {
        //Stalemate
        assertTrue(search("8/7p/7P/7P/7P/7P/4k2P/7K b", "",  4, "e2f1 e2f2"));
    }

    
    @Ignore(value="Test is correct, but engine fails. Re-enable later")
    @Test
    public void avoidingMateMinMax() throws Exception
    {
        assertTrue(search("k7/P1p5/KP6/8/8/8/1P6/8 b - -", "",  4, "c7b6"));
    }

    @Ignore(value = "slow")
    @Test
    public void bratkoKopecTest() throws Exception {
       assertTrue(search("1k1r4/pp1b1R2/3q2pp/4p3/2B5/4Q3/PPP2B2/2K5 b", "",  7, "d6d1")); //BK.01
    }
    
    @Ignore(value = "slow")
    @Test
    public void endGameTacticsTest()  throws Exception {
        assert(search("7k/ppp5/8/PPP5/8/8/8/7K w", "",  10, "b5b6"));
    }
  
}

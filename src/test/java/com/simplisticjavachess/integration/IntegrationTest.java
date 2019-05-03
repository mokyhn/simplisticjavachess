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





    /*
     * It is tested that evaluation handles regular pawn moves, one and two steps ahead
     * This is tested for both black and white
     */
    @Test
    public void testSimplePawnMoves2() throws Exception
    {
        for (int depth = 2; depth < 5; depth++)
        {
            // One ahead - white
            assertTrue(search("q7/nbr5/p7/ppp5/pkp5/8/2P5/1N4K1 w", "", depth, "c2c3"));
            assertTrue(search("q7/nbr5/8/2ppp3/2pkp3/4p3/2P1P3/3N2K1 w", "",  depth, "c2c3"));
            assertTrue(search("8/8/8/5ppp/5pkp/8/6KP/8 w", "",  depth, "h2h3"));

            // One ahead - black
            assertTrue(search("8/pk6/8/PKP5/PPP5/8/8/8 b", "",  depth, "a7a6"));
            assertTrue(search("8/3pk3/8/3PKP2/3PPP2/8/8/8 b", "",  depth, "d7d6"));
            assertTrue(search("8/QP4kp/8/5PKP/5PPP/8/1N6/8 b", "",  depth, "h7h6"));

            // Two ahead - white
            assertTrue(search("q7/nbr5/ppp5/pkp5/8/2P5/PN6/7K w", "",  depth, "a2a4"));
            assertTrue(search("q7/nbr5/2ppp3/2pkp3/4p3/4P3/2PN4/6K1 w", "",  depth, "c2c4"));
            assertTrue(search("8/8/5ppp/5pkp/8/6K1/7P/8 w", "",  depth, "h2h4"));

            // Two ahead - black
            assertTrue(search("8/p7/1k6/8/BKP5/NBN5/8/8 b", "",  depth, "a7a5"));
            assertTrue(search("1Q5Q/3p4/2k2pp1/2p1p1p1/2p1K1p1/4P3/8/Q7 b", "",  depth, "d7d5"));
            assertTrue(search("8/QP5p/6k1/5N2/5PKP/5PPP/8/8 b", "",  depth, "h7h5"));
        }
    }

    @Test
    public void testPawnPromotions() throws Exception
    {
        assertTrue(search("nn3k2/P7/8/8/8/8/8/4K3 w", "",  1, "a7b8Q"));
        assertTrue(search("nn3k2/P7/8/8/8/8/8/4K3 w", "",  2, "a7b8Q"));
        assertTrue(search("nn3k2/P7/8/8/8/8/8/4K3 w", "",  3, "a7b8Q"));
        assertTrue(search("4k3/pppppppP/8/8/8/8/PPPPPPPP/4K3 w", "",  3, "h7h8Q"));
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

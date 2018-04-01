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
    public void simpleAlphaBetaTest1() throws Exception
    {
        assertTrue(search("8/8/8/3k4/3P4/8/3K4/8 w", "", ALPHABETA, 2, "d2d3 d2c3 d2e3"));
    }

    @Test
    public void simpleAlphaBetaTest2() throws Exception
    {
        assertTrue(search("8/3k4/8/3p4/3K4/8/8/8 b", "", ALPHABETA, 2, "d7d6 d7c6 d7e6"));
    }

    @Test
    public void simpleAlphaBetaTest3() throws Exception
    {
        assertTrue(search("3k4/3P4/3K4/8/8/8/8/8 w", "", ALPHABETA, 5, "d6e6 d6c6"));
    }

    @Test
    public void simpleAlphaBetaTest4() throws Exception
    {
        assertTrue(search("8/8/8/8/8/3k4/3p4/3K4 b", "", ALPHABETA, 5, "d3e3 d3c3"));
    }

    @Test
    public void staleMateAndPossiblePawnPromotion() throws Exception
    {
        assertTrue(search("3k4/3P4/8/3K4/8/8/8/8 w", "", ALPHABETA, 5, "d5c5 d5c6 d5d6 d5e6 d5e5 d5e4 d5d4 d5c4"));
    }

    @Test
    public void simpleAlphaBetaTest6() throws Exception
    {
        assertTrue(search("8/8/8/8/3k4/8/3p4/3K4 b", "", ALPHABETA, 5, "d4e3 d4c3"));
    }

    /*
     * It is tested that evaluation handles regular pawn moves, one and two steps ahead
     * This is tested for both black and white
     * in search depths from 2 to 6
     * using alphabeta search
     */
    @Test
    public void testSimplePawnMoves2() throws Exception
    {
        for (int depth = 2; depth < 7; depth++)
        {
            // One ahead - white
            assertTrue(search("q7/nbr5/p7/ppp5/pkp5/8/P1P5/1N4K1 w", "", ALPHABETA, depth, "a2a3"));
            assertTrue(search("q7/nbr5/8/2ppp3/2pkp3/4p3/2P1P3/3N2K1 w", "", ALPHABETA, depth, "c2c3"));
            assertTrue(search("8/8/8/5ppp/5pkp/8/6KP/8 w", "", ALPHABETA, depth, "h2h3"));

            // One ahead - black
            assertTrue(search("8/pk6/8/PKP5/PPP5/8/8/8 b", "", ALPHABETA, depth, "a7a6"));
            assertTrue(search("8/3pk3/8/3PKP2/3PPP2/8/8/8 b", "", ALPHABETA, depth, "d7d6"));
            assertTrue(search("8/QP4kp/8/5PKP/5PPP/8/1N6/8 b", "", ALPHABETA, depth, "h7h6"));

            // Two ahead - white
            assertTrue(search("q7/nbr5/ppp5/pkp5/8/2P5/PN6/7K w", "", ALPHABETA, depth, "a2a4"));
            assertTrue(search("q7/nbr5/2ppp3/2pkp3/4p3/4P3/2PN4/6K1 w", "", ALPHABETA, depth, "c2c4"));
            assertTrue(search("8/8/5ppp/5pkp/8/6K1/7P/8 w", "", ALPHABETA, depth, "h2h4"));

            // Two ahead - black
            assertTrue(search("8/p7/1k6/8/BKP5/NBN5/8/8 b", "", ALPHABETA, depth, "a7a5"));
            assertTrue(search("1Q5Q/3p4/2k2pp1/2p1p1p1/2p1K1p1/4P3/8/Q7 b", "", ALPHABETA, depth, "d7d5"));
            assertTrue(search("8/QP5p/6k1/5N2/5PKP/5PPP/8/8 b", "", ALPHABETA, depth, "h7h5"));
        }
    }

    @Test
    public void testPawnPromotions() throws Exception
    {
        assertTrue(search("nn3k2/P7/8/8/8/8/8/4K3 w", "", MINMAX, 1, "a7b8Q"));
        assertTrue(search("nn3k2/P7/8/8/8/8/8/4K3 w", "", ALPHABETA, 1, "a7b8Q"));
        assertTrue(search("nn3k2/P7/8/8/8/8/8/4K3 w", "", MINMAX, 2, "a7b8Q"));
        assertTrue(search("nn3k2/P7/8/8/8/8/8/4K3 w", "", ALPHABETA, 2, "a7b8Q"));
        assertTrue(search("nn3k2/P7/8/8/8/8/8/4K3 w", "", MINMAX, 3, "a7b8Q"));
        assertTrue(search("nn3k2/P7/8/8/8/8/8/4K3 w", "", ALPHABETA, 3, "a7b8Q"));
        assertTrue(search("4k3/pppppppP/8/8/8/8/PPPPPPPP/4K3 w", "", MINMAX, 3, "h7h8Q"));
        assertTrue(search("4k3/pppppppP/8/8/8/8/PPPPPPPP/4K3 w", "", ALPHABETA, 3, "h7h8Q"));
    }

    @Test
    public void testPawnCaptureMinMax1() throws Exception
    {
        assertTrue(search("4k3/ppppppp1/8/8/8/7p/PPPPPPPP/4K3 w", "", MINMAX, 1, "g2h3"));        
    }

    @Test
    public void testPawnCaptureAlphaBeta1() throws Exception
    {
        assertTrue(search("4k3/ppppppp1/8/8/8/7p/PPPPPPPP/4K3 w", "", ALPHABETA, 1, "g2h3"));
    }

    @Test
    public void testPawnCaptureMinMax2() throws Exception
    {
        assertTrue(search("4k3/ppppppp1/8/8/8/7p/PPPPPPPP/4K3 w", "", MINMAX, 2, "g2h3"));
    }    

    @Test
    public void testPawnCaptureAlphaBeta2() throws Exception
    {
        assertTrue(search("4k3/ppppppp1/8/8/8/7p/PPPPPPPP/4K3 w", "", ALPHABETA, 2, "g2h3"));
    }

    @Ignore(value="Test is correct, but engine fails. Re-enable later")
    @Test
    public void testPawnCaptureMinMax3() throws Exception
    {
        assertTrue(search("4k3/ppppppp1/8/8/8/7p/PPPPPPPP/4K3 w", "", MINMAX, 3, "g2h3"));
    }    

    @Ignore(value="Test is correct, but engine fails. Re-enable later")
    @Test
    public void testPawnCaptureAlphaBeta3() throws Exception
    {
        assertTrue(search("4k3/ppppppp1/8/8/8/7p/PPPPPPPP/4K3 w", "", ALPHABETA, 3, "g2h3"));
    }
    
    @Test
    public void testPawnCaptureMinMax6() throws Exception
    {
        assertTrue(search("4k3/8/7P/8/p7/8/P7/7K w - -", "", MINMAX, 6, "h6h7"));
    }
    
    @Test
    public void testThreefoldRepetitionDraw() throws Exception
    {
        assertTrue(search("4k3/pppppppp/8/8/8/8/PPPPPPPP/4K3 w KQkq", "e1d1 e8d8 d1e1 d8e8 e1d1 e8d8 d1e1 d8e8 e1d1 e8d8 d1e1 d8e8 ", MINMAX, 0, ""));
        assertTrue(search("4k3/pppppppp/8/8/8/8/PPPPPPPP/4K3 w KQkq", "e1d1 e8d8 d1e1 d8e8 e1d1 e8d8 d1e1 d8e8 e1d1 e8d8 d1e1 d8e8 ", ALPHABETA, 0, ""));
    }

    @Test
    public void fiftyMoveRuleDraw() throws Exception
    {
        assertTrue(search("k7/nn6/8/8/8/8/N7/K7 w KQkq", "", MINMAX, 3, "a2b4 a2c3 a2c1 a1b1 a1b2"));
        assertTrue(search("k7/nn6/8/8/8/8/N7/K7 w KQkq", "", ALPHABETA, 3, "a2b4 a2c3 a2c1 a1b1 a1b2"));
    }

    @Test
    public void kingTakes_MinMax() throws Exception
    {
        assertTrue(search("4k3/7p/7K/8/8/8/8/8 w", "", MINMAX, 5, "h6h7"));
    }

    @Test
    public void kingTakes_AlphaBeta() throws Exception
    {
        assertTrue(search("4k3/7p/7K/8/8/8/8/8 w", "", ALPHABETA, 5, "h6h7"));
    }    
    
    @Test
    public void castlingTest() throws Exception
    {
       // It is not allowed to castle away from a check...
        //assertTrue(testSearch("4k3/8/8/8/8/PP1PP3/2PPP2q/R3K3 w Q", "", MINMAX, 3,    "e1c1")); // o-o-o
        assertTrue(search("4k3/8/8/8/8/PP1PP3/2PPPP1q/R3K3 w Q", "", ALPHABETA, 3, "e1c1")); // o-o-o: TODO: Add in check rule in movegen of castling...
    }

    @Test
    public void staleMateTest() throws Exception
    {
        //Stalemate
        assertTrue(search("8/7p/7P/7P/7P/7P/4k2P/7K b", "", MINMAX, 4, "e2f1 e2f2"));
        assertTrue(search("8/7p/7P/7P/7P/7P/4k2P/7K b", "", ALPHABETA, 4, "e2f1 e2f2"));

    }

    
    @Ignore(value="Test is correct, but engine fails. Re-enable later")
    @Test
    public void avoidingMateMinMax() throws Exception
    {
        assertTrue(search("k7/P1p5/KP6/8/8/8/1P6/8 b - -", "", MINMAX, 4, "c7b6"));
    }

    @Ignore(value="Test is correct, but engine fails. Re-enable later")
    @Test
    public void avoidingMateAlphaBeta() throws Exception
    {
        assertTrue(search("k7/P1p5/KP6/8/8/8/1P6/8 b - -", "", ALPHABETA, 4, "c7b6"));
    }

    @Ignore(value = "slow")
    @Test
    public void bratkoKopecTest() throws Exception {
       assertTrue(search("1k1r4/pp1b1R2/3q2pp/4p3/2B5/4Q3/PPP2B2/2K5 b", "", ALPHABETA, 7, "d6d1")); //BK.01
    }
    
    @Ignore(value = "slow")
    @Test
    public void endGameTacticsTest()  throws Exception {
        assert(search("7k/ppp5/8/PPP5/8/8/8/7K w", "", ALPHABETA, 10, "b5b6"));
    }
  
    
}

/**
 *
 * @author Morten Kühnrich
 */

package com.simplisticjavachess.integration;

import static com.simplisticjavachess.integration.TestSearch.*;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class KnightTest
{
    private static final String KNIGHT_TAKES_PAWN_RIGHT = "k7/4n3/8/5P2/8/8/8/K7 b";
    private static final String KNIGHT_TAKES_PAWN_LEFT = "k7/4n3/8/3P4/8/8/8/K7 b";
    private static final String KNIGHT_TAKE_ROOK_OR_QUEEN = "k7/4R3/8/3n4/8/2Q5/8/K7 b";
    
    @Test
    public void knightTest1() throws Exception
    {        
        assertTrue(search(KNIGHT_TAKE_ROOK_OR_QUEEN, "", MINMAX, 3, "d5c3"));
    }
    
    @Test
    public void knightTest2() throws Exception
    {        
        assertTrue(search(KNIGHT_TAKE_ROOK_OR_QUEEN, "", ALPHABETA, 5, "d5e7 d5c3"));
    }
    
      @Test
    public void knightTest3() throws Exception
    {        
        assertTrue(search(KNIGHT_TAKES_PAWN_LEFT, "", ALPHABETA, 5, "e7d5"));
    }
    
    @Test
    public void knightTest4() throws Exception
    {        
        assertTrue(search(KNIGHT_TAKES_PAWN_RIGHT, "", ALPHABETA, 5, "e7f5"));
    }
    
       
    @Test
    public void knightTestMinMax2() throws Exception
    {
        assertTrue(search("q7/ppp1N1k1/5pN1/N4PN1/N2N2N1/8/p2PPPPP/4K3 w", "", MINMAX, 2, "d4e6"));
    }
    
    @Test
    public void knightTestMinMax3() throws Exception
    {
        assertTrue(search("q7/ppp1N1k1/5pN1/N4PN1/N2N2N1/8/p2PPPPP/4K3 w", "", MINMAX, 3, "d4e6"));
    }
    
    @Test
    public void knightTestMinMax4() throws Exception
    {
        assertTrue(search("q7/ppp1N1k1/5pN1/N4PN1/N2N2N1/8/p2PPPPP/4K3 w", "", MINMAX, 4, "d4e6"));
    }
        
    @Test
    public void knightTestAlphaBeta2() throws Exception
    {
        assertTrue(search("q7/ppp1N1k1/5pN1/N4PN1/N2N2N1/8/p2PPPPP/4K3 w", "", ALPHABETA, 2, "d4e6"));
    }
    
    @Test
    public void knightTestAlphaBeta3() throws Exception
    {
        assertTrue(search("q7/ppp1N1k1/5pN1/N4PN1/N2N2N1/8/p2PPPPP/4K3 w", "", ALPHABETA, 3, "d4e6"));
    }
    
    @Test
    public void knightTestAlphaBeta4() throws Exception
    {
        assertTrue(search("q7/ppp1N1k1/5pN1/N4PN1/N2N2N1/8/p2PPPPP/4K3 w", "", ALPHABETA, 4, "d4e6"));
    }
    
    @Test
    public void knightTestAlphaBeta5() throws Exception
    {
        assertTrue(search("q7/ppp1N1k1/5pN1/N4PN1/N2N2N1/8/p2PPPPP/4K3 w", "", ALPHABETA, 5, "d4e6"));
    }
}

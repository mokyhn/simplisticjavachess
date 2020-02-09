/**
 *
 * @author Morten Kühnrich
 */

package com.simplisticjavachess.integration;

import static com.simplisticjavachess.integration.TestSearch.*;

import org.junit.Ignore;
import org.junit.Test;

public class KnightTest
{
    private static final String KNIGHT_TAKES_PAWN_RIGHT = "k7/4n3/8/5P2/8/8/8/K7 b";
    private static final String KNIGHT_TAKES_PAWN_LEFT = "k7/4n3/8/3P4/8/8/8/K7 b";
    private static final String KNIGHT_TAKE_ROOK_OR_QUEEN = "k7/4R3/8/3n4/8/2Q5/8/K7 b";
    
    @Test
    public void knightTest1()
    {        
        assertMove("d5c3", KNIGHT_TAKE_ROOK_OR_QUEEN, "",  3);
    }
    
    @Test
    public void knightTest2()
    {        
        assertMove("d5e7 d5c3", KNIGHT_TAKE_ROOK_OR_QUEEN, "",  5);
    }
    
      @Test
    public void knightTest3()
    {        
        assertMove("e7d5", KNIGHT_TAKES_PAWN_LEFT, "",  5);
    }
    
    @Test
    public void knightTest4()
    {        
        assertMove("e7f5", KNIGHT_TAKES_PAWN_RIGHT, "",  5);
    }

    @Ignore("Finds a correct move, but not the strongest")
    @Test
    public void knightTestMinMax3()
    {
        assertMove("d4e6", "q7/ppp1N1k1/5pN1/N4PN1/N2N2N1/8/p2PPPPP/4K3 w", "",  3);
    }

    @Ignore("Finds a correct move, but not the strongest")
    @Test
    public void knightTestMinMax4()
    {
        assertMove("d4e6", "q7/ppp1N1k1/5pN1/N4PN1/N2N2N1/8/p2PPPPP/4K3 w", "",  4);
    }
        
  
}

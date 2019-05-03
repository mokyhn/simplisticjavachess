package com.simplisticjavachess.integration;

/**
 * @author Morten Kühnrich
 */
import org.junit.Test;

import static com.simplisticjavachess.integration.TestSearch.*;
import org.junit.Ignore;

public class MateTests
{
 
    private static final String WHITE_READY_TO_MATE_WITH_QUEEN_SIMPLE = "4k3/5R1/5Q2/8/8/8/8/4K3 w";
    private static final String WHITE_READY_TO_MATE_WITH_QUEEN_COMPLEX = "r1b1k3/1p3R2/p2p1Q2/P1pPp1P1/3P4/1P6/4P1P1/RN2KBN1 w Q";

    private static final String MATE_WITH_PAWN_0 = "k7/P7/KP6/8/7p/8/8/8 w";
    private static final String MATE_WITH_PAWN_1 = "k7/P7/KP6/8/7p/8/1P6/8 w";
    private static final String SUFFUCATED_MATE_TEST_2 = "r3r2k/6pp/8/6N1/2Q5/1B6/1PPP4/2K5 w";
  
    @Test
    public void mate_with_pawn_1_test()
    {
        assertMove("b6b7", MATE_WITH_PAWN_0, "",  3);
    }

    @Test
    public void mate_with_pawn_2_test()
    {
        assertMove("b6b7", MATE_WITH_PAWN_0, "",  3);
    }        
    
     @Test
    public void mate_with_pawn_3_test()
    {
        assertMove("b6b7", MATE_WITH_PAWN_1, "",  3);
    }
   
    @Test
    public void mate_with_pawn_4_test()
    {
        assertMove("b6b7", MATE_WITH_PAWN_1, "",  3);
    }
    
    @Test
    public void mate_with_queen_1_test()
    {
        assertMove("f6e7", WHITE_READY_TO_MATE_WITH_QUEEN_COMPLEX, "",  3);
    }
    
    @Ignore
    @Test
    public void mate_with_queen_2_test()
    {
        assertMove("f6e7", WHITE_READY_TO_MATE_WITH_QUEEN_SIMPLE, "",  4);
        
    }
    
    @Ignore
    @Test
    public void mate_with_queen_3_test()
    {
        assertMove("f6e7", WHITE_READY_TO_MATE_WITH_QUEEN_SIMPLE, "",  4);
    }

    @Ignore
    @Test
    public void mate_with_queen_4_test()
    {
        assertMove("f6e7", WHITE_READY_TO_MATE_WITH_QUEEN_COMPLEX, "",  4);
    }

    @Ignore
    @Test
    public void mate_with_queen_5_test()
    {
        assertMove("f6e7", "r1b1k3/1p3R2/p2p1Q2/P1pPp1P1/3P4/1P6/4P1P1/RN2KBN1 w Q", "",  4);
    }

    @Ignore
    @Test
    public void mate_with_queen_6_test()
    {
        assertMove("b7g7", "6k1/1Q6/2p2B2/p1P1p3/P7/1P2P1PN/R2P1P1P/1N2KBR1 w", "",  4);
    }

    @Test
    public void suffucated_mate_3_test()
    {
        assertMove("c4g8", SUFFUCATED_MATE_TEST_2, "",  4);
    }
    
    @Test
    public void suffucated_mate_4_test()
    {
        assertMove("c4g8", SUFFUCATED_MATE_TEST_2, "",  4);
    }
    
}

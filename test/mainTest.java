/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * Have a look at http://www.cs.kent.ac.uk/people/staff/djb/pgn-extract/
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mku
 */
public class mainTest {
    
    public mainTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

  
    /**
     * Test of testSearch method, of class main.
     */
    @Test
    public void testPawnPromotions() throws Exception {
       assertTrue(main.testSearch("nn3k2/P7/8/8/8/8/8/4K3 w - - 0 1",            "", Search.MINMAX,    1, 9-3,   "a7b8Q"));
       assertTrue(main.testSearch("nn3k2/P7/8/8/8/8/8/4K3 w - - 0 1",         "", Search.MINMAX,    1, 9-3,   "a7b8Q"));
       assertTrue(main.testSearch("nn3k2/P7/8/8/8/8/8/4K3 w - - 0 1",         "", Search.ALPHABETA, 1, 9-3,   "a7b8Q"));
       assertTrue(main.testSearch("nn3k2/P7/8/8/8/8/8/4K3 w - - 0 1",         "", Search.MINMAX,    2, 9-3,   "a7b8Q"));
       assertTrue(main.testSearch("nn3k2/P7/8/8/8/8/8/4K3 w - - 0 1",         "", Search.ALPHABETA, 2, 9-3,   "a7b8Q"));
       assertTrue(main.testSearch("nn3k2/P7/8/8/8/8/8/4K3 w - - 0 1",         "", Search.MINMAX,    3, 9,   "a7b8Q"));
       assertTrue(main.testSearch("nn3k2/P7/8/8/8/8/8/4K3 w - - 0 1",         "", Search.ALPHABETA, 3, 9,   "a7b8Q"));
       assertTrue(main.testSearch("4k3/pppppppP/8/8/8/8/PPPPPPPP/4K3 w - - 0 1", "", Search.MINMAX, 3,   Evaluator.BLACK_IS_MATED,  "h7h8Q"));
       assertTrue(main.testSearch("4k3/pppppppP/8/8/8/8/PPPPPPPP/4K3 w - - 0 1", "", Search.ALPHABETA, 3, Evaluator.BLACK_IS_MATED, "h7h8Q"));        
    }
      
    @Test
    public void testPawnCapture() throws Exception {       
       assertTrue(main.testSearch("4k3/ppppppp1/8/8/8/7p/PPPPPPPP/4K3 w KQkq - 0 1", "", Search.MINMAX,    1, 1,   "g2h3"));
       assertTrue(main.testSearch("4k3/ppppppp1/8/8/8/7p/PPPPPPPP/4K3 w KQkq - 0 1", "", Search.ALPHABETA, 1, 1,   "g2h3"));
       assertTrue(main.testSearch("4k3/ppppppp1/8/8/8/7p/PPPPPPPP/4K3 w KQkq - 0 1", "", Search.MINMAX,    2, 1,   "g2h3"));
       assertTrue(main.testSearch("4k3/ppppppp1/8/8/8/7p/PPPPPPPP/4K3 w KQkq - 0 1", "", Search.ALPHABETA, 2, 1,   "g2h3"));
       assertTrue(main.testSearch("4k3/ppppppp1/8/8/8/7p/PPPPPPPP/4K3 w KQkq - 0 1", "", Search.MINMAX,    3, 1,   "g2h3"));
       assertTrue(main.testSearch("4k3/ppppppp1/8/8/8/7p/PPPPPPPP/4K3 w KQkq - 0 1", "", Search.ALPHABETA, 3, 1,   "g2h3"));
       assertTrue(main.testSearch("k7/8/7P/8/8/1p6/P7/7K w - - 0 1",                 "", Search.MINMAX,    5, 9+1, "a2b3"));
    }
    
    @Test
    public void testThreefoldRepetitionDraw() throws Exception {    
       assertTrue(main.testSearch("4k3/pppppppp/8/8/8/8/PPPPPPPP/4K3 w KQkq - 0 1", "e1d1 e8d8 d1e1 d8e8 e1d1 e8d8 d1e1 d8e8 e1d1 e8d8 d1e1 d8e8 ", Search.MINMAX,    0, 0, ""));
       assertTrue(main.testSearch("4k3/pppppppp/8/8/8/8/PPPPPPPP/4K3 w KQkq - 0 1", "e1d1 e8d8 d1e1 d8e8 e1d1 e8d8 d1e1 d8e8 e1d1 e8d8 d1e1 d8e8 ", Search.ALPHABETA, 0, 0, ""));
    }
    
    @Test 
    public void fiftyMoveRuleDraw() throws Exception {
           assertTrue(main.testSearch("k7/nn6/8/8/8/8/N7/K7 w KQkq - 47 1", "", Search.MINMAX,    3, Evaluator.DRAW, "a2b4 a2c3 a2c1 a1b1 a1b2"));
           assertTrue(main.testSearch("k7/nn6/8/8/8/8/N7/K7 w KQkq - 47 1", "", Search.ALPHABETA, 3, Evaluator.DRAW, "a2b4 a2c3 a2c1 a1b1 a1b2"));           
    }
    
    @Test
    public void kingTakes() throws Exception {    
       assertTrue(main.testSearch("4k3/7p/7K/8/8/8/8/8 w - - 0 1", "", Search.MINMAX,    5, 0, "h6h7"));
       assertTrue(main.testSearch("4k3/7p/7K/8/8/8/8/8 w - - 0 1", "", Search.ALPHABETA, 5, 0, "h6h7"));  
    }
    
    @Test
    public void matingTest() throws Exception {
       //Mating with pawn
       assertTrue(main.testSearch("k7/P7/KP6/8/7p/8/8/8 w - - 0 1", "", Search.MINMAX,    3, Evaluator.BLACK_IS_MATED, "b6b7"));
       assertTrue(main.testSearch("k7/P7/KP6/8/7p/8/8/8 w - - 0 1", "", Search.ALPHABETA, 3, Evaluator.BLACK_IS_MATED, "b6b7"));

       //assertTrue(main.testSearch("k7/P7/KP6/8/7p/8/1P6/8 w - - 0 1", "", Search.MINMAX,    3, Evaluator.BLACK_IS_MATED, "b6b7"));
       //assertTrue(main.testSearch("k7/P7/KP6/8/7p/8/1P6/8 w - - 0 1", "", Search.ALPHABETA, 3, Evaluator.BLACK_IS_MATED, "b6b7"));
    }
    
    @Test
    public void staleMateTest() throws Exception {
       //Stalemate
       assertTrue(main.testSearch("8/7p/7P/7P/7P/7P/4k2P/7K b - - 0 1", "", Search.MINMAX,    4, 0, "e2f1 e2f2"));
       assertTrue(main.testSearch("8/7p/7P/7P/7P/7P/4k2P/7K b - - 0 1", "", Search.ALPHABETA, 4, 0, "e2f1 e2f2"));

    }
    
    @Test
    public void avoidingMate() throws Exception {
           //Avoiding mate
       assertTrue(main.testSearch("k7/P1p5/KP6/8/8/8/1P5p/8 b - - 0 1", "", Search.MINMAX,    4, -(9-2), "c7b6"));
       assertTrue(main.testSearch("k7/P1p5/KP6/8/8/8/1P5p/8 b - - 0 1", "", Search.ALPHABETA, 4, -(9-2), "c7b6"));
    }
    
    @Test
    public void knightTest() throws Exception {
     assertTrue(main.testSearch("k7/4R3/8/3n4/8/2Q5/8/K7 b KQkq - 0 1", "", Search.MINMAX,    5, -Evaluator.knightValue, "d5e7 d5c3"));
     assertTrue(main.testSearch("k7/4R3/8/3n4/8/2Q5/8/K7 b KQkq - 0 1", "", Search.ALPHABETA, 5, -Evaluator.knightValue, "d5e7 d5c3"));
     assertTrue(main.testSearch("k7/4n3/8/3P4/8/8/8/K7 b KQkq - 0 1", "", Search.ALPHABETA, 5, -Evaluator.knightValue, "e7d5"));
     assertTrue(main.testSearch("k7/4n3/8/5P2/8/8/8/K7 b KQkq - 0 1", "", Search.ALPHABETA, 5, -Evaluator.knightValue, "e7f5"));
    }
   
        //System.out.println("End game tactics : pawn breakthrough");
       //assert(testSearch("7k/ppp5/8/PPP5/8/8/8/7K w - - 0 1", Search.ALPHABETA, 9, 9-3, "b5b6"));
// A wrong trace 1.g1-f3 b8-c6 2.c2-c3 c6-d4 3.c3xd4 e8-d8 4.b1-c3 d8-c8 5.e2-e4 c8-b8 with alphabeta search sd. 6
}



























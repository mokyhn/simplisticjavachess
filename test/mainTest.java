/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
       assertTrue(main.testSearch("nn3k2/P7/8/8/8/8/8/4K3 w KQkq - 0 1",         "", Search.MINMAX,    1, 9-3,   "a7b8Q"));
       assertTrue(main.testSearch("nn3k2/P7/8/8/8/8/8/4K3 w KQkq - 0 1",         "", Search.MINMAX,    1, 9-3,   "a7b8Q"));
       assertTrue(main.testSearch("nn3k2/P7/8/8/8/8/8/4K3 w KQkq - 0 1",         "", Search.ALPHABETA, 1, 9-3,   "a7b8Q"));
       assertTrue(main.testSearch("nn3k2/P7/8/8/8/8/8/4K3 w KQkq - 0 1",         "", Search.MINMAX,    2, 9-3,   "a7b8Q"));
       assertTrue(main.testSearch("nn3k2/P7/8/8/8/8/8/4K3 w KQkq - 0 1",         "", Search.ALPHABETA, 2, 9-3,   "a7b8Q"));
       assertTrue(main.testSearch("nn3k2/P7/8/8/8/8/8/4K3 w KQkq - 0 1",         "", Search.MINMAX,    3, 9-3,   "a7b8Q"));
       assertTrue(main.testSearch("nn3k2/P7/8/8/8/8/8/4K3 w KQkq - 0 1",         "", Search.ALPHABETA, 3, 9-3,   "a7b8Q"));
       assertTrue(main.testSearch("4k3/pppppppP/8/8/8/8/PPPPPPPP/4K3 w - - 0 1", "", Search.MINMAX, 3, 8+9-7,    "h7h8Q"));
       assertTrue(main.testSearch("4k3/pppppppP/8/8/8/8/PPPPPPPP/4K3 w - - 0 1", "", Search.ALPHABETA, 3, 8+9-7, "h7h8Q"));        
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
    public void kingTakes() throws Exception {    
       assertTrue(main.testSearch("4k3/7p/7K/8/8/8/8/8 w - - 0 1", "", Search.MINMAX,    5, 0, "h6h7"));
       assertTrue(main.testSearch("4k3/7p/7K/8/8/8/8/8 w - - 0 1", "", Search.ALPHABETA, 5, 0, "h6h7"));  
    }
    
    @Test
    public void matingTest() throws Exception {
       //Mating with pawn
       assertTrue(main.testSearch("k7/P7/KP6/8/7p/8/1P6/8 w - - 0 1", "", Search.MINMAX,    3, Evaluator.BLACK_IS_MATED, "b6b7"));
       assertTrue(main.testSearch("k7/P7/KP6/8/7p/8/1P6/8 w - - 0 1", "", Search.ALPHABETA, 3, Evaluator.BLACK_IS_MATED, "b6b7"));
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
}

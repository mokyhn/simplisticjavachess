/**
 * @author Morten Kühnrich
 * @year 2012
 * "Unit test" of the testprogram.
 * In practice this is a blackbox-test, where we look at different
 * chess positions and test whether the search algorithm finds the expected
 * moves and avoids certain other moves.
 */

import java.util.ArrayList;
import java.util.Iterator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import sjc.*;

public class mainTest {
    
    public mainTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    
    private static boolean testSearchAux(Board b, int method, int plyDepth, ArrayList<Move> expectedMoves) throws NoMoveException {
       Search  engine       = new Search();
       
       Move strongestMove;
                    
       engine.dosearch(b, plyDepth, method);
       strongestMove = engine.getStrongestMove();

       if (expectedMoves.isEmpty())       
       System.out.println("Expected moves:       none" + ", actual " + strongestMove);
       else System.out.println("Expected moves:       " + expectedMoves.toString() + ", move: ___" + engine.moveAndStatistics() + "___");           
       
       
       if (strongestMove == null && expectedMoves.isEmpty()) return true;
       
       Iterator<Move> it = expectedMoves.iterator();
       
       while (it.hasNext()) {
        if (it.next().equal(strongestMove)) return true;
       }
       
        return false;    
     }
      
    private static boolean testSearchDoNotPlayThis(String fen, int method, int plyDepth, String moves) throws NoMoveException {
       String[] unExpectedMovesStr = moves.split(" ");
       ArrayList<Move> unExpectedMoves = new ArrayList<Move>();
       Chessio cio                   = new Chessio();
       Board b = new Board(fen);
       Move m;
       Search  engine       = new Search();
       int i;
       Move strongestMove;
                    
       engine.dosearch(b, plyDepth, method);
       strongestMove = engine.getStrongestMove();
       
        for (i = 0; i < unExpectedMovesStr.length; i++) {
         if (unExpectedMovesStr[i] != null) {
            m = cio.parseMove(b, unExpectedMovesStr[i]);
            if (m != null) unExpectedMoves.add(m);
         }
        }

       if (unExpectedMoves.isEmpty())       
       System.out.println("Unexpected moves:       none" + ", actual " + strongestMove);
       else System.out.println("Unxpected moves:       " + unExpectedMoves.toString() + ", move: ___" + engine.moveAndStatistics() + "___");           
       
       
       if (strongestMove == null && unExpectedMoves.isEmpty()) return true;
       
       Iterator<Move> it = unExpectedMoves.iterator();
       
       while (it.hasNext()) {
        if (it.next().equal(strongestMove)) return false;
       }
       
        return true;    
     }
    
    
    public static boolean testSearch(String fen, String moveSequence, int method, int plyDepth, String expectedMoveStrs) throws NoMoveException {
        String[] moveStrings          = moveSequence.split(" ");
        Board b                       = new Board(fen);
        Chessio cio                   = new Chessio();
        String[] expectedMoveStrings  = expectedMoveStrs.split(" ");
        ArrayList<Move> expectedMoves = new ArrayList<Move>();
        Move m = null;
        int i;

        for(i = 0; i < moveStrings.length; i++) {
         if (moveStrings[i] != null)
          m = cio.parseMove(b, moveStrings[i]);
          if (m != null)
          b.performMove(m);
        }
        
        
        for (i = 0; i < expectedMoveStrings.length; i++) {
         if (expectedMoveStrings[i] != null) {
            m = cio.parseMove(b, expectedMoveStrings[i]);
            if (m != null) expectedMoves.add(m);
         }
        }

        
       
        return testSearchAux(b, method, plyDepth, expectedMoves);
    }    
  
    /*
     * It is tested that evaluation handles regular pawn moves, one and two steps ahead
     * This is tested for both black and white
     * in search depths from 2 to 7
     * using alphabeta search
     */
    @Test
    public void testSimplePawnMoves() throws Exception {
       for (int depth = 2; depth < 8; depth++) {
        // One ahead - white
        assertTrue(testSearch("q7/nbr5/p7/ppp5/pkp5/8/P1P5/1N4K1 w - - 0 1", "", Search.ALPHABETA, depth, "a2a3")); 
        assertTrue(testSearch("q7/nbr5/8/2ppp3/2pkp3/4p3/2P1P3/3N2K1 w - - 0 1", "",  Search.ALPHABETA, depth, "c2c3"));
        assertTrue(testSearch("8/8/8/5ppp/5pkp/8/6KP/8 w - - 0 1", "", Search.ALPHABETA, depth, "h2h3")); 
        
        // One ahead - black
        assertTrue(testSearch("8/pk6/8/PKP5/PPP5/8/8/8 b - - 0 1", "", Search.ALPHABETA, depth, "a7a6")); 
        assertTrue(testSearch("8/3pk3/8/3PKP2/3PPP2/8/8/8 b - - 0 1", "", Search.ALPHABETA, depth, "d7d6"));
        assertTrue(testSearch("8/QP4kp/8/5PKP/5PPP/8/1N6/8 b - - 0 1", "", Search.ALPHABETA, depth, "h7h6"));
        
        // Two ahead - white
        assertTrue(testSearch("q7/nbr5/ppp5/pkp5/8/2P5/PN6/7K w - - 0 1", "", Search.ALPHABETA, depth, "a2a4")); 
        assertTrue(testSearch("q7/nbr5/2ppp3/2pkp3/4p3/4P3/2PN4/6K1 w - - 0 1", "", Search.ALPHABETA, depth, "c2c4")); 
        assertTrue(testSearch("8/8/5ppp/5pkp/8/6K1/7P/8 w - - 0 1", "", Search.ALPHABETA, depth, "h2h4")); 
        
        // Two ahead - black
        assertTrue(testSearch("8/p7/1k6/8/BKP5/NBN5/8/8 b - - 0 1", "", Search.ALPHABETA, depth, "a7a5"));
        assertTrue(testSearch("1Q5Q/3p4/2k2pp1/2p1p1p1/2p1K1p1/4P3/8/Q7 b - - 0 1",  "", Search.ALPHABETA, depth, "d7d5"));
        assertTrue(testSearch("8/QP5p/6k1/5N2/5PKP/5PPP/8/8 b - - 0 1", "", Search.ALPHABETA, depth, "h7h5"));
       }
    }
    
    @Test
    public void testPawnPromotions() throws Exception {
       //assertTrue(testSearch("nn3k2/P7/8/8/8/8/8/4K3 w - - 0 1",            "", Search.MINMAX,    1,   "a7b8Q"));
       //assertTrue(testSearch("nn3k2/P7/8/8/8/8/8/4K3 w - - 0 1",         "", Search.MINMAX,    1,   "a7b8Q"));
       assertTrue(testSearch("nn3k2/P7/8/8/8/8/8/4K3 w - - 0 1",         "", Search.ALPHABETA, 1,    "a7b8Q"));
       assertTrue(testSearch("nn3k2/P7/8/8/8/8/8/4K3 w - - 0 1",         "", Search.MINMAX,    2,    "a7b8Q"));
       assertTrue(testSearch("nn3k2/P7/8/8/8/8/8/4K3 w - - 0 1",         "", Search.ALPHABETA, 2,    "a7b8Q"));
       assertTrue(testSearch("nn3k2/P7/8/8/8/8/8/4K3 w - - 0 1",         "", Search.MINMAX,    3,    "a7b8Q"));
       assertTrue(testSearch("nn3k2/P7/8/8/8/8/8/4K3 w - - 0 1",         "", Search.ALPHABETA, 3,    "a7b8Q"));
       assertTrue(testSearch("4k3/pppppppP/8/8/8/8/PPPPPPPP/4K3 w - - 0 1", "", Search.MINMAX, 3,    "h7h8Q"));
       assertTrue(testSearch("4k3/pppppppP/8/8/8/8/PPPPPPPP/4K3 w - - 0 1", "", Search.ALPHABETA, 3, "h7h8Q"));        
    }
      
    @Test
    public void testPawnCapture() throws Exception {       
       assertTrue(testSearch("4k3/ppppppp1/8/8/8/7p/PPPPPPPP/4K3 w - - 0 1", "", Search.MINMAX,    1,    "g2h3"));
       assertTrue(testSearch("4k3/ppppppp1/8/8/8/7p/PPPPPPPP/4K3 w - - 0 1", "", Search.ALPHABETA, 1,    "g2h3"));
       assertTrue(testSearch("4k3/ppppppp1/8/8/8/7p/PPPPPPPP/4K3 w - - 0 1", "", Search.MINMAX,    2,    "g2h3"));
       assertTrue(testSearch("4k3/ppppppp1/8/8/8/7p/PPPPPPPP/4K3 w - - 0 1", "", Search.ALPHABETA, 2,    "g2h3"));
       assertTrue(testSearch("4k3/ppppppp1/8/8/8/7p/PPPPPPPP/4K3 w - - 0 1", "", Search.MINMAX,    3,    "g2h3"));
       assertTrue(testSearch("4k3/ppppppp1/8/8/8/7p/PPPPPPPP/4K3 w - - 0 1", "", Search.ALPHABETA, 3,    "g2h3"));
       assertTrue(testSearch("k7/8/7P/8/8/1p6/P7/7K w - - 0 1",                 "", Search.MINMAX,    5, "a2b3"));
    }
    
    @Test
    public void testThreefoldRepetitionDraw() throws Exception {    
       assertTrue(testSearch("4k3/pppppppp/8/8/8/8/PPPPPPPP/4K3 w KQkq - 0 1", "e1d1 e8d8 d1e1 d8e8 e1d1 e8d8 d1e1 d8e8 e1d1 e8d8 d1e1 d8e8 ", Search.MINMAX,    0, ""));
       assertTrue(testSearch("4k3/pppppppp/8/8/8/8/PPPPPPPP/4K3 w KQkq - 0 1", "e1d1 e8d8 d1e1 d8e8 e1d1 e8d8 d1e1 d8e8 e1d1 e8d8 d1e1 d8e8 ", Search.ALPHABETA, 0, ""));
    }
    
    @Test 
    public void fiftyMoveRuleDraw() throws Exception {
           assertTrue(testSearch("k7/nn6/8/8/8/8/N7/K7 w KQkq - 47 1", "", Search.MINMAX,    3,  "a2b4 a2c3 a2c1 a1b1 a1b2"));
           assertTrue(testSearch("k7/nn6/8/8/8/8/N7/K7 w KQkq - 47 1", "", Search.ALPHABETA, 3,  "a2b4 a2c3 a2c1 a1b1 a1b2"));           
    }
    
    @Test
    public void kingTakes() throws Exception {    
       assertTrue(testSearch("4k3/7p/7K/8/8/8/8/8 w - - 0 1", "", Search.MINMAX,    5, "h6h7"));
       assertTrue(testSearch("4k3/7p/7K/8/8/8/8/8 w - - 0 1", "", Search.ALPHABETA, 5, "h6h7"));  
    }
  
    @Test
    public void castlingTest() throws Exception {
       // It is not allowed to castle away from a check...
       //assertTrue(testSearch("4k3/8/8/8/8/PP1PP3/2PPP2q/R3K3 w Q - 0 1", "", Search.MINMAX, 3,    "e1c1")); // o-o-o
      assertTrue(testSearch("4k3/8/8/8/8/PP1PP3/2PPPP1q/R3K3 w Q - 0 1", "", Search.ALPHABETA, 3, "e1c1")); // o-o-o: TODO: Add in check rule in movegen of castling...
      assertTrue(testSearch("4k3/1r5b/8/8/8/8/PPP4N/R3K3 w Q - 0 1", "", Search.MINMAX, 3, "e1c1"));
    }
    
    @Test
    public void matingTest() throws Exception {
       // Mating with queen
       assertTrue(testSearch("r1b1k3/1p3R2/p2p1Q2/P1pPp1P1/3P4/1P6/4P1P1/RN2KBN1 w Q - 5 21", "", Search.ALPHABETA, 3, "f6e7"));
       assertTrue(testSearch("4k3/5R1/5Q2/8/8/8/8/4K3 w - - 5 21", "", Search.ALPHABETA, 4, "f6e7"));
       assertTrue(testSearch("4k3/5R1/5Q2/8/8/8/8/4K3 w - - 5 21", "", Search.ALPHABETA, 4, "f6e7"));
       assertTrue(testSearch("r1b1k3/1p3R2/p2p1Q2/P1pPp1P1/3P4/1P6/4P1P1/RN2KBN1 w Q - 5 21", "", Search.ALPHABETA, 4, "f6e7"));
       assertTrue(testSearch("r1b1k3/1p3R2/p2p1Q2/P1pPp1P1/3P4/1P6/4P1P1/RN2KBN1 w Q - 5 21", "", Search.ALPHABETA, 5,  "f6e7"));

        
       //Mating with pawn
       assertTrue(testSearch("k7/P7/KP6/8/7p/8/8/8 w - - 0 1", "", Search.MINMAX,    3,  "b6b7"));
       assertTrue(testSearch("k7/P7/KP6/8/7p/8/8/8 w - - 0 1", "", Search.ALPHABETA, 3,  "b6b7"));

       //Suffucated mate
       //assertTrue(testSearch("r3r2k/6pp/8/6N1/2Q5/1B6/1PPP4/2K5 w - - 0 1", "", Search.MINMAX, 5,  "c4g8"));
       assertTrue(testSearch("r3r2k/6pp/8/6N1/2Q5/1B6/1PPP4/2K5 w - - 0 1", "", Search.ALPHABETA, 5,  "c4g8"));
       //assertTrue(testSearch("k7/P7/KP6/8/7p/8/1P6/8 w - - 0 1", "", Search.MINMAX,    3, "b6b7"));
       assertTrue(testSearch("k7/P7/KP6/8/7p/8/1P6/8 w - - 0 1", "", Search.ALPHABETA, 3, "b6b7"));
       assertTrue(testSearch("6k1/1Q6/2p2B2/p1P1p3/P7/1P2P1PN/R2P1P1P/1N2KBR1 w - - 2 27", "", Search.ALPHABETA, 4,  "b7g7"));
    }
    
    @Test
    public void staleMateTest() throws Exception {
       //Stalemate
       assertTrue(testSearch("8/7p/7P/7P/7P/7P/4k2P/7K b - - 0 1", "", Search.MINMAX,    4, "e2f1 e2f2"));
       assertTrue(testSearch("8/7p/7P/7P/7P/7P/4k2P/7K b - - 0 1", "", Search.ALPHABETA, 4, "e2f1 e2f2"));

    }
    
    @Test
    public void avoidingMate() throws Exception {
           //Avoiding mate
       assertTrue(testSearch("k7/P1p5/KP6/8/8/8/1P5p/8 b - - 0 1", "", Search.MINMAX,    4,  "c7b6"));
       assertTrue(testSearch("k7/P1p5/KP6/8/8/8/1P5p/8 b - - 0 1", "", Search.ALPHABETA, 4,  "c7b6"));
    }

    @Test
    public void knightTest() throws Exception {
     assertTrue(testSearch("k7/4R3/8/3n4/8/2Q5/8/K7 b - - 0 1", "", Search.MINMAX,    5,  "d5e7 d5c3"));
     assertTrue(testSearch("k7/4R3/8/3n4/8/2Q5/8/K7 b - - 0 1", "", Search.ALPHABETA, 5,  "d5e7 d5c3"));
     assertTrue(testSearch("k7/4n3/8/3P4/8/8/8/K7 b - - 0 1", "", Search.ALPHABETA, 5, "e7d5"));
     assertTrue(testSearch("k7/4n3/8/5P2/8/8/8/K7 b - - 0 1", "", Search.ALPHABETA, 5, "e7f5"));
  
    
     for (int depth = 2; depth < 8; depth++) {
      //White knight example
      assertTrue(testSearch("q7/ppp1N1k1/5pN1/N4PN1/N2N2N1/8/p2PPPPP/4K3 w - - 0 1", "", Search.ALPHABETA, depth, "d4e6"));
      
      // Black knight example
      assertTrue(testSearch("1kr3b1/1B2P3/PPP3p1/Q3K3/4n1pn/1PP3P1/3Bnn2/8 b - - 1 1", "", Search.ALPHABETA, depth, "h4f3"));
      assertTrue(testSearchDoNotPlayThis("1n3rk1/4pppp/8/4P3/6n1/2N2N1P/5PP1/5RK1 b - - 1 1", Search.ALPHABETA, depth, "g4e5"));
     }
     
     
    }
    
    @Test
    public void BratkoKopecTest() throws Exception {
     assertTrue(testSearch("1k1r4/pp1b1R2/3q2pp/4p3/2B5/4Q3/PPP2B2/2K5 b - - 1 0", "", Search.ALPHABETA, 7, "d6d1")); //BK.01
    }
    
      //System.out.println("End game tactics : pawn breakthrough");
       //assert(testSearch("7k/ppp5/8/PPP5/8/8/8/7K w - - 0 1", Search.ALPHABETA, 9, "b5b6"));

}



























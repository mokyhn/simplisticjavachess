package com.simplisticchess.unit;

/*
 * @author Morten Kühnrich
 */

import com.simplisticchess.board.Bitboard;
import com.simplisticchess.board.Board;
import com.simplisticchess.piece.Piece;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class BitboardTest {
    public BitboardTest() {}
    
    @BeforeClass
    public static void setUpClass() {    }
    
    @AfterClass
    public static void tearDownClass() {    }
 
    @Before
    public void setUp() {    }

    @After
    public void tearDown() {    }


     /**
     * Test of insertPiece method, of class Bitboard.
     */
    @Test
    public void testBitboardConstructor() {
        System.out.println("testBitboardConstructor");
        Board b = new Board();

        Piece p1 = new Piece(1, 2, Piece.WHITE, Piece.KING);
        Piece p2 = new Piece(7, 4, Piece.BLACK, Piece.KING);
        Piece p3 = new Piece(5, 5, 'Q');
        Piece p4 = new Piece(6, 6, 'q');

        b.insertPiece(p1);
        b.insertPiece(p2);
        b.insertPiece(p3);
        b.insertPiece(p4);


        Bitboard instance = new Bitboard(b);
              
        assertTrue(instance.hasPiece(1, 2, Piece.WHITE, Piece.KING));
        assertTrue(instance.hasPiece(7, 4, Piece.BLACK, Piece.KING));
        assertTrue(instance.hasPiece(5, 5, Piece.WHITE, Piece.QUEEN));
        assertTrue(instance.hasPiece(6, 6, Piece.BLACK, Piece.QUEEN));
    }

    /**
     * Test of insertPiece method, of class Bitboard.
     */
    @Test
    public void testInsertPiece() {
        System.out.println("insertPiece");
        Piece p1 = new Piece(1, 2, Piece.WHITE, Piece.KING);
        Piece p2 = new Piece(7, 4, Piece.BLACK, Piece.KING);
        Piece p3 = new Piece(5, 5, 'Q');
        Piece p4 = new Piece(6, 6, 'q');
        
        Bitboard instance = new Bitboard();
        instance.insertPiece(p1);
        instance.insertPiece(p2);
        instance.insertPiece(p3);
        instance.insertPiece(p4);
              
        assertTrue(instance.hasPiece(1, 2, Piece.WHITE, Piece.KING));
        assertTrue(instance.hasPiece(7, 4, Piece.BLACK, Piece.KING));
        assertTrue(instance.hasPiece(5, 5, Piece.WHITE, Piece.QUEEN));
        assertTrue(instance.hasPiece(6, 6, Piece.BLACK, Piece.QUEEN));
    }

    /**
     * Test of removePiece method, of class Bitboard.
     */
    @Test
    public void testRemovePiece() {
        System.out.println("removePiece");

        Piece p1 = new Piece(1, 2, Piece.WHITE, Piece.KING);
        Piece p2 = new Piece(7, 4, Piece.BLACK, Piece.KING);
        Piece p3 = new Piece(5, 5, 'Q');
        Piece p4 = new Piece(6, 6, 'q');
        
        Bitboard instance = new Bitboard();
        instance.insertPiece(p1);
        instance.insertPiece(p2);
        instance.insertPiece(p3);
        instance.insertPiece(p4);
        
        
        Piece expResult1 = new Piece(1,2, 'K');
        Piece expResult2 = new Piece(7,4, 'k');
        Piece expResult3 = new Piece(6,6, 'q');
        Piece expResult4 = new Piece(5,5, 'Q');
        
        Piece result1 = instance.removePiece(1, 2);
        Piece result2 = instance.removePiece(7, 4);
        Piece result3 = instance.removePiece(6, 6);
        Piece result4 = instance.removePiece(5, 5);
        
        assertTrue(result1.equals(expResult1));
        assertTrue(result2.equals(expResult2));
        assertTrue(result3.equals(expResult3));
        assertTrue(result4.equals(expResult4));        
    }


    /**
     * Test of equals method, of class Bitboard.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");       
        
        Bitboard instance1 = new Bitboard();        
        Bitboard instance2 = new Bitboard();

        Piece p1 = new Piece(1, 2, Piece.WHITE, Piece.KING);
        Piece p2 = new Piece(7, 4, Piece.BLACK, Piece.KING);
        Piece p3 = new Piece(5, 5, 'Q');
        Piece p4 = new Piece(6, 6, 'q');
             
        instance1.insertPiece(p1);
        instance1.insertPiece(p2);

        instance2.insertPiece(p2);
        instance2.insertPiece(p1);
        
        assertFalse(instance1.equals(null));
        assertTrue(instance1.equals(instance2));          
    }

    /**
     * Test of clone method, of class Bitboard.
     */
    @Test
    public void testClone() {
        System.out.println("clone");
                Piece p1 = new Piece(1, 2, Piece.WHITE, Piece.KING);
        Piece p2 = new Piece(7, 4, Piece.BLACK, Piece.KING);
        Piece p3 = new Piece(5, 5, 'Q');
        Piece p4 = new Piece(6, 6, 'q');
        
        Bitboard instance1 = new Bitboard();
        instance1.insertPiece(p1);
        instance1.insertPiece(p2);
        instance1.insertPiece(p3);
        instance1.insertPiece(p4);
        
        Bitboard instance2 = instance1.clone();
        
        assertTrue(instance1.equals(instance2));
    
    }
    
    @Test
    public void extremetyTest1() {
     System.out.println("extremetyTest1");
     Bitboard b = new Bitboard();
     b.insertPiece(new Piece(7,7,Piece.BLACK, Piece.PAWN));
     assertTrue(b.hasPiece(7,7, Piece.BLACK, Piece.PAWN));
    }
    
    @Test
    public void testBitboardInsertDelete() {
        System.out.println("testBitboardInsertDelete");
        int color, type, x, y;
        int translatedColor;
        
        Bitboard b = new Bitboard();
        
        int n = 0;
        for (type = 0; type < 6; type++)  {            
                for (color = 0; color < 1; color++) {
                        for (x = 0; x < 8; x++) {
                                for (y = 0; y < 8; y++) {
                                    n++;
                                    translatedColor =  color == 0 ? Piece.BLACK : Piece.WHITE;
                                    b.insertPiece(new Piece(x, y, translatedColor, type));                                   
                                    assertTrue(b.hasPiece(x, y, translatedColor, type));
                                    assertTrue(b.getNumberOfPieces() == n);                        
                                }
                        }
                        assertTrue(n==64);

                        for (x = 0; x < 8; x++) {
                                for (y = 0; y < 8; y++) {
                                   translatedColor =  color == 0 ? Piece.BLACK : Piece.WHITE;
                                   b.removePiece(x, y);
                                   assertTrue(!b.hasPiece(x, y, translatedColor, type));
                                   n--;
                                   assertTrue(b.getNumberOfPieces() == n);
                                }
                        }
                        assertTrue(n==0);
                }
                assertTrue(n==0);
        }
    }
}

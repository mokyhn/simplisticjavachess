/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//package sjc;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import sjc.*;

/**
 *
 * @author moku
 */
public class BitboardTest {
    
    public BitboardTest() {
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
}

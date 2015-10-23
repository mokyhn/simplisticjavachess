package com.simplisticchess.unit;

/*
 * @author Morten Kühnrich
 */

import com.simplisticchess.board.BitBoard;
import com.simplisticchess.board.Board;
import com.simplisticchess.piece.Piece;
import com.simplisticchess.piece.Piece.Color;

import org.junit.Test;
import static org.junit.Assert.*;

public class BitBoardTest {
     /**
     * Test of insertPiece method, of class BitBoard.
     */
    @Test
    public void testBitboardConstructor() {
        System.out.println("testBitboardConstructor");
        Board b = new Board();

        Piece p1 = new Piece(1, 2, Color.WHITE, Piece.KING);
        Piece p2 = new Piece(7, 4, Color.BLACK, Piece.KING);
        Piece p3 = new Piece(5, 5, 'Q');
        Piece p4 = new Piece(6, 6, 'q');

        b.insertPiece(p1);
        b.insertPiece(p2);
        b.insertPiece(p3);
        b.insertPiece(p4);


        BitBoard instance = new BitBoard(b);
              
        assertTrue(instance.hasPiece(1, 2, Color.WHITE, Piece.KING));
        assertTrue(instance.hasPiece(7, 4, Color.BLACK, Piece.KING));
        assertTrue(instance.hasPiece(5, 5, Color.WHITE, Piece.QUEEN));
        assertTrue(instance.hasPiece(6, 6, Color.BLACK, Piece.QUEEN));
    }

    /**
     * Test of insertPiece method, of class Bitboard.
     */
    @Test
    public void testInsertPiece() {
        System.out.println("insertPiece");
        Piece p1 = new Piece(1, 2, Color.WHITE, Piece.KING);
        Piece p2 = new Piece(7, 4, Color.BLACK, Piece.KING);
        Piece p3 = new Piece(5, 5, 'Q');
        Piece p4 = new Piece(6, 6, 'q');
        
        BitBoard instance = new BitBoard();
        instance.insertPiece(p1);
        instance.insertPiece(p2);
        instance.insertPiece(p3);
        instance.insertPiece(p4);
              
        assertTrue(instance.hasPiece(1, 2, Color.WHITE, Piece.KING));
        assertTrue(instance.hasPiece(7, 4, Color.BLACK, Piece.KING));
        assertTrue(instance.hasPiece(5, 5, Color.WHITE, Piece.QUEEN));
        assertTrue(instance.hasPiece(6, 6, Color.BLACK, Piece.QUEEN));
    }

    /**
     * Test of removePiece method, of class Bitboard.
     */
    @Test
    public void testRemovePiece() {
        System.out.println("removePiece");

        Piece p1 = new Piece(1, 2, Color.WHITE, Piece.KING);
        Piece p2 = new Piece(7, 4, Color.BLACK, Piece.KING);
        Piece p3 = new Piece(5, 5, 'Q');
        Piece p4 = new Piece(6, 6, 'q');
        
        BitBoard instance = new BitBoard();
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
        
        BitBoard instance1 = new BitBoard();        
        BitBoard instance2 = new BitBoard();

        Piece p1 = new Piece(1, 2, Color.WHITE, Piece.KING);
        Piece p2 = new Piece(7, 4, Color.BLACK, Piece.KING);
        Piece p3 = new Piece(5, 5, 'Q');
        Piece p4 = new Piece(6, 6, 'q');
             
        instance1.insertPiece(p1);
        instance1.insertPiece(p2);

        instance2.insertPiece(p2);
        instance2.insertPiece(p1);
        
        assertFalse(instance1.equals(null));
        assertTrue(instance1.equals(instance2));          
    }

    @Test
    public void testCopyConstructor() {
        Piece p1 = new Piece(1, 2, Color.WHITE, Piece.KING);
        Piece p2 = new Piece(7, 4, Color.BLACK, Piece.KING);
        Piece p3 = new Piece(5, 5, 'Q');
        Piece p4 = new Piece(6, 6, 'q');
        
        BitBoard instance1 = new BitBoard();
        instance1.insertPiece(p1);
        instance1.insertPiece(p2);
        instance1.insertPiece(p3);
        instance1.insertPiece(p4);
        
        BitBoard instance2 = new BitBoard(instance1);
        
        assertTrue(instance1.equals(instance2));
    
    }
    
    @Test
    public void extremetyTest1() {
     System.out.println("extremetyTest1");
     BitBoard b = new BitBoard();
     b.insertPiece(new Piece(7,7,Color.BLACK, Piece.PAWN));
     assertTrue(b.hasPiece(7,7, Color.BLACK, Piece.PAWN));
    }
    
    @Test
    public void testBitboardInsertDelete() {
        System.out.println("testBitboardInsertDelete");
        int color, type, x, y;
        Color translatedColor;
        
        BitBoard b = new BitBoard();
        
        int n = 0;
        for (type = 0; type < 6; type++)  {            
                for (color = 0; color < 1; color++) {
                        for (x = 0; x < 8; x++) {
                                for (y = 0; y < 8; y++) {
                                    n++;
                                    translatedColor =  color == 0 ? Color.BLACK : Color.WHITE;
                                    b.insertPiece(new Piece(x, y, translatedColor, type));                                   
                                    assertTrue(b.hasPiece(x, y, translatedColor, type));
                                    assertTrue(b.getNumberOfPieces() == n);                        
                                }
                        }
                        assertTrue(n==64);

                        for (x = 0; x < 8; x++) {
                                for (y = 0; y < 8; y++) {
                                   translatedColor =  color == 0 ? Color.BLACK : Color.WHITE;
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

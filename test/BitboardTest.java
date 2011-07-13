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
public class BitboardTest {
    
    public BitboardTest() {
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
     * Test of SquareNoFromPos method, of class Bitboard.
     */
    @Test
    public void testSquareNoFromPos() {
        System.out.println("SquareNoFromPos");
        assertEquals(0,  Bitboard.SquareNoFromPos(0, 0));        
        assertEquals(9,  Bitboard.SquareNoFromPos(1, 1));
        assertEquals(63, Bitboard.SquareNoFromPos(7, 7));
    }

    /**
     * Test of setBitHigh method, of class Bitboard.
     */
    @Test
    public void testSetBitHigh() {
        System.out.println("setBitHigh");
        long result = Bitboard.setBitHigh(0);
        assertEquals(1, result);
        result = Bitboard.setBitHigh(1);
        assertEquals(2, result);
        result = Bitboard.setBitHigh(8);
        assertEquals(256, result);
    }

    /**
     * Test of toString method, of class Bitboard.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        long b = 0L;
        Bitboard instance = new Bitboard();
        String result = instance.toString(b);
        System.out.println(result);
        
    }

    /**
     * Test of equals method, of class Bitboard.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Bitboard b2 = null;
        Bitboard instance = new Bitboard();
        boolean expResult = false;
        boolean result = instance.equals(b2);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of clone method, of class Bitboard.
     */
    @Test
    public void testClone() {
        System.out.println("clone");
        Bitboard instance = new Bitboard();
        Bitboard expResult = null;
        Bitboard result = instance.clone();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}

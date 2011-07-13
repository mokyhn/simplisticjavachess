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

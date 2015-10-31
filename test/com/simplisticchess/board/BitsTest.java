package com.simplisticchess.board;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Morten Kühnrich
 */
public class BitsTest
{
    @Test
    public void testNumberOfSetBits()
    {
        assertEquals(0, Bits.NumberOfSetBits(0));
        assertEquals(1, Bits.NumberOfSetBits(1));
        assertEquals(1, Bits.NumberOfSetBits(2));
        assertEquals(2, Bits.NumberOfSetBits(3));
        assertEquals(1, Bits.NumberOfSetBits(4));
        assertEquals(2, Bits.NumberOfSetBits(5));
        assertEquals(2, Bits.NumberOfSetBits(6));
        assertEquals(3, Bits.NumberOfSetBits(7));
        assertEquals(1, Bits.NumberOfSetBits(8));
        
    }
  
    @Test
    public void testNumberOfSetBitsLarger()
    {
        assertEquals(1, Bits.NumberOfSetBits(1 << 10));
        assertEquals(1, Bits.NumberOfSetBits(2 << 10));
        assertEquals(2, Bits.NumberOfSetBits(3 << 10));
        assertEquals(1, Bits.NumberOfSetBits(4 << 10));
        assertEquals(2, Bits.NumberOfSetBits(5 << 10));
        assertEquals(2, Bits.NumberOfSetBits(6 << 10));
        assertEquals(3, Bits.NumberOfSetBits(7 << 10));
        assertEquals(1, Bits.NumberOfSetBits(8 << 10)); 
    }

    @Test
    public void testNumberOfSetBitsEvenLarger()
    {
        assertEquals(1, Bits.NumberOfSetBits(1L << 40));
        assertEquals(1, Bits.NumberOfSetBits(2L << 40));
        assertEquals(2, Bits.NumberOfSetBits(3L << 40));
        assertEquals(1, Bits.NumberOfSetBits(4L << 40));
        assertEquals(2, Bits.NumberOfSetBits(5L << 40));
        assertEquals(2, Bits.NumberOfSetBits(6L << 40));
        assertEquals(3, Bits.NumberOfSetBits(7L << 40));
        assertEquals(1, Bits.NumberOfSetBits(8L << 40)); 
    }
  
    
    
    @Test
    public void testNumberOfSetBitsMixed()
    {
        assertEquals(2, Bits.NumberOfSetBits(8 + 1L << 40));
        assertEquals(4, Bits.NumberOfSetBits((7 << 10) + (2L << 40)));
    }
  
    @Test
    public void testNumberOfSetBitsExtreme()
    {
        assertEquals(63, Bits.NumberOfSetBits(Long.MAX_VALUE));
        assertEquals(1, Bits.NumberOfSetBits(Long.MIN_VALUE));
        
    }
    
}

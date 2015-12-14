package com.simplisticchess.movegenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Morten Kühnrich
 */
public class IteratorUtilsTest
{
    public IteratorUtilsTest()
    {        
    }

    private Iterator<Integer> emptyIterator() 
    {
        return new ArrayList<Integer>().iterator();
    }
    
    private Iterator<Integer> oneElementIterator() 
    {
        return Arrays.asList(1).iterator();
    }
    
    private Iterator<Integer> threeElementIterator() 
    {
        return Arrays.asList(1,2,3).iterator();
    }

    private Iterator<Integer> reversedThreeElementIterator() 
    {
        return Arrays.asList(3,2,1).iterator();
    }
    
    
    
    @Test
    public void testEmptyAndEmpty()
    {
        Iterator result = IteratorUtils.compose(emptyIterator(), emptyIterator());
        assertFalse(result.hasNext());
    }
    
    @Test
    public void testEmptyAndOne()
    {
        Iterator result = IteratorUtils.compose(emptyIterator(), oneElementIterator());
        assertTrue(result.hasNext());
        assertEquals(1, result.next());
        assertFalse(result.hasNext());   
    }

    @Test
    public void testOneAndEmpty() 
    {
        Iterator result = IteratorUtils.compose(oneElementIterator(), emptyIterator());
        assertTrue(result.hasNext());
        assertEquals(1, result.next());
        assertFalse(result.hasNext());   
    }
        
    @Test
    public void testOneAndOne() 
    {
        Iterator result = IteratorUtils.compose(oneElementIterator(), oneElementIterator());
        assertTrue(result.hasNext());
        assertEquals(1, result.next());
        assertTrue(result.hasNext());
        assertEquals(1, result.next());
        assertFalse(result.hasNext());   
    }    
    
    @Test
    public void testThreeAndThree() 
    {
        Iterator result = IteratorUtils.compose(threeElementIterator(), threeElementIterator());
        assertTrue(result.hasNext());
        assertEquals(1, result.next());
        assertTrue(result.hasNext());
        assertEquals(2, result.next());
        assertTrue(result.hasNext());
        assertEquals(3, result.next());
        assertTrue(result.hasNext());
        assertEquals(1, result.next());
        assertTrue(result.hasNext());
        assertEquals(2, result.next());
        assertTrue(result.hasNext());
        assertEquals(3, result.next());
        assertFalse(result.hasNext());        
    } 
    
    @Test
    public void testReversedThreeAndThree() 
    {
        Iterator result = IteratorUtils.compose(reversedThreeElementIterator(), threeElementIterator());
        assertTrue(result.hasNext());
        assertEquals(3, result.next());
        assertTrue(result.hasNext());
        assertEquals(2, result.next());
        assertTrue(result.hasNext());
        assertEquals(1, result.next());
        assertTrue(result.hasNext());
        assertEquals(1, result.next());
        assertTrue(result.hasNext());
        assertEquals(2, result.next());
        assertTrue(result.hasNext());
        assertEquals(3, result.next());
        assertFalse(result.hasNext());        
    } 

}

package com.simplisticchess.movegenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import static org.junit.Assert.*;
import org.junit.Test;

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
    public void testEmptyAndEmpty_compose_two()
    {
        Iterator result = IteratorUtils.compose(emptyIterator(), emptyIterator());
        assertFalse(result.hasNext());
    }
    
    @Test
    public void testEmptyAndOne_compose_two()
    {
        Iterator result = IteratorUtils.compose(emptyIterator(), oneElementIterator());
        assertTrue(result.hasNext());
        assertEquals(1, result.next());
        assertFalse(result.hasNext());   
    }

    @Test
    public void testOneAndEmpty_compose_two() 
    {
        Iterator result = IteratorUtils.compose(oneElementIterator(), emptyIterator());
        assertTrue(result.hasNext());
        assertEquals(1, result.next());
        assertFalse(result.hasNext());   
    }
        
    @Test
    public void testOneAndOne_compose_two() 
    {
        Iterator result = IteratorUtils.compose(oneElementIterator(), oneElementIterator());
        assertTrue(result.hasNext());
        assertEquals(1, result.next());
        assertTrue(result.hasNext());
        assertEquals(1, result.next());
        assertFalse(result.hasNext());   
    }    
    
    @Test
    public void testThreeAndThree_compose_two() 
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
    public void testReversedThreeAndThree_compose_two() 
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

    @Test
    public void testEmptyAndEmpty_compose_many()
    {
        Collection<Iterator<Integer>> input = (Collection<Iterator<Integer>>) Arrays.asList(emptyIterator(), emptyIterator());
        Iterator result = IteratorUtils.compose(input);
        assertFalse(result.hasNext());
    }
    
    @Test
    public void testEmptyAndOne_compose_many()
    {
        Collection<Iterator<Integer>> input = (Collection<Iterator<Integer>>) Arrays.asList(emptyIterator(), oneElementIterator());
        Iterator result = IteratorUtils.compose(input);
        assertTrue(result.hasNext());
        assertEquals(1, result.next());
        assertFalse(result.hasNext());   
    }

    @Test
    public void testOneAndEmpty_compose_many()
    {
       Collection<Iterator<Integer>> input = (Collection<Iterator<Integer>>) Arrays.asList(oneElementIterator(), emptyIterator());
        Iterator result = IteratorUtils.compose(input);
        assertTrue(result.hasNext());
        assertEquals(1, result.next());
        assertFalse(result.hasNext());   
    }
        
    @Test
    public void testOneAndOne_compose_many()
    {
        Collection<Iterator<Integer>> input = (Collection<Iterator<Integer>>) Arrays.asList(oneElementIterator(), oneElementIterator());
        Iterator result = IteratorUtils.compose(input);
        assertTrue(result.hasNext());
        assertEquals(1, result.next());
        assertTrue(result.hasNext());
        assertEquals(1, result.next());
        assertFalse(result.hasNext());   
    }    
    
    @Test
    public void testThreeAndThree_compose_many() 
    {
        Collection<Iterator<Integer>> input = (Collection<Iterator<Integer>>) Arrays.asList(threeElementIterator(), threeElementIterator());
        Iterator result = IteratorUtils.compose(input);
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
    public void testReversedThreeAndThree_compose_many() 
    {
        Collection<Iterator<Integer>> input = (Collection<Iterator<Integer>>) Arrays.asList(reversedThreeElementIterator(), threeElementIterator());
        Iterator result = IteratorUtils.compose(input);
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
 
    @Test
    public void testReversedThreeEmptyAndThreeAndThree_compose_many() 
    {
        Collection<Iterator<Integer>> input = (Collection<Iterator<Integer>>) Arrays.asList(reversedThreeElementIterator(), emptyIterator(), threeElementIterator(), threeElementIterator());
        Iterator result = IteratorUtils.compose(input);
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
        assertTrue(result.hasNext());
        assertEquals(1, result.next());
        assertTrue(result.hasNext());
        assertEquals(2, result.next());
        assertTrue(result.hasNext());
        assertEquals(3, result.next());
        assertFalse(result.hasNext());
        assertNull(result.next());
    }     
    
}

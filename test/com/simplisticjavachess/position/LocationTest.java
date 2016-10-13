package com.simplisticjavachess.position;

import org.junit.Test;
import static org.junit.Assert.*;

public class LocationTest {
    @Test 
    public void testStringContructor() throws InvalidLocationException 
    {
        Location p = new Location("F7");
        assertEquals(new Location(5, 6), p);
    }
    
    @Test
    public void testCopyConstructor() 
    {
        Location p1 = new Location(3, 5);        
        Location p2 = new Location(p1);
        assertEquals(p1, p2);       
    }
    
    @Test
    public void testGetters() 
    {
        Location p = new Location(3, 5);        
        assertEquals(3, p.getX());
        assertEquals(5, p.getY());
    }

    @Test
    public void testToString()
    {
        Location p = new Location(3, 5);        
        assertEquals("d6", p.toString());
    }
    
    @Test
    public void testFromToStringComposition() throws InvalidLocationException
    {
        assertEquals(new Location("a1").toString(), "a1");
        assertEquals(new Location("a4").toString(), "a4");
        assertEquals(new Location("h8").toString(), "h8");
    }
    
    @Test(expected=InvalidLocationException.class)
    public void testInvalidFile() throws InvalidLocationException
    {
        Location location = new Location("i4");
    }
    
    @Test(expected=InvalidLocationException.class)
    public void testInvalidRank() throws InvalidLocationException
    {
        Location location = new Location("a9");
    }
    
    @Test
    public void testHash1() 
    {
        Location location = new Location(0,0);
        assertEquals(0, location.hashCode());
    }
    
    @Test
    public void testHash2() 
    {
        Location location = new Location(7,0);
        assertEquals(7, location.hashCode());
    }

    @Test
    public void testHash3() 
    {
        Location location = new Location(7,7);
        assertEquals(63, location.hashCode());
    }
}

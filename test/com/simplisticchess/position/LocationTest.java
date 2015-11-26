package com.simplisticchess.position;

import org.junit.Test;
import static org.junit.Assert.*;

public class LocationTest {
    @Test 
    public void testStringContructor() throws Exception 
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
    public void testFromToStringComposition() throws Exception
    {
        assertEquals(new Location("a1").toString(), "a1");
        assertEquals(new Location("a4").toString(), "a4");
        assertEquals(new Location("h8").toString(), "h8");
    }
}

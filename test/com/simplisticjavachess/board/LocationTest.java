package com.simplisticjavachess.board;

import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;

public class LocationTest {
    @Test 
    public void testStringContructor() 
    {
        Location p = new Location("F7");
        assertEquals(new Location(5, 6), p);
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
    public void testFromToStringComposition()
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
    
    @Test
    public void testAllHashValues()
    {
        Set<Integer> result = new HashSet<Integer>();
        
        for (int x = 0; x < 8; x++)
        {
            for (int y = 0; y < 8; y++)
            {
                Location location = new Location(x, y);
                Integer hashCode = location.hashCode();
                result.add(hashCode);
            }
        }
        
        assertEquals(64, result.size());
    }
    
    @Test
    public void testLocationDistance()
    {
        Location l1 = new Location(3,4);
        Location l2 = new Location(5,7);
        assertEquals(3, l1.distanceTo(l2));
        assertEquals(3, l2.distanceTo(l1));
        assertEquals(0, l1.distanceTo(l1));
        
        assertEquals(2, l1.horizontalDistance(l2));
        assertEquals(3, l1.verticalDistance(l2));
    }
    
    public void testIsValid1()
    {
        assertTrue(new Location(5,4).isValid());
    }
    
    public void testIsValid2()
    {
        assertFalse(new Location(7,8).isValid());
    }
}

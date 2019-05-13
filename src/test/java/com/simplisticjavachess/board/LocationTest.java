package com.simplisticjavachess.board;

import java.util.*;

import com.simplisticjavachess.misc.*;
import org.junit.Test;

import static com.simplisticjavachess.misc.IteratorUtils.toList;
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
    
    @Test(expected=IllegalArgumentException.class)
    public void testInvalidFile()
    {
        new Location("i4");
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testInvalidRank()
    {
        new Location("a9");
    }

    @Test
    public void testAllHashValues()
    {
        Set<Integer> result = new HashSet<>();
        
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

    @Test
    public void testIsValid1()
    {
        assertTrue(new Location(5,4).isValid());
    }

    @Test
    public void testIsValid2()
    {
        assertFalse(new Location(7,8).isValid());
    }

    @Test
    public void testLocationIterationDiagonalUp()
    {
        Location l = Location.fromString("b2");
        String result = IteratorUtils.toString(l.iterateTO(Location.fromString("h8")));
        assertEquals("[c3, d4, e5, f6, g7]", result);
    }

    @Test
    public void testLocationIterationDiagonalDown()
    {
        Location l = Location.fromString("h8");
        String result = IteratorUtils.toString(l.iterateTO(Location.fromString("b2")));
        assertEquals("[g7, f6, e5, d4, c3]", result);
    }


    @Test
    public void testLocationIterationRight()
    {
        Location l = Location.fromString("a6");
        String result = IteratorUtils.toString(l.iterateTO(Location.fromString("f6")));
        assertEquals("[b6, c6, d6, e6]", result);
    }

    @Test
    public void testLocationIterationLeft()
    {
        Location l = Location.fromString("f6");
        String result = IteratorUtils.toString(l.iterateTO(Location.fromString("a6")));
        assertEquals("[e6, d6, c6, b6]", result);
    }

    @Test
    public void testLocationIterationUp()
    {
        Location l = Location.fromString("d2");
        String result = IteratorUtils.toString(l.iterateTO(Location.fromString("d8")));
        assertEquals("[d3, d4, d5, d6, d7]", result);
    }

    @Test
    public void testLocationIterationDown()
    {
        Location l = Location.fromString("d8");
        String result = IteratorUtils.toString(l.iterateTO(Location.fromString("d2")));
        assertEquals("[d7, d6, d5, d4, d3]", result);
    }
}

package com.simplisticjavachess.position;

import java.util.*;

import com.simplisticjavachess.misc.IteratorUtils;
import org.junit.Test;

import static org.junit.Assert.*;

public class LocationTest {
    @Test 
    public void testStringContructor() 
    {
        Location p = Location.parse("F7");
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
        assertEquals(Location.parse("a1").toString(), "a1");
        assertEquals(Location.parse("a4").toString(), "a4");
        assertEquals(Location.parse("h8").toString(), "h8");
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testInvalidFile()
    {
        Location.parse("i4");
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testInvalidRank()
    {
        Location.parse("a9");
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
        Location l = Location.parse("b2");
        String result = IteratorUtils.toString(l.iterateTO(Location.parse("h8")).iterator());
        assertEquals("[c3, d4, e5, f6, g7]", result);
    }

    @Test
    public void testLocationIterationDiagonalDown()
    {
        Location l = Location.parse("h8");
        String result = IteratorUtils.toString(l.iterateTO(Location.parse("b2")).iterator());
        assertEquals("[g7, f6, e5, d4, c3]", result);
    }


    @Test
    public void testLocationIterationRight()
    {
        Location l = Location.parse("a6");
        String result = IteratorUtils.toString(l.iterateTO(Location.parse("f6")).iterator());
        assertEquals("[b6, c6, d6, e6]", result);
    }

    @Test
    public void testLocationIterationLeft()
    {
        Location l = Location.parse("f6");
        String result = IteratorUtils.toString(l.iterateTO(Location.parse("a6")).iterator());
        assertEquals("[e6, d6, c6, b6]", result);
    }

    @Test
    public void testLocationIterationUp()
    {
        Location l = Location.parse("d2");
        String result = IteratorUtils.toString(l.iterateTO(Location.parse("d8")).iterator());
        assertEquals("[d3, d4, d5, d6, d7]", result);
    }

    @Test
    public void testLocationIterationDown()
    {
        Location l = Location.parse("d8");
        String result = IteratorUtils.toString(l.iterateTO(Location.parse("d2")).iterator());
        assertEquals("[d7, d6, d5, d4, d3]", result);
    }

    @Test
    public void testLocationIterationNoFields()
    {
        Location l = Location.parse("d4");
        String result = IteratorUtils.toString(l.iterateTO(Location.parse("d5")).iterator());
        assertEquals("[]", result);
    }

    @Test
    public void fileTest()
    {
        assertTrue(Location.parse("f8").fileEquals(Location.parse("f1")));
        assertTrue(Location.parse("f8").fileDifferent(Location.parse("a1")));
    }

    @Test
    public void rankTest()
    {
        assertTrue(Location.parse("f8").rankEquals(Location.parse("a8")));
        assertTrue(Location.parse("f8").rankDifferent(Location.parse("a1")));
    }

    @Test
    public void diagonalTest()
    {
        assertTrue(Location.parse("f8").diagonalEquals(Location.parse("a3")));
        assertFalse(Location.parse("f8").diagonalEquals(Location.parse("g6")));
    }

    @Test
    public void iterateTest()
    {
        assertEquals("[e3, e4, e5, e6, e7, e8]",
                IteratorUtils.toString(Location.parse("e2").iterate(Vector.UP).iterator()));

    }

    @Test
    public void iterateTestEmpty()
    {
        assertEquals("[]", IteratorUtils.toString(Location.parse("h8").iterate(Vector.UP).iterator()));
    }
}


package com.simplisticchess.position;

import org.junit.Test;
import static org.junit.Assert.*;

public class PointTest {
    @Test 
    public void testStringContructor()
    {
        Point p = new Point("F7");
        assertEquals(new Point(5, 6), p);
    }
    
    @Test
    public void testCopyConstructor() 
    {
        Point p1 = new Point(3, 5);        
        Point p2 = new Point(p1);
        assertEquals(p1, p2);       
    }
    
    @Test
    public void testGetters() 
    {
        Point p = new Point(3, 5);        
        assertEquals(3, p.getX());
        assertEquals(5, p.getY());
    }

    @Test
    public void testToString()
    {
        Point p = new Point(3, 5);        
        assertEquals("d5", p.toString());
    }
    
    @Test
    public void testFromToStringComposition()
    {
        assertEquals(new Point("a0").toString(), "a0");
        assertEquals(new Point("a4").toString(), "a4");
        assertEquals(new Point("h8").toString(), "h8");
    }
}

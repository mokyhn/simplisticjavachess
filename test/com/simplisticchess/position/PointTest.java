package com.simplisticchess.position;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PointTest {
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
}

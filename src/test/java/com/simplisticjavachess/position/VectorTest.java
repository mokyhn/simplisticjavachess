package com.simplisticjavachess.position;

import com.simplisticjavachess.piece.Color;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

/**
 *
 * @author Morten Kühnrich
 */
public class VectorTest {
     
    @Test
    public void testGetDx() {
        assertEquals(-1, Vector.LEFT.getDx());
    }

    @Test
    public void testGetDy() {
        assertEquals(1, Vector.UP.getDy());
    }

    @Test
    public void testAdd() {
        assertEquals(new Vector(1,1), Vector.UP.add(Vector.RIGHT));
    }

    @Test
    public void testScale() {
        assertEquals(new Vector(2, 18), new Vector(1, 9).scale(2));
    }

    @Test
    public void testInverse() {
        assertEquals(Vector.DOWN, Vector.UP.inverse());
    }

    @Test
    public void testGetMoveDirection() {
        assertEquals(Vector.UP, Vector.getMoveDirection(Color.WHITE));
    }

    @Test
    public void testTranslocate() {
        assertEquals(new Location(5,65), new Vector(1,2).translate(new Location(4, 63)));
        assertEquals(new Location(4,62), Vector.DOWN.translate(new Location(4, 63)));
        assertEquals(new Location(4,64), Vector.UP.translate(new Location(4, 63)));
        assertEquals(new Location(3,63), Vector.LEFT.translate(new Location(4, 63)));
        assertEquals(new Location(5,63), Vector.RIGHT.translate(new Location(4, 63)));
    }

    @Test
    public void normalizeTest()
    {
        assertEquals(new Vector(1,1), new Vector(4,4).normalize().get());
        assertEquals(new Vector(1,0), new Vector(4,0).normalize().get());
        assertEquals(new Vector(-1,1), new Vector(-4,4).normalize().get());
        assertEquals(new Vector(0,1), new Vector(0,3).normalize().get());
    }

    @Test
    public void cannotNormalizeTest()
    {
        assertEquals(Optional.empty(), new Vector(1,2).normalize());

    }

}

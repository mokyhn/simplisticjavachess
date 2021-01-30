package com.simplisticjavachess.position;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HalfMoveClockTest {

    @Test
    public void testParse() {
        HalfMoveClock halfMoveClock = new HalfMoveClock();
        halfMoveClock = halfMoveClock.tick().tick();
        assertEquals(halfMoveClock, HalfMoveClock.fromString("2"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseIll() {
        HalfMoveClock.fromString("1as23");
    }

    public void testEquals() {
        HalfMoveClock halfMoveClockA = new HalfMoveClock();
        HalfMoveClock halfMoveClockB = new HalfMoveClock();
        assertTrue(halfMoveClockA.equals(halfMoveClockA));
        assertTrue(halfMoveClockA.equals(halfMoveClockB));
        assertFalse(halfMoveClockA.equals(halfMoveClockB.tick()));
    }

}
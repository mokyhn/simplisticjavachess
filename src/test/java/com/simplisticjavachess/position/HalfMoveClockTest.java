package com.simplisticjavachess.position;

import org.junit.Test;

import static org.junit.Assert.*;

public class HalfMoveClockTest
{

    @Test
    public void testParse()
    {
        assertEquals(new HalfMoveClock(123), HalfMoveClock.fromString("123"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseIll()
    {
        HalfMoveClock.fromString("1as23");
    }

}
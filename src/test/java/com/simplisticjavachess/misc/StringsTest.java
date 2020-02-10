package com.simplisticjavachess.misc;

import org.junit.Test;

import static org.junit.Assert.*;

public class StringsTest {

    @Test
    public void trimWhiteSpace()
    {
        assertEquals("", Strings.trimWhiteSpace(""));
        assertEquals("a b", Strings.trimWhiteSpace(" a b"));
        assertEquals("a b", Strings.trimWhiteSpace(" a b "));
        assertEquals("a b", Strings.trimWhiteSpace("a      b"));
        assertEquals("a b c d e", Strings.trimWhiteSpace("a b  c d      e"));
    }
}
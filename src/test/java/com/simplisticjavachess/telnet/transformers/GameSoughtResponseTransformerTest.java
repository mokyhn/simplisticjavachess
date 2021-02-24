package com.simplisticjavachess.telnet.transformers;

import com.simplisticjavachess.UnitTest;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

@UnitTest
public class GameSoughtResponseTransformerTest {

    private final static String SEEK_1 = "GuestWFFD (++++) seeking 5 5 unrated blitz f (\"play 78\" to respond)";
    private final static String SEEK_2 = "callipygian(C) (1698) seeking 10 0 rated blitz f (\"play 7\" to respond)";

    GameSoughtResponseTransformer gameSoughtResponseTransformer;

    @Before
    public void setUp() {
        gameSoughtResponseTransformer = new GameSoughtResponseTransformer();
    }

    @Test
    public void isApplicable() {
        assertFalse(gameSoughtResponseTransformer.isApplicable(SEEK_1));
        assertTrue(gameSoughtResponseTransformer.isApplicable(SEEK_2));
    }

    @Test
    public void apply() {
        assertEquals("play 78", gameSoughtResponseTransformer.apply(SEEK_1).getArgument());
        assertEquals("play 7", gameSoughtResponseTransformer.apply(SEEK_2).getArgument());
    }
}
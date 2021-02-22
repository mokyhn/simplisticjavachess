package com.simplisticjavachess.telnet.transformers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class GameCreatedResponseTransformerTest {

    GameCreatedResponseTransformer handler;

    String myUserName = "GuestHRLH";

    @Before
    public void before() {
        this.handler = new GameCreatedResponseTransformer(myUserName);
    }


    @Test
    public void isApplicable() {
        assertTrue(handler.isApplicable("Creating: GuestHRLH (++++) GuestWRXX (++++) unrated blitz 2 15"));
    }

    @Test
    public void apply() {
        assertEquals("kjkljl", handler.apply("Creating: GuestHRLH (++++) GuestWRXX (++++) unrated blitz 2 15"));
    }
}
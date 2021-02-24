package com.simplisticjavachess.telnet.transformers;

import com.simplisticjavachess.UnitTest;
import com.simplisticjavachess.telnet.Command;
import com.simplisticjavachess.telnet.CommandEnum;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@UnitTest
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
        assertEquals(new Command(CommandEnum.NEW_GAME, "AS_WHITE"), handler.apply("Creating: GuestHRLH (++++) GuestWRXX (++++) unrated blitz 2 15"));
    }
}
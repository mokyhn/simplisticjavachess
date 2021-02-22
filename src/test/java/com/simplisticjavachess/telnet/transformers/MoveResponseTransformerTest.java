package com.simplisticjavachess.telnet.transformers;

import com.simplisticjavachess.UnitTest;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@UnitTest
public class MoveResponseTransformerTest {
    String response0 = "#@#052GuestYPRQ       :uun             *RNBQKBNRPPPPPPPP                                " +
            "pppppppprnbqkbnr001W39390012000120none   (0:00)@#@\n";

    String response1 = "#@#033GuestHKDM       *GuestHRLH       :RNBQKBNRPPPP PPP            P     p             pp ppppprnbqkbnr002W39390012000120P/c7-c5(0:00)@#@";
    String response2 = "Nnbqkbnr p---ppp- -------- -------p -------- -------- PPPP-PPP RNBQKBNR B -1 1 1 1 0 0 318 xyzwxyzwq xyzwxyzw -1 2 60 41 31 311 264 5 P/b7-a8=N (0:24) bxa8=N 0 1 0";
    String response3 = "#@#052GuestYPRQ       :uun             *RNB  RK PPP BPPP    PN     p       p            pp   ppprnbqkbnr006B29380016800158o-o    (0:05)@#@";
    String response4 = "{Game 82 (GuestHRLH vs. GuestWRXX) GuestHRLH resigns} 0-1";


    MoveResponseTransformer handler = new MoveResponseTransformer();

    @Test
    public void isApplicableTest() {
        assertFalse(handler.isApplicable(response0));
        assertTrue(handler.isApplicable(response1));
        assertTrue(handler.isApplicable(response2));
        assertTrue(handler.isApplicable(response3));
        assertFalse(handler.isApplicable(response4));
    }

    @Test
    public void parseMoveTest() {
        assertEquals(Optional.empty(), handler.parseMove(response0));
        assertEquals(Optional.of("c7c5"), handler.parseMove(response1));
        assertEquals(Optional.of("b7a8N"), handler.parseMove(response2));
        assertEquals(Optional.of("o-o"), handler.parseMove(response3));
        assertEquals(Optional.empty(), handler.parseMove(response4));
    }
}
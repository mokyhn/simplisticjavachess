package com.simplisticjavachess.piece;

import static org.junit.Assert.assertNotEquals;

import com.simplisticjavachess.UnitTest;
import org.junit.Test;

@UnitTest
public class ColorTest {

    @Test
    public void testHashCode() {
        assertNotEquals(Color.BLACK.getChessHashCode(), Color.WHITE.getChessHashCode());
    }
}

package com.simplisticjavachess.piece;

import static org.junit.Assert.assertNotEquals;
import org.junit.Test;

public class ColorTest {
    
    @Test
    public void testHashCode()
    {
        assertNotEquals(Color.BLACK.getChessHashCode(), Color.WHITE.getChessHashCode());
    }
}

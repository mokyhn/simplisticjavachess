package com.simplisticjavachess.piece;

import static org.junit.Assert.assertNotEquals;
import org.junit.Test;

public class PieceTypeTest
{
    @Test
    public void hashCodeTest()
    {
        assertNotEquals(PieceType.BISHOP.hashCode(), PieceType.KNIGHT.hashCode());
    }
}

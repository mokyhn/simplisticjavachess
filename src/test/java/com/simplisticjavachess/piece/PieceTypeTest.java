package com.simplisticjavachess.piece;

import static org.junit.Assert.assertNotEquals;

import com.simplisticjavachess.UnitTest;
import org.junit.Test;

@UnitTest
public class PieceTypeTest {
    @Test
    public void hashCodeTest() {
        assertNotEquals(PieceType.BISHOP.hashCode(), PieceType.KNIGHT.hashCode());
    }
}

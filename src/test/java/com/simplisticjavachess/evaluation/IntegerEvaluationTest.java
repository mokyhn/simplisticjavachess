package com.simplisticjavachess.evaluation;

import com.simplisticjavachess.UnitTest;
import com.simplisticjavachess.piece.Color;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Morten Kühnrich
 */
@UnitTest
public class IntegerEvaluationTest {

    private static Evaluation NONE = new IntegerEvaluator().getNone();

    @Test
    public void testImproveNoneWithNone() {
        assertFalse(NONE.isWorseThan(Color.BLACK, NONE));
    }

    @Test
    public void testIsWorseThanNoneWithSomething() {
        assertTrue(NONE.isWorseThan(Color.BLACK, IntegerEvaluation.of(0)));
    }

    @Test
    public void testIsWorseThanSomethingWithNone() {
        assertFalse(IntegerEvaluation.of(0).isWorseThan(Color.BLACK, NONE));
    }

    @Test
    public void testIsWorseLessThanWhite() {
        assertTrue(IntegerEvaluation.of(0).isWorseThan(Color.WHITE, IntegerEvaluation.of(5)));
    }

    @Test
    public void testIsWorseLessThanBlack() {
        assertFalse(IntegerEvaluation.of(0).isWorseThan(Color.BLACK, IntegerEvaluation.of(5)));
    }

    @Test
    public void testImproveNoneWithSomething() {
        IntegerEvaluation result = (IntegerEvaluation) NONE.improveWith(Color.BLACK, IntegerEvaluation.of(17));
        assertEquals(IntegerEvaluation.of(17), result);
    }

    @Test
    public void testImproveSomethingWithNone() {
        IntegerEvaluation result = (IntegerEvaluation) IntegerEvaluation.of(17).improveWith(Color.BLACK, NONE);
        assertEquals(IntegerEvaluation.of(17), result);
    }

    @Test
    public void testImproveSomethingWithSomething_no_improvement_equals() {
        IntegerEvaluation result = (IntegerEvaluation) IntegerEvaluation.of(17).improveWith(Color.BLACK,
                IntegerEvaluation.of(17));
        assertEquals(IntegerEvaluation.of(17), result);
    }

    @Test
    public void testImproveSomethingWithSomething_no_improvement_larger() {
        IntegerEvaluation result = (IntegerEvaluation) IntegerEvaluation.of(17).improveWith(Color.BLACK,
                IntegerEvaluation.of(18));
        assertEquals(IntegerEvaluation.of(17), result);
    }

    @Test
    public void testBlackImproveSomethingWithSomething() {
        IntegerEvaluation result = (IntegerEvaluation) IntegerEvaluation.of(17).improveWith(Color.BLACK,
                IntegerEvaluation.of(16));
        assertEquals(IntegerEvaluation.of(16), result);
    }

    @Test
    public void testWhiteImproveSomethingWithSomething() {
        IntegerEvaluation result = (IntegerEvaluation) IntegerEvaluation.of(17).improveWith(Color.WHITE,
                IntegerEvaluation.of(18));
        assertEquals(IntegerEvaluation.of(18), result);
    }

    @Test
    public void testToString() {
        assertEquals("None", NONE.toString());
        assertEquals("17", IntegerEvaluation.of(17).toString());
    }

    @Test
    public void testEquals() {
        assertEquals(IntegerEvaluation.of(17), IntegerEvaluation.of(17));
        Assert.assertNotEquals(IntegerEvaluation.of(17), NONE);
    }


}

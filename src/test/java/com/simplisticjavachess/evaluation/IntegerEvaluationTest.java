package com.simplisticjavachess.evaluation;

import com.simplisticjavachess.UnitTest;
import com.simplisticjavachess.piece.Color;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Morten Kühnrich
 */
@UnitTest
public class IntegerEvaluationTest {

    private static Evaluation NONE = EvaluationConstantsFactoryImpl.instance().getNone();

    @Test
    public void testImproveNoneWithSomething() {
        IntegerEvaluation result = (IntegerEvaluation) NONE.improveWith(Color.BLACK, IntegerEvaluation.of(17));
        Assert.assertEquals(IntegerEvaluation.of(17), result);
    }

    @Test
    public void testImproveSomethingWithNone() {
        IntegerEvaluation result = (IntegerEvaluation) IntegerEvaluation.of(17).improveWith(Color.BLACK, NONE);
        Assert.assertEquals(IntegerEvaluation.of(17), result);
    }

    @Test
    public void testImproveSomethingWithSomething_no_improvement_equals() {
        IntegerEvaluation result = (IntegerEvaluation) IntegerEvaluation.of(17).improveWith(Color.BLACK,
                IntegerEvaluation.of(17));
        Assert.assertEquals(IntegerEvaluation.of(17), result);
    }

    @Test
    public void testImproveSomethingWithSomething_no_improvement_larger() {
        IntegerEvaluation result = (IntegerEvaluation) IntegerEvaluation.of(17).improveWith(Color.BLACK,
                IntegerEvaluation.of(18));
        Assert.assertEquals(IntegerEvaluation.of(17), result);
    }

    @Test
    public void testBlackImproveSomethingWithSomething() {
        IntegerEvaluation result = (IntegerEvaluation) IntegerEvaluation.of(17).improveWith(Color.BLACK,
                IntegerEvaluation.of(16));
        Assert.assertEquals(IntegerEvaluation.of(16), result);
    }

    @Test
    public void testWhiteImproveSomethingWithSomething() {
        IntegerEvaluation result = (IntegerEvaluation) IntegerEvaluation.of(17).improveWith(Color.WHITE,
                IntegerEvaluation.of(18));
        Assert.assertEquals(IntegerEvaluation.of(18), result);
    }

    @Test
    public void testToString() {
        Assert.assertEquals("None", NONE.toString());
        Assert.assertEquals("17", IntegerEvaluation.of(17).toString());
    }

    @Test
    public void testEquals() {
        Assert.assertEquals(IntegerEvaluation.of(17), IntegerEvaluation.of(17));
        Assert.assertNotEquals(IntegerEvaluation.of(17), NONE);
    }


}

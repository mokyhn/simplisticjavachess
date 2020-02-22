package com.simplisticjavachess.evaluation;

import com.simplisticjavachess.piece.Color;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Morten Kühnrich
 */
public class IntegerEvaluationTest
{

    private static Evaluation NONE = EvaluationConstantsFactoryImpl.instance().getNone();

    @Test
    public void testImproveNoneWithSomething()
    {
        IntegerEvaluation result = (IntegerEvaluation) NONE.improveWith(Color.BLACK, IntegerEvaluation.of(17));
        assertEquals(IntegerEvaluation.of(17), result);
    }

    @Test
    public void testImproveSomethingWithNone()
    {
        IntegerEvaluation result = (IntegerEvaluation) IntegerEvaluation.of(17).improveWith(Color.BLACK, NONE);
        assertEquals(IntegerEvaluation.of(17), result);
    }

    @Test
    public void testImproveSomethingWithSomething_no_improvement_equals()
    {
        IntegerEvaluation result = (IntegerEvaluation) IntegerEvaluation.of(17).improveWith(Color.BLACK, IntegerEvaluation.of(17));
        assertEquals(IntegerEvaluation.of(17), result);
    }

    @Test
    public void testImproveSomethingWithSomething_no_improvement_larger()
    {
        IntegerEvaluation result = (IntegerEvaluation) IntegerEvaluation.of(17).improveWith(Color.BLACK, IntegerEvaluation.of(18));
        assertEquals(IntegerEvaluation.of(17), result);
    }

    @Test
    public void testBlackImproveSomethingWithSomething()
    {
        IntegerEvaluation result = (IntegerEvaluation) IntegerEvaluation.of(17).improveWith(Color.BLACK, IntegerEvaluation.of(16));
        assertEquals(IntegerEvaluation.of(16), result);
    }

    @Test
    public void testWhiteImproveSomethingWithSomething()
    {
        IntegerEvaluation result = (IntegerEvaluation) IntegerEvaluation.of(17).improveWith(Color.WHITE, IntegerEvaluation.of(18));
        assertEquals(IntegerEvaluation.of(18), result);
    }

    @Test
    public void testToString()
    {
        assertEquals("None", NONE.toString());
        assertEquals("17", IntegerEvaluation.of(17).toString());
    }

    @Test
    public void testEquals()
    {
        assertEquals(IntegerEvaluation.of(17), IntegerEvaluation.of(17));
        assertNotEquals(IntegerEvaluation.of(17), NONE);
    }

    /**
     * Test of hashCode method, of class Evaluation.
     */
    @Test
    public void testHashCode()
    {
        assertEquals(0, NONE.hashCode());
        assertNotEquals(0, IntegerEvaluation.of(17).hashCode());
    }

}

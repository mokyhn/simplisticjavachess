package com.simplisticjavachess.evaluator;

import com.simplisticjavachess.piece.Color;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Morten Kühnrich
 */
public class EvaluationTest
{

    @Test
    public void testImproveNoneWithSomething()
    {
        Evaluation result = Evaluation.NONE.improveWith(Color.BLACK, Evaluation.of(17));
        assertEquals(Evaluation.of(17), result);
    }

    @Test
    public void testImproveSomethingWithNone()
    {
        Evaluation result = Evaluation.of(17).improveWith(Color.BLACK, Evaluation.NONE);
        assertEquals(Evaluation.of(17), result);
    }

    @Test
    public void testImproveSomethingWithSomething_no_improvement_equals()
    {
        Evaluation result = Evaluation.of(17).improveWith(Color.BLACK, Evaluation.of(17));
        assertEquals(Evaluation.of(17), result);
    }

    @Test
    public void testImproveSomethingWithSomething_no_improvement_larger()
    {
        Evaluation result = Evaluation.of(17).improveWith(Color.BLACK, Evaluation.of(18));
        assertEquals(Evaluation.of(17), result);
    }

    @Test
    public void testBlackImproveSomethingWithSomething()
    {
        Evaluation result = Evaluation.of(17).improveWith(Color.BLACK, Evaluation.of(16));
        assertEquals(Evaluation.of(16), result);
    }

    @Test
    public void testWhiteImproveSomethingWithSomething()
    {
        Evaluation result = Evaluation.of(17).improveWith(Color.WHITE, Evaluation.of(18));
        assertEquals(Evaluation.of(18), result);
    }

    @Test
    public void testToString()
    {
        assertEquals("None", Evaluation.NONE.toString());
        assertEquals("17", Evaluation.of(17).toString());
    }

    @Test
    public void testEquals()
    {
        assertEquals(Evaluation.of(17), Evaluation.of(17));
        assertNotEquals(Evaluation.of(17), Evaluation.NONE);
    }

    /**
     * Test of hashCode method, of class Evaluation.
     */
    @Test
    public void testHashCode()
    {
        assertEquals(0, Evaluation.NONE.hashCode());
        assertNotEquals(0, Evaluation.of(17).hashCode());
    }

}

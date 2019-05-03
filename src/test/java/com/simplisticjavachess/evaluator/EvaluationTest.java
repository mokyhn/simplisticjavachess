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
        Evaluation result = Evaluation.NONE.improveWith(Color.BLACK, new Evaluation(17));
        assertEquals(new Evaluation(17), result);
    }

    @Test
    public void testImproveSomethingWithNone()
    {
        Evaluation result = new Evaluation(17).improveWith(Color.BLACK, Evaluation.NONE);
        assertEquals(new Evaluation(17), result);
    }

    @Test
    public void testImproveSomethingWithSomething_no_improvement_equals()
    {
        Evaluation result = new Evaluation(17).improveWith(Color.BLACK, new Evaluation(17));
        assertEquals(new Evaluation(17), result);
    }
    
    @Test
    public void testImproveSomethingWithSomething_no_improvement_larger()
    {
        Evaluation result = new Evaluation(17).improveWith(Color.BLACK, new Evaluation(18));        
        assertEquals(new Evaluation(17), result);
    }
    
    @Test
    public void testBlackImproveSomethingWithSomething()
    {
        Evaluation result = new Evaluation(17).improveWith(Color.BLACK, new Evaluation(16));        
        assertEquals(new Evaluation(16), result);
    }

    @Test
    public void testWhiteImproveSomethingWithSomething()
    {
        Evaluation result = new Evaluation(17).improveWith(Color.WHITE, new Evaluation(18));        
        assertEquals(new Evaluation(18), result);
    }

    @Test
    public void testToString()
    {
        assertEquals("None", Evaluation.NONE.toString());
        assertEquals("17", new Evaluation(17).toString());
    }

    @Test
    public void testEquals()
    {
        assertTrue(Evaluation.NONE.equals(Evaluation.NONE));
        assertTrue(new Evaluation(17).equals(new Evaluation(17)));
        assertFalse(new Evaluation(17).equals(Evaluation.NONE));
    }

    /**
     * Test of hashCode method, of class Evaluation.
     */
    @Test
    public void testHashCode()
    {
        assertEquals(0, Evaluation.NONE.hashCode());
        assertNotEquals(0, new Evaluation(17).hashCode());
    }
    
}

package com.simplisticjavachess.evaluation;

import com.simplisticjavachess.UnitTest;
import com.simplisticjavachess.position.PositionIO;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@UnitTest
public class IntegerEvaluatorTest {

    @Test
    public void evaluateAll() {
        assertEquals("0",
                new IntegerEvaluator().evaluate(
                        PositionIO.FEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1")).toString());

    }

    @Test
    public void evaluatePieces() {
        assertEquals("5", new IntegerEvaluator().evaluate(PositionIO.algebraic("Rd1 w")).toString());
        assertEquals("-5", new IntegerEvaluator().evaluate(PositionIO.algebraic("rd1 w")).toString());
        assertEquals("3", new IntegerEvaluator().evaluate(PositionIO.algebraic("Bd1 w")).toString());
        assertEquals("-3", new IntegerEvaluator().evaluate(PositionIO.algebraic("bd1 w")).toString());
        assertEquals("3", new IntegerEvaluator().evaluate(PositionIO.algebraic("Nd1 w")).toString());
        assertEquals("-3", new IntegerEvaluator().evaluate(PositionIO.algebraic("nd1 w")).toString());
        assertEquals("9", new IntegerEvaluator().evaluate(PositionIO.algebraic("Qd1 w")).toString());
        assertEquals("-9", new IntegerEvaluator().evaluate(PositionIO.algebraic("qd1 w")).toString());
        assertEquals("0", new IntegerEvaluator().evaluate(PositionIO.algebraic("Kd1 w")).toString());
        assertEquals("0", new IntegerEvaluator().evaluate(PositionIO.algebraic("kd1 w")).toString());
    }

}
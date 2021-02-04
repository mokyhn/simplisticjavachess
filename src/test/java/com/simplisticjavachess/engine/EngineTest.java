package com.simplisticjavachess.engine;

import com.simplisticjavachess.evaluation.Evaluation;
import com.simplisticjavachess.evaluation.Evaluator;
import com.simplisticjavachess.movegenerator.MoveGenerator;
import com.simplisticjavachess.piece.Color;
import com.simplisticjavachess.position.Mover;
import com.simplisticjavachess.position.Position;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class EngineTest {

    private final static Collection engines =
            Arrays.asList(new MinMaxEngine(), new AlphaBetaEngine(), new RandomEngine());

    private final Engine engine;

    public EngineTest(Engine engine) {
        this.engine = engine;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection get() {
        return engines;
    }


    Position position;
    Mover mover;
    MoveGenerator moveGenerator;
    Evaluator evaluator;
    Evaluation evaluation;

    @Before
    public void before() {
        position = mock(Position.class);
        mover = mock(Mover.class);
        moveGenerator = mock(MoveGenerator.class);
        evaluator = mock(Evaluator.class);
        evaluation = mock(Evaluation.class);
    }

    @Test
    public void testDrawBy50MoveRule() {
        when(position.isDrawBy50Move()).thenReturn(true);
        when(evaluator.getEqual()).thenReturn(evaluation);
        SearchResult searchResult = engine.search(position, mover, moveGenerator, evaluator, 0);
        Assert.assertFalse(searchResult.getMoveSequence().iterator().hasNext());
        Assert.assertEquals(evaluation, searchResult.getEvaluation());
    }

    @Test
    public void testEvaluationDepth0() {
        when(position.isDrawBy50Move()).thenReturn(false);
        when(evaluator.evaluate(position)).thenReturn(evaluation);
        SearchResult searchResult = engine.search(position, mover, moveGenerator, evaluator, 0);
        Assert.assertFalse(searchResult.getMoveSequence().iterator().hasNext());
        Assert.assertEquals(evaluation, searchResult.getEvaluation());
    }

    @Test
    public void testEvaluationMate() {
        when(position.isDrawBy50Move()).thenReturn(false);
        when(moveGenerator.generateMoves(position)).thenReturn(Collections.emptyIterator());
        when(position.isInCheck()).thenReturn(true);
        when(position.inMove()).thenReturn(Color.WHITE);
        when(evaluator.getWhiteIsMate()).thenReturn(evaluation);
        SearchResult searchResult = engine.search(position, mover, moveGenerator, evaluator, 2);
        verify(evaluator, times(1)).getWhiteIsMate();
    }

    @Test
    public void testEvaluationStaleMate() {
        when(position.isDrawBy50Move()).thenReturn(false);
        when(moveGenerator.generateMoves(position)).thenReturn(Collections.emptyIterator());
        when(position.isInCheck()).thenReturn(false);
        when(position.inMove()).thenReturn(Color.WHITE);
        when(evaluator.getWhiteIsMate()).thenReturn(evaluation);
        SearchResult searchResult = engine.search(position, mover, moveGenerator, evaluator, 2);
        verify(evaluator, times(1)).getEqual();
    }

    //TODO: Add tree tests here. a) Basic trees and b) Deeper trees with alpha/beta scenarios

}
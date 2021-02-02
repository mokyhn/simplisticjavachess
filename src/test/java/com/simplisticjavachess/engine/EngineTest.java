package com.simplisticjavachess.engine;

import com.simplisticjavachess.evaluation.Evaluation;
import com.simplisticjavachess.evaluation.Evaluator;
import com.simplisticjavachess.movegenerator.MoveGenerator;
import com.simplisticjavachess.position.Mover;
import com.simplisticjavachess.position.Position;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.mockito.Mockito.mock;
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
    Evaluation equalsEvaluation;

    @Before
    public void before() {
        position = mock(Position.class);
        mover = mock(Mover.class);
        moveGenerator = mock(MoveGenerator.class);
        evaluator = mock(Evaluator.class);
        equalsEvaluation = mock(Evaluation.class);
    }

    @Test
    public void testDrawBy50MoveRule() {
        when(position.isDrawBy50Move()).thenReturn(true);
        when(evaluator.getEqual()).thenReturn(equalsEvaluation);
        SearchResult searchResult = engine.search(position, mover, moveGenerator, evaluator, 0);
        Assert.assertFalse(searchResult.getMoveSequence().iterator().hasNext());
        Assert.assertEquals(equalsEvaluation, searchResult.getEvaluation());
    }

    // Engines under test




}
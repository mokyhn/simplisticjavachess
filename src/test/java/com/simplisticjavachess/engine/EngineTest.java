package com.simplisticjavachess.engine;

import com.simplisticjavachess.evaluation.Evaluation;
import com.simplisticjavachess.evaluation.Evaluator;
import com.simplisticjavachess.evaluation.IntegerEvaluation;
import com.simplisticjavachess.movegenerator.MoveGenerator;
import com.simplisticjavachess.piece.Color;
import com.simplisticjavachess.position.IllegalMoveException;
import com.simplisticjavachess.position.Mover;
import com.simplisticjavachess.position.Position;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class EngineTest {

    private final static Collection engines =
            Arrays.asList(new MinMaxEngine(), new AlphaBetaEngine());

    private final Engine engine;

    public EngineTest(Engine engine) {
        this.engine = engine;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection get() {
        return engines;
    }


    Mover mover;
    MoveGenerator moveGenerator;
    Evaluator evaluator;
    Evaluation evaluation;

    TreeMaker treeMaker;


    @Before
    public void before() {
        mover = mock(Mover.class);
        moveGenerator = mock(MoveGenerator.class);
        evaluator = mock(Evaluator.class);
        when(evaluator.getNone()).thenReturn(new IntegerEvaluation());


        treeMaker = new TreeMaker(moveGenerator, mover, evaluator);

        evaluation = mock(Evaluation.class);


    }

    @Test
    public void testDrawBy50MoveRule() {
        TreeMaker t = treeMaker.getDrawBy50MoveRule(evaluation);
        SearchResult searchResult = engine.search(t.getRoot(), t.getMover(), t.getMoveGenerator(), t.getEvaluator(), 0);
        Assert.assertFalse(searchResult.getMoveSequence().iterator().hasNext());
        assertEquals(evaluation, searchResult.getEvaluation());
    }

    @Test
    public void testEvaluationDepth0() {
        Position position = mock(Position.class);
        when(position.isDrawBy50Move()).thenReturn(false);
        when(evaluator.evaluate(position)).thenReturn(evaluation);
        SearchResult searchResult = engine.search(position, mover, moveGenerator, evaluator, 0);
        Assert.assertFalse(searchResult.getMoveSequence().iterator().hasNext());
        assertEquals(evaluation, searchResult.getEvaluation());
    }

    @Test
    public void testEvaluationMate() {
        TreeMaker t = treeMaker.mate(Color.WHITE, evaluation);
        SearchResult searchResult = engine.search(t.getRoot(), t.getMover(),
                t.getMoveGenerator(), t.getEvaluator(), 1);
        verify(t.getEvaluator(), times(1)).getWhiteIsMate();
        assertEquals(evaluation, searchResult.getEvaluation());
    }

    @Test
    public void testEvaluationStaleMate() {
        TreeMaker t = treeMaker.staleMate(Color.WHITE, evaluation);
        SearchResult searchResult = engine.search(t.getRoot(), t.getMover(),
                t.getMoveGenerator(), t.getEvaluator(), 2);
        verify(t.getEvaluator(), times(1)).getEqual();
    }


    @Test
    public void testSimpleTreeWhite() throws IllegalMoveException {
        TreeMaker t = treeMaker.leafs(Color.WHITE,0, 1, 2);
        SearchResult searchResult = engine.search(t.getRoot(), t.getMover(),
                t.getMoveGenerator(), t.getEvaluator(), 1);
        assertEquals("2", searchResult.getEvaluation().toString());
    }

    @Test
    public void testSimpleTreeBlack() throws IllegalMoveException {
        TreeMaker t = treeMaker.leafs(Color.BLACK, 0, 1, 2);
        SearchResult searchResult = engine.search(t.getRoot(), t.getMover(),
                t.getMoveGenerator(), t.getEvaluator(), 1);
        assertEquals("0", searchResult.getEvaluation().toString());
    }

    @Test
    public void testDepth2TreeWhite() throws IllegalMoveException {
        TreeMaker t = treeMaker.compose(Color.WHITE,
                treeMaker.leafs(Color.BLACK, 0, 1, 2),
                treeMaker.leafs(Color.BLACK, 4, 3, 5));

        SearchResult searchResult = engine.search(t.getRoot(),
                t.getMover(), t.getMoveGenerator(), t.getEvaluator(), 2);
        assertEquals("3", searchResult.getEvaluation().toString());
    }

    @Test
    public void testDepth2TreeBlack() throws IllegalMoveException {
        TreeMaker t = treeMaker.compose(Color.BLACK,
                treeMaker.leafs(Color.WHITE, 0, 1, 2),
                treeMaker.leafs(Color.WHITE, 4, 3, 5));

        SearchResult searchResult = engine.search(t.getRoot(),
                t.getMover(), t.getMoveGenerator(), t.getEvaluator(), 2);
        assertEquals("2", searchResult.getEvaluation().toString());
    }

    @Test
    public void testDepth6TreeWhiteWithCutoffs() throws IllegalMoveException {
        TreeMaker t1l = treeMaker.compose(Color.WHITE, treeMaker.compose(Color.BLACK,
                treeMaker.leafs(Color.WHITE, 5, 6),
                treeMaker.leafs(Color.WHITE, 7, 4, 5)));

        TreeMaker t1r = treeMaker.compose(Color.WHITE, treeMaker.compose(Color.BLACK,
                treeMaker.leafs(Color.WHITE, 3)));

        TreeMaker t1 = treeMaker.compose(Color.BLACK, t1l, t1r);

        TreeMaker t2l = treeMaker.compose(Color.WHITE, treeMaker.compose(Color.BLACK,
                treeMaker.leafs(Color.WHITE, 6), treeMaker.leafs(Color.WHITE, 6, 9)));

        TreeMaker t2r = treeMaker.compose(Color.WHITE, treeMaker.compose(Color.BLACK,
                treeMaker.leafs(Color.WHITE, 7)));

        TreeMaker t2 = treeMaker.compose(Color.BLACK, t2l, t2r);




        TreeMaker t3l = treeMaker.compose(Color.WHITE, treeMaker.compose(Color.BLACK,
                treeMaker.leafs(Color.WHITE, 5)));

        TreeMaker t3r = treeMaker.compose(Color.WHITE, treeMaker.compose(Color.BLACK,
                treeMaker.leafs(Color.WHITE, 9, 8),
                treeMaker.leafs(Color.WHITE, 6)));

        TreeMaker t3 = treeMaker.compose(Color.BLACK, t3l, t3r);

        TreeMaker t = treeMaker.compose(Color.WHITE, t1, t2, t3);



        SearchResult searchResult = engine.search(t.getRoot(),
                t.getMover(), t.getMoveGenerator(), t.getEvaluator(), 5);
        assertEquals("6", searchResult.getEvaluation().toString());
    }

}
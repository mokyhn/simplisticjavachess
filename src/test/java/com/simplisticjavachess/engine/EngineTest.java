package com.simplisticjavachess.engine;

import com.simplisticjavachess.evaluation.Evaluation;
import com.simplisticjavachess.evaluation.Evaluator;
import com.simplisticjavachess.evaluation.IntegerEvaluation;
import com.simplisticjavachess.move.Move;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
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

    // Mocked positions for non-leaf nodes
    List<Position> positions;


    // Mocked positions for leaf nodes
    List<Position> leafs;

    // Mocked evaluations for leaf nodes
    Map<Position, Evaluation> evaluations;

    List<Move> moves;

    @Before
    public void before() {
        position = mock(Position.class);
        mover = mock(Mover.class);
        moveGenerator = mock(MoveGenerator.class);
        evaluator = mock(Evaluator.class);
        when(evaluator.getNone()).thenReturn(new IntegerEvaluation());

        evaluation = mock(Evaluation.class);

        evaluations = new HashMap<>();

        positions = new ArrayList<>();
        leafs = new ArrayList<>();

        // Mock leafs
        for (int i = 0; i < 10; i++) {
            Position thePosition = mock(Position.class);
            when(thePosition.isDrawBy50Move()).thenReturn(false);
            positions.add(mock(Position.class));
            Position leaf = mock(Position.class);
            when(leaf.isDrawBy50Move()).thenReturn(false);
            leafs.add(leaf);
            evaluations.put(leaf, IntegerEvaluation.of(i));
            when(evaluator.evaluate(leaf)).thenReturn(IntegerEvaluation.of(i));
        }

        moves = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            moves.add(mock(Move.class));
        }

    }

    @Test
    public void testDrawBy50MoveRule() {
        when(position.isDrawBy50Move()).thenReturn(true);
        when(evaluator.getEqual()).thenReturn(evaluation);
        SearchResult searchResult = engine.search(position, mover, moveGenerator, evaluator, 0);
        Assert.assertFalse(searchResult.getMoveSequence().iterator().hasNext());
        assertEquals(evaluation, searchResult.getEvaluation());
    }

    @Test
    public void testEvaluationDepth0() {
        when(position.isDrawBy50Move()).thenReturn(false);
        when(evaluator.evaluate(position)).thenReturn(evaluation);
        SearchResult searchResult = engine.search(position, mover, moveGenerator, evaluator, 0);
        Assert.assertFalse(searchResult.getMoveSequence().iterator().hasNext());
        assertEquals(evaluation, searchResult.getEvaluation());
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


    @Test
    public void testSimpleTreeWhite() throws IllegalMoveException {
        when(position.isDrawBy50Move()).thenReturn(false);
        when(moveGenerator.generateMoves(position)).thenReturn(Arrays.asList(moves.get(0), moves.get(1), moves.get(2)).iterator());
        when(position.inMove()).thenReturn(Color.WHITE);

        when(mover.doMove(position, moves.get(0))).thenReturn(leafs.get(0));
        when(mover.doMove(position, moves.get(1))).thenReturn(leafs.get(1));
        when(mover.doMove(position, moves.get(2))).thenReturn(leafs.get(2));

        SearchResult searchResult = engine.search(position, mover, moveGenerator, evaluator, 1);
        assertEquals("2", searchResult.getEvaluation().toString());

    }

    @Test
    public void testSimpleTreeBlack() throws IllegalMoveException {
        when(position.isDrawBy50Move()).thenReturn(false);
        when(moveGenerator.generateMoves(position)).thenReturn(Arrays.asList(moves.get(0), moves.get(1), moves.get(2)).iterator());
        when(position.inMove()).thenReturn(Color.BLACK);

        when(mover.doMove(position, moves.get(0))).thenReturn(leafs.get(0));
        when(mover.doMove(position, moves.get(1))).thenReturn(leafs.get(1));
        when(mover.doMove(position, moves.get(2))).thenReturn(leafs.get(2));

        SearchResult searchResult = engine.search(position, mover, moveGenerator, evaluator, 1);
        assertEquals("0", searchResult.getEvaluation().toString());

    }
    //TODO: Add tree tests here. a) Depth 1 trees (done), b) simple depth tree (2) and b) Deeper trees with alpha/beta scenarios
    // Print number of nodes visited in some tests
}
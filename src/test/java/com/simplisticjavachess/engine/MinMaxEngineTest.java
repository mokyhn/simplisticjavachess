package com.simplisticjavachess.engine;

import com.simplisticjavachess.board.BoardParser;
import com.simplisticjavachess.board.Position;
import com.simplisticjavachess.evaluation.Evaluator;
import com.simplisticjavachess.evaluation.IntegerEvaluator;
import com.simplisticjavachess.movegenerator.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MinMaxEngineTest {

    MinMaxEngine engine;
    MoveGenerator moveGenerator;
    Evaluator evaluator;

    @Before
    public void before()
    {
        engine = new MinMaxEngine();
        evaluator = new IntegerEvaluator();
    }

    @Test
    public void tryToGetAdvantage() {
        Position position = BoardParser.algebraic("Kh1 Pb2 kd8 na5 nc5 w");
        moveGenerator = new MainMoveGenerator(MainMoveGenerator.PAWN_MOVE_GENERATOR,
                MainMoveGenerator.KNIGHT_MOVE_GENERATOR, MainMoveGenerator.KING_MOVE_GENERATOR);
        SearchResult result = engine.search(position, moveGenerator, evaluator, 3);
        assertEquals("b2-b4", result.getMoveSequence().getFirst().toString());
    }

    @Test
    public void protoToKnightAndMate()
    {
        Position position = BoardParser.FEN("8/6Pp/7k/5K1p/7P/8/8/B7 w - - 0 1");
        moveGenerator = new MainMoveGenerator();
        SearchResult result = engine.search(position, moveGenerator, evaluator, 3);
        assertEquals("g7-g8=N", result.getMoveSequence().getFirst().toString());
    }

    @Test
    public void obtainStaleMate()
    {
        Position position = BoardParser.FEN("2r4r/7k/7p/8/8/pp6/p7/K5R1 w - - 0 1");
        moveGenerator = new MainMoveGenerator();
        SearchResult result = engine.search(position, moveGenerator, evaluator, 4);
        assertEquals("g1-g7", result.getMoveSequence().getFirst().toString());
    }

}
package com.simplisticjavachess.engine;

import com.simplisticjavachess.End2EndTest;
import com.simplisticjavachess.movegenerator.MainMoveGenerator;
import com.simplisticjavachess.movegenerator.MoveGenerator;
import com.simplisticjavachess.position.ChessMover;
import com.simplisticjavachess.position.Mover;
import com.simplisticjavachess.position.PositionIO;
import com.simplisticjavachess.position.Position;
import com.simplisticjavachess.evaluation.Evaluator;
import com.simplisticjavachess.evaluation.IntegerEvaluator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

@End2EndTest
public class MinMaxEngineTest {

    MinMaxEngine engine;
    Mover mover;
    MoveGenerator moveGenerator;
    Evaluator evaluator;

    @Before
    public void before() {
        engine = new MinMaxEngine();
        evaluator = new IntegerEvaluator();
        mover = new ChessMover();
    }

    @Test
    public void tryToGetAdvantage() {
        Position position = PositionIO.algebraic("Kh1 Pb2 kd8 na5 nc5 w"); //TODO rewrite into cucumber like:
        // with a white pawn on "D2"
        // with a black king on "A2"
        // with etc
        // with black to move
        // with white to move...
        moveGenerator = new MainMoveGenerator(MainMoveGenerator.PAWN_MOVE_GENERATOR,
                MainMoveGenerator.KNIGHT_MOVE_GENERATOR, MainMoveGenerator.KING_MOVE_GENERATOR);
        SearchResult result = engine.search(position, mover, moveGenerator, evaluator, 3);
        Assert.assertEquals("b2-b4", result.getMoveSequence().getFirst().toString());
    }

    @Test
    public void protoToKnightAndMate() {
        Position position = PositionIO.FEN("8/6Pp/7k/5K1p/7P/8/8/B7 w - - 0 1");
        moveGenerator = new MainMoveGenerator();
        SearchResult result = engine.search(position, mover, moveGenerator, evaluator, 3);
        Assert.assertEquals("g7-g8=N", result.getMoveSequence().getFirst().toString());
    }

    @Test
    public void obtainStaleMate() {
        Position position = PositionIO.FEN("2r4r/7k/7p/8/8/pp6/p7/K5R1 w - - 0 1");
        moveGenerator = new MainMoveGenerator();
        SearchResult result = engine.search(position, mover, moveGenerator, evaluator, 4);
        Assert.assertEquals("g1-g7", result.getMoveSequence().getFirst().toString());
    }

}
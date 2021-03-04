package com.simplisticjavachess.movegenerator;

import com.simplisticjavachess.UnitTest;
import com.simplisticjavachess.evaluation.ComplexEvaluator;
import com.simplisticjavachess.position.PositionIO;
import com.simplisticjavachess.position.Position;
import com.simplisticjavachess.misc.IteratorUtils;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.piece.Piece;
import com.simplisticjavachess.piece.PieceType;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;

@UnitTest
public class QueenMoveGeneratorTest {
    @Test
    public void getTypeTest() {
        assertEquals(PieceType.QUEEN, new QueenMoveGenerator().getPieceType());
    }

    @Test
    public void queenMovesCenterTest() {
        Position board = PositionIO.algebraic("Qd4 w");
        Piece piece = Piece.parse("Qd4");
        Iterator<Move> moves = new QueenMoveGenerator().generateMoves(board, piece);
        assertEquals("[d4-e5, d4-f6, d4-g7, d4-h8, d4-e3, d4-f2, d4-g1, d4-c5, d4-b6, d4-a7, d4-c3, " +
                        "d4-b2, d4-a1, d4-d5, d4-d6, d4-d7, d4-d8, d4-d3, d4-d2, d4-d1, d4-e4, d4-f4, d4-g4, d4-h4, " +
                        "d4-c4, d4-b4, d4-a4]",
                IteratorUtils.toList(moves).toString());
    }

    @Test
    public void queenMovesShadowsTest() {
        Position board = PositionIO.algebraic("Qd4 Pd6 Pb6 Pf6 Pc3 Pd1 pf2 w");
        Piece piece = Piece.parse("Qd4");
        Iterator<Move> moves = new QueenMoveGenerator().generateMoves(board, piece);
        assertEquals("[d4-e5, d4-e3, d4xf2, d4-c5, " +
                        "d4-d5, d4-d3, d4-d2, d4-e4, d4-f4, d4-g4, d4-h4, d4-c4, d4-b4, d4-a4]",
                IteratorUtils.toList(moves).toString());
    }

    @Test
    public void wierd() {
        MoveGenerator moveGenerator = new MainMoveGenerator();

        Position before = PositionIO.FEN("r1b2k1r/pppp2pp/2n1pp2/2P3q1/1P1PP3/2BB4/P3QPPP/3R1RK1 b kq - 0 14");
        Position position = PositionIO.FEN("r1b2k1r/pppp2pp/2n1pp2/2P3q1/1P1PP3/2BB4/P3QPPP/3R1RK1 b kq - 0 14");

        String moves1 = IteratorUtils.toString(moveGenerator.generateMoves(position));
        String moves2 = IteratorUtils.toString(moveGenerator.generateMoves(position));

        System.out.println(moves1);

        ComplexEvaluator complexEvaluator = new ComplexEvaluator();

        complexEvaluator.evaluate(position);
        complexEvaluator.evaluate(position);

        assertEquals(before, position);
        assertEquals(moves1, moves2);

    }

}
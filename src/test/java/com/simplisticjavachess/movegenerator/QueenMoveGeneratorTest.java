package com.simplisticjavachess.movegenerator;

import com.simplisticjavachess.board.PositionIO;
import com.simplisticjavachess.board.Position;
import com.simplisticjavachess.misc.IteratorUtils;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.piece.Piece;
import com.simplisticjavachess.piece.PieceType;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

public class QueenMoveGeneratorTest
{
    @Test
    public void getTypeTest()
    {
        assertEquals(PieceType.QUEEN, new QueenMoveGenerator().getPieceType());
    }

    @Test
    public void queenMovesCenterTest() {
        Position board = PositionIO.algebraic("Qd4 w");
        Piece piece = Piece.parse("Qd4");
        Iterator<Move> moves = new QueenMoveGenerator().generateMoves(board, piece);
        assertEquals("[d4-e5, d4-f6, d4-g7, d4-h8, d4-e3, d4-f2, d4-g1, d4-c5, d4-b6, d4-a7, d4-c3, " +
                        "d4-b2, d4-a1, d4-d5, d4-d6, d4-d7, d4-d8, d4-d3, d4-d2, d4-d1, d4-e4, d4-f4, d4-g4, d4-h4, d4-c4, d4-b4, d4-a4]",
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

}
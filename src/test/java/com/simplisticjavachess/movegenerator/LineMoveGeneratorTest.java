package com.simplisticjavachess.movegenerator;

import com.simplisticjavachess.UnitTest;
import com.simplisticjavachess.position.PositionIO;
import com.simplisticjavachess.position.Position;
import com.simplisticjavachess.position.Vector;
import com.simplisticjavachess.misc.IteratorUtils;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.piece.Piece;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Morten Kühnrich
 */
@UnitTest
public class LineMoveGeneratorTest {

    @Test
    public void testGenerateMovesUpRight() {
        Position position = PositionIO.algebraic("Bd5 w");
        Piece piece = Piece.parse("Bd5");

        List<Move> result = IteratorUtils.toList(new LineMoveGenerator(new Vector(1, 1)).generateMoves(position,
                piece));
        Assert.assertEquals(3, result.size()); // e6, f7, g8
        Assert.assertTrue(result.toString().contains("d5-e6"));
        Assert.assertTrue(result.toString().contains("d5-f7"));
        Assert.assertTrue(result.toString().contains("d5-g8"));
    }

    @Test
    public void testGenerateMovesDown() {
        Position board = PositionIO.algebraic("Rd5 w");
        Piece piece = Piece.parse("Rd5");

        List<Move> result = IteratorUtils.toList(new LineMoveGenerator(new Vector(0, -1)).generateMoves(board, piece));
        Assert.assertEquals(4, result.size());
        Assert.assertTrue(result.toString().contains("d5-d4"));
        Assert.assertTrue(result.toString().contains("d5-d3"));
        Assert.assertTrue(result.toString().contains("d5-d2"));
        Assert.assertTrue(result.toString().contains("d5-d1"));
    }


    @Test
    public void testGenerateMovesLeft() {
        Position board = PositionIO.algebraic("Rd5 w");
        Piece piece = Piece.parse("Rd5");

        List<Move> result = IteratorUtils.toList(new LineMoveGenerator(new Vector(-1, 0)).generateMoves(board, piece));
        Assert.assertEquals(3, result.size());
        Assert.assertTrue(result.toString().contains("d5-c5"));
        Assert.assertTrue(result.toString().contains("d5-b5"));
        Assert.assertTrue(result.toString().contains("d5-a5"));
    }

    @Test
    public void testGenerateMovesLeftBlockingPiece() {
        Position board = PositionIO.algebraic("Rd5 Pb5 w");
        Piece piece = Piece.parse("Rd5");

        List<Move> result = IteratorUtils.toList(new LineMoveGenerator(new Vector(-1, 0)).generateMoves(board, piece));
        Assert.assertEquals(1, result.size());
        Assert.assertTrue(result.toString().contains("d5-c5"));
    }

    @Test
    public void testGenerateDownRightAndCapture() {
        Position board = PositionIO.algebraic("Bc5 pe3 pf2 w");
        Piece piece = Piece.parse("Bc5");

        List<Move> result = IteratorUtils.toList(new LineMoveGenerator(new Vector(1, -1)).generateMoves(board, piece));
        Assert.assertEquals(2, result.size());
        Assert.assertTrue(result.toString().contains("c5-d4"));
        Assert.assertTrue(result.toString().contains("c5xe3"));
    }

}

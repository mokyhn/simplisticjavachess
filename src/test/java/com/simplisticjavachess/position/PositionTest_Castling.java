/**
 * @author Morten Kühnrich
 */
package com.simplisticjavachess.position;

import com.simplisticjavachess.move.Moves;
import com.simplisticjavachess.piece.Color;
import org.junit.Assert;
import org.junit.Test;

public class PositionTest_Castling {
    private static Position WHITE_IN_MOVE_CASTLING() {
        return PositionIO.FEN("r3k2r/8/8/8/8/8/8/R3K2R w KQkq - 0 0");
    }

    private static Position WHITE_IN_MOVE_CANNOT_CASTLE() {
        return PositionIO.FEN("r3k2r/8/8/8/8/8/8/R3K2R w kq - 0 0");
    }

    private static Position AFTER_WHITE_SHORT_CASTLING() {
        return PositionIO.FEN("r3k2r/8/8/8/8/8/8/R4RK1 b kq - 0 0");
    }

    private static Position AFTER_WHITE_LONG_CASTLING() {
        return PositionIO.FEN("r3k2r/8/8/8/8/8/8/2KR3R b kq - 0 0");
    }


    private static Position BLACK_IN_MOVE_CASTLING() {
        return PositionIO.FEN("r3k2r/8/8/8/8/8/8/R3K2R b KQkq - 0 0");
    }

    private static Position BLACK_IN_MOVE_CANNOT_CASTLE() {
        return PositionIO.FEN("r3k2r/8/8/8/8/8/8/R3K2R b KQ - 0 0");
    }

    private static Position AFTER_BLACK_SHORT_CASTLING() {
        return PositionIO.FEN("r4rk1/8/8/8/8/8/8/R3K2R w KQ - 0 0");
    }

    private static Position AFTER_BLACK_LONG_CASTLING() {
        return PositionIO.FEN("2kr3r/8/8/8/8/8/8/R3K2R w KQ - 0 0");
    }

    private final static Mover mover = new ChessMover();

    @Test
    public void testWhiteShortCastling() throws IllegalMoveException {
        // Test of doMove
        Position actual = WHITE_IN_MOVE_CASTLING();
        Position expected = AFTER_WHITE_SHORT_CASTLING();
        Position moveResult = mover.doMove(actual, Moves.WHITE_SHORT_CASTLING());
        Assert.assertEquals(expected, moveResult);
    }

    @Test(expected = IllegalMoveException.class)
    public void testWhiteShortCastling_cannotCastle() throws IllegalMoveException {
        Position actual = WHITE_IN_MOVE_CANNOT_CASTLE();
        mover.doMove(actual, Moves.WHITE_SHORT_CASTLING());
    }

    @Test
    public void testBlackShortCastling() throws IllegalMoveException {
        Position actual = BLACK_IN_MOVE_CASTLING();
        Position expected = AFTER_BLACK_SHORT_CASTLING();
        Position moveResult = mover.doMove(actual, Moves.BLACK_SHORT_CASTLING());
        Assert.assertEquals(expected, moveResult);
    }

    @Test(expected = IllegalMoveException.class)
    public void testBlackShortCastling_cannotCastle() throws IllegalMoveException {
        Position actual = BLACK_IN_MOVE_CANNOT_CASTLE();
        mover.doMove(actual, Moves.BLACK_SHORT_CASTLING());
    }

    @Test
    public void testLongShortCastling() throws IllegalMoveException {
        Position actual = WHITE_IN_MOVE_CASTLING();
        Position expected = AFTER_WHITE_LONG_CASTLING();
        Position moveResult = mover.doMove(actual, Moves.WHITE_LONG_CASTLING());
        Assert.assertEquals(expected, moveResult);
    }

    @Test(expected = IllegalMoveException.class)
    public void testWhiteLongCastling_cannotCastle() throws IllegalMoveException {
        Position actual = WHITE_IN_MOVE_CANNOT_CASTLE();
        mover.doMove(actual, Moves.WHITE_LONG_CASTLING());
    }

    @Test
    public void testBlackLongCastling() throws IllegalMoveException {
        Position actual = BLACK_IN_MOVE_CASTLING();
        Position expected = AFTER_BLACK_LONG_CASTLING();
        Position moveResult = mover.doMove(actual, Moves.BLACK_LONG_CASTLING());
        Assert.assertEquals(expected, moveResult);
    }

    @Test(expected = IllegalMoveException.class)
    public void testBlackLongCastling_cannotCastle() throws IllegalMoveException {
        Position actual = BLACK_IN_MOVE_CANNOT_CASTLE();
        mover.doMove(actual, Moves.BLACK_LONG_CASTLING());
    }

    @Test
    public void rookMoveRemovesCastlingLongPossibility() throws IllegalMoveException {
        Position board = PositionIO.algebraic("Ra1 Ke1 kh8 w");
        board = board.setCanCastleLong(true, Color.WHITE);
        board = mover.doMove(board, "a1b1 h8g8 b1a1 g8h8");
        Assert.assertTrue(board.isWhiteInMove());
        Assert.assertFalse(board.canCastleLong());
    }

    @Test
    public void rookMoveRemovesCastlingShortPossibility() throws IllegalMoveException {
        Position board = PositionIO.algebraic("Rh1 Ke1 ka8 w");
        board = board.setCanCastleShort(true, Color.WHITE);
        board = mover.doMove(board, "h1g1 a8b8 g1h1 b8a8");
        Assert.assertTrue(board.isWhiteInMove());
        Assert.assertFalse(board.canCastleShort());
    }
}

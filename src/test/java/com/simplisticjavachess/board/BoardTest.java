package com.simplisticjavachess.board;

import com.simplisticjavachess.piece.Color;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest
{
    @Test
    public void rookMoveRemovesCastlingLongPossibility() throws IllegalMoveException
    {
        Position board = BoardParser.algebraic("Ra1 Ke1 kh8 w");
        board = board.setCanCastleLong(true, Color.WHITE);
        board = Mover.doMove(board, "a1b1 h8g8 b1a1 g8h8");
        assertTrue(board.isWhiteInMove());
        assertFalse(board.canCastleLong());
    }

    @Test
    public void rookMoveRemovesCastlingShortPossibility() throws IllegalMoveException
    {
        Position board = BoardParser.algebraic("Rh1 Ke1 ka8 w");
        board = board.setCanCastleShort(true, Color.WHITE);
        board = Mover.doMove(board, "h1g1 a8b8 g1h1 b8a8");
        assertTrue(board.isWhiteInMove());
        assertFalse(board.canCastleShort());
    }
}
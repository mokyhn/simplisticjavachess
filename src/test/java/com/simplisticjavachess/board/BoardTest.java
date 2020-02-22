package com.simplisticjavachess.board;

import com.simplisticjavachess.piece.Color;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest
{
    @Test
    public void rookMoveRemovesCastlingLongPossibility()
    {
        Board board = Board.createFromLetters("Ra1 Ke1 kh8 w");
        board = board.setCanCastleLong(true, Color.WHITE);
        MoveResult moveResult = board.doMove("a1b1 h8g8 b1a1 g8h8");
        board = moveResult.getBoard();
        assertTrue(board.isWhiteInMove());
        assertFalse(board.canCastleLong());
    }

    @Test
    public void rookMoveRemovesCastlingShortPossibility()
    {
        Board board = Board.createFromLetters("Rh1 Ke1 ka8 w");
        board = board.setCanCastleShort(true, Color.WHITE);
        System.out.println(board.asASCII());
        MoveResult moveResult = board.doMove("h1g1 a8b8 g1h1 b8a8");
        board = moveResult.getBoard();
        assertTrue(board.isWhiteInMove());
        assertFalse(board.canCastleShort());
    }
}
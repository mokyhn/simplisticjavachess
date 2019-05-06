package com.simplisticjavachess.movegenerator;

import com.simplisticjavachess.board.Board;
import com.simplisticjavachess.misc.IteratorUtils;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.piece.Piece;
import java.util.Iterator;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class PawnMoveGeneratorTest
{
    
    @Test
    public void testMoveFromHome()
    {
        Board board = Board.createFromLetters("Pa2 w");
        Iterator<Move> moves = new PawnMoveGenerator().generateMoves(board, Piece.fromPositionCode("Pa2"));
        String movesStr = IteratorUtils.toString(moves);
        assertEquals("[a2-a3, a2-a4]", movesStr);
    }

    @Test
    public void testNormalMove()
    {
        Board board = Board.createFromLetters("Pd4 w");
        Iterator<Move> moves = new PawnMoveGenerator().generateMoves(board, Piece.fromPositionCode("Pd4"));
        String movesStr = IteratorUtils.toString(moves);
        assertEquals("[d4-d5]", movesStr);
    }

    @Test
    public void testPromotion()
    {
        Board board = Board.createFromLetters("Pd7 w");
        System.out.println(board.asASCII());
        Iterator<Move> moves = new PawnMoveGenerator().generateMoves(board, Piece.fromPositionCode("Pd7"));
        String movesStr = IteratorUtils.toString(moves);
        assertEquals("[d7-d8=Q, d7-d8=R, d7-d8=N, d7-d8=B]", movesStr);
    }



}

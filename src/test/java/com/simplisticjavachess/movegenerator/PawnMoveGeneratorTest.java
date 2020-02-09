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
        Iterator<Move> moves = new PawnMoveGenerator().generateMoves(board, Piece.parse("Pa2"));
        String movesStr = IteratorUtils.toString(moves);
        assertEquals("[a2-a3, a2-a4]", movesStr);
    }

    @Test
    public void testNormalMove()
    {
        Board board = Board.createFromLetters("Pd4 w");
        Iterator<Move> moves = new PawnMoveGenerator().generateMoves(board, Piece.parse("Pd4"));
        String movesStr = IteratorUtils.toString(moves);
        assertEquals("[d4-d5]", movesStr);
    }

    @Test
    public void testPromotion()
    {
        Board board = Board.createFromLetters("Pd7 w");
        System.out.println(board.asASCII());
        Iterator<Move> moves = new PawnMoveGenerator().generateMoves(board, Piece.parse("Pd7"));
        String movesStr = IteratorUtils.toString(moves);
        assertEquals("[d7-d8=Q, d7-d8=R, d7-d8=N, d7-d8=B]", movesStr);
    }


    @Test
    public void testCapturingPromotion()
    {
        Board board = Board.createFromLetters("Pd7 rd8 rc8 re8 w");
        System.out.println(board.asASCII());
        Iterator<Move> moves = new PawnMoveGenerator().generateMoves(board, Piece.parse("Pd7"));
        String movesStr = IteratorUtils.toString(moves);
        assertEquals("[d7xc8=B, d7xc8=N, d7xc8=Q, d7xc8=R, d7xe8=B, d7xe8=N, d7xe8=Q, d7xe8=R]", movesStr);
    }


    @Test
    public void testNormalCapturing()
    {
        Board board = Board.createFromLetters("Pd4 Pd5 pc5 pe5 w");
        System.out.println(board.asASCII());
        Iterator<Move> moves = new PawnMoveGenerator().generateMoves(board, Piece.parse("Pd4"));
        String movesStr = IteratorUtils.toString(moves);
        assertEquals("[d4xc5, d4xe5]", movesStr);
    }

}

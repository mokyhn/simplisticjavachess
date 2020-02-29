package com.simplisticjavachess.movegenerator;

import com.simplisticjavachess.board.Board;
import com.simplisticjavachess.board.BoardParser;
import com.simplisticjavachess.board.MoveResult;
import com.simplisticjavachess.misc.IteratorUtils;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.move.MoveParser;
import com.simplisticjavachess.piece.Piece;
import java.util.Iterator;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class PawnMoveGeneratorTest
{
    
    @Test
    public void testMoveFromHome()
    {
        Board board = BoardParser.algebraic("Pa2 w");
        Iterator<Move> moves = new PawnMoveGenerator().generateMoves(board, Piece.parse("Pa2"));
        String movesStr = IteratorUtils.toString(moves);
        assertEquals("[a2-a3, a2-a4]", movesStr);
    }

    @Test
    public void testNormalMove()
    {
        Board board = BoardParser.algebraic("Pd4 w");
        Iterator<Move> moves = new PawnMoveGenerator().generateMoves(board, Piece.parse("Pd4"));
        String movesStr = IteratorUtils.toString(moves);
        assertEquals("[d4-d5]", movesStr);
    }

    @Test
    public void testPromotion()
    {
        Board board = BoardParser.algebraic("Pd7 w");
        System.out.println(board.asASCII());
        Iterator<Move> moves = new PawnMoveGenerator().generateMoves(board, Piece.parse("Pd7"));
        String movesStr = IteratorUtils.toString(moves);
        assertEquals("[d7-d8=Q, d7-d8=R, d7-d8=N, d7-d8=B]", movesStr);
    }


    @Test
    public void testCapturingPromotion()
    {
        Board board = BoardParser.algebraic("Pd7 rd8 rc8 re8 w");
        System.out.println(board.asASCII());
        Iterator<Move> moves = new PawnMoveGenerator().generateMoves(board, Piece.parse("Pd7"));
        String movesStr = IteratorUtils.toString(moves);
        assertEquals("[d7xc8=B, d7xc8=N, d7xc8=Q, d7xc8=R, d7xe8=B, d7xe8=N, d7xe8=Q, d7xe8=R]", movesStr);
    }


    @Test
    public void testNormalCapturing()
    {
        Board board = BoardParser.algebraic("Pd4 Pd5 pc5 pe5 w");
        System.out.println(board.asASCII());
        Iterator<Move> moves = new PawnMoveGenerator().generateMoves(board, Piece.parse("Pd4"));
        String movesStr = IteratorUtils.toString(moves);
        assertEquals("[d4xc5, d4xe5]", movesStr);
    }

    @Test
    public void testEnpassantLeft()
    {
        Board board = BoardParser.algebraic("ka1 Kh8 Pd2 pc4  w");
        MoveResult moveResult = board.doMove(MoveParser.parse(board,"d2d4"));
        board = moveResult.getBoard();
        System.out.println(board.asASCII());
        Iterator<Move> moves = new PawnMoveGenerator().generateMoves(board, Piece.parse("pc4"));
        String movesStr = IteratorUtils.toString(moves);
        assertEquals("[c4-c3, c4xd3]", movesStr);
    }

    @Test
    public void testEnpassantRight()
    {
        Board board = BoardParser.algebraic("ka1 Kh8 Pd2 pe4  w");
        MoveResult moveResult = board.doMove(MoveParser.parse(board,"d2d4"));
        board = moveResult.getBoard();
        System.out.println(board.asASCII());
        Iterator<Move> moves = new PawnMoveGenerator().generateMoves(board, Piece.parse("pe4"));
        String movesStr = IteratorUtils.toString(moves);
        assertEquals("[e4-e3, e4xd3]", movesStr);
    }

    @Test
    public void lostEnpassantRight()
    {
        Board board = BoardParser.algebraic("ka1 Kh8 Pd2 pe4  w");
        MoveResult moveResult = board.doMove(MoveParser.parse(board,"d2d4"));
        board = moveResult.getBoard();
        moveResult = board.doMove(MoveParser.parse(board,"a1b1"));
        board = moveResult.getBoard();
        moveResult = board.doMove(MoveParser.parse(board,"h8g8"));
        board = moveResult.getBoard();
        System.out.println(board.asASCII());
        Iterator<Move> moves = new PawnMoveGenerator().generateMoves(board, Piece.parse("pe4"));
        String movesStr = IteratorUtils.toString(moves);
        assertEquals("[e4-e3]", movesStr);
    }

}

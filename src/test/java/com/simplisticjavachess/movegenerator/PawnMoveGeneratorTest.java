package com.simplisticjavachess.movegenerator;

import com.simplisticjavachess.board.BoardParser;
import com.simplisticjavachess.board.MoveResult;
import com.simplisticjavachess.board.Mover;
import com.simplisticjavachess.board.Position;
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
        Position board = BoardParser.algebraic("Pa2 w");
        Iterator<Move> moves = new PawnMoveGenerator().generateMoves(board, Piece.parse("Pa2"));
        String movesStr = IteratorUtils.toString(moves);
        assertEquals("[a2-a3, a2-a4]", movesStr);
    }

    @Test
    public void testNormalMove()
    {
        Position board = BoardParser.algebraic("Pd4 w");
        Iterator<Move> moves = new PawnMoveGenerator().generateMoves(board, Piece.parse("Pd4"));
        String movesStr = IteratorUtils.toString(moves);
        assertEquals("[d4-d5]", movesStr);
    }

    @Test
    public void testPromotion()
    {
        Position board = BoardParser.algebraic("Pd7 w");
        Iterator<Move> moves = new PawnMoveGenerator().generateMoves(board, Piece.parse("Pd7"));
        String movesStr = IteratorUtils.toString(moves);
        assertEquals("[d7-d8=Q, d7-d8=R, d7-d8=N, d7-d8=B]", movesStr);
    }


    @Test
    public void testCapturingPromotion()
    {
        Position board = BoardParser.algebraic("Pd7 rd8 rc8 re8 w");
        Iterator<Move> moves = new PawnMoveGenerator().generateMoves(board, Piece.parse("Pd7"));
        String movesStr = IteratorUtils.toString(moves);
        assertEquals("[d7xc8=B, d7xc8=N, d7xc8=Q, d7xc8=R, d7xe8=B, d7xe8=N, d7xe8=Q, d7xe8=R]", movesStr);
    }


    @Test
    public void testNormalCapturing()
    {
        Position board = BoardParser.algebraic("Pd4 Pd5 pc5 pe5 w");
        Iterator<Move> moves = new PawnMoveGenerator().generateMoves(board, Piece.parse("Pd4"));
        String movesStr = IteratorUtils.toString(moves);
        assertEquals("[d4xc5, d4xe5]", movesStr);
    }

    @Test
    public void testEnpassantLeft()
    {
        Position board = BoardParser.algebraic("ka1 Kh8 Pd2 pc4  w");
        MoveResult moveResult = Mover.doMove(board,MoveParser.parse(board, "d2d4"));
        board = moveResult.getPosition();
        Iterator<Move> moves = new PawnMoveGenerator().generateMoves(board, Piece.parse("pc4"));
        String movesStr = IteratorUtils.toString(moves);
        assertEquals("[c4-c3, c4xd3]", movesStr);
    }

    @Test
    public void testEnpassantRight()
    {
        Position board = BoardParser.algebraic("ka1 Kh8 Pd2 pe4  w");
        MoveResult moveResult = Mover.doMove(board, MoveParser.parse(board,"d2d4"));
        board = moveResult.getPosition();
        Iterator<Move> moves = new PawnMoveGenerator().generateMoves(board, Piece.parse("pe4"));
        String movesStr = IteratorUtils.toString(moves);
        assertEquals("[e4-e3, e4xd3]", movesStr);
    }

    @Test
    public void lostEnpassantRight()
    {
        Position board = BoardParser.algebraic("ka1 Kh8 Pd2 pe4  w");
        MoveResult moveResult = Mover.doMove(board, MoveParser.parse(board,"d2d4"));
        board = moveResult.getPosition();
        moveResult = Mover.doMove(board, MoveParser.parse(board,"a1b1"));
        board = moveResult.getPosition();
        moveResult = Mover.doMove(board, MoveParser.parse(board,"h8g8"));
        board = moveResult.getPosition();
        Iterator<Move> moves = new PawnMoveGenerator().generateMoves(board, Piece.parse("pe4"));
        String movesStr = IteratorUtils.toString(moves);
        assertEquals("[e4-e3]", movesStr);
    }

}

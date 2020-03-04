package com.simplisticjavachess.movegenerator;

import com.simplisticjavachess.board.BoardParser;
import com.simplisticjavachess.board.Position;
import com.simplisticjavachess.board.Vector;
import com.simplisticjavachess.misc.IteratorUtils;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.piece.Piece;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author Morten Kühnrich
 */
public class LineMoveGeneratorTest
{
    
    @Test
    public void testGenerateMovesUpRight()
    {         
        Position position = BoardParser.algebraic("Bd5 w");
        Piece piece = Piece.parse("Bd5");

        List<Move> result = IteratorUtils.toList(new LineMoveGenerator(new Vector(1, 1)).generateMoves(position, piece));
        assertEquals(3, result.size()); // e6, f7, g8
        assertTrue(result.toString().contains("d5-e6"));
        assertTrue(result.toString().contains("d5-f7"));
        assertTrue(result.toString().contains("d5-g8"));
    }
    
    @Test
    public void testGenerateMovesDown()
    {
        Position board = BoardParser.algebraic("Rd5 w");
        Piece piece = Piece.parse("Rd5");

        List<Move> result = IteratorUtils.toList(new LineMoveGenerator(new Vector(0, -1)).generateMoves(board, piece));
        assertEquals(4, result.size());
        assertTrue(result.toString().contains("d5-d4"));
        assertTrue(result.toString().contains("d5-d3"));
        assertTrue(result.toString().contains("d5-d2"));
        assertTrue(result.toString().contains("d5-d1"));
    }
    
    
    @Test
    public void testGenerateMovesLeft()
    {
        Position board = BoardParser.algebraic("Rd5 w");
        Piece piece = Piece.parse("Rd5");

        List<Move> result = IteratorUtils.toList(new LineMoveGenerator(new Vector(-1, 0)).generateMoves(board, piece));
        assertEquals(3, result.size());
        assertTrue(result.toString().contains("d5-c5"));
        assertTrue(result.toString().contains("d5-b5"));
        assertTrue(result.toString().contains("d5-a5"));
    }

    @Test
    public void testGenerateMovesLeftBlockingPiece()
    {
        Position board = BoardParser.algebraic("Rd5 Pb5 w");
        Piece piece = Piece.parse("Rd5");

        List<Move> result = IteratorUtils.toList(new LineMoveGenerator(new Vector(-1, 0)).generateMoves(board, piece));
        assertEquals(1, result.size());
        assertTrue(result.toString().contains("d5-c5"));
    }
    
    @Test
    public void testGenerateDownRightAndCapture()
    {
        Position board = BoardParser.algebraic("Bc5 pe3 pf2 w");
        Piece piece = Piece.parse("Bc5");

        List<Move> result = IteratorUtils.toList(new LineMoveGenerator(new Vector(1, -1)).generateMoves(board, piece));
        assertEquals(2, result.size());
        assertTrue(result.toString().contains("c5-d4"));
        assertTrue(result.toString().contains("c5xe3"));        
    }
    
}

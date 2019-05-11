package com.simplisticjavachess.movegenerator;

import com.simplisticjavachess.board.Board;
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
        Board b = new Board();
        
        Piece p = Piece.fromPositionCode("Bd5");
        b.insert(p);
        b.setWhiteToMove();
        List<Move> result = IteratorUtils.toList(new LineMoveGenerator(new Vector(1, 1)).generateMoves(b, p));
        assertEquals(3, result.size()); // e6, f7, g8
        assertTrue(result.toString().contains("d5-e6"));
        assertTrue(result.toString().contains("d5-f7"));
        assertTrue(result.toString().contains("d5-g8"));
    }
    
    @Test
    public void testGenerateMovesDown()
    {         
        Board b = new Board();
        
        Piece p = Piece.fromPositionCode("Rd5");
        b.insert(p);
        b.setWhiteToMove();
        List<Move> result = IteratorUtils.toList(new LineMoveGenerator(new Vector(0, -1)).generateMoves(b, p));
        assertEquals(4, result.size());
        assertTrue(result.toString().contains("d5-d4"));
        assertTrue(result.toString().contains("d5-d3"));
        assertTrue(result.toString().contains("d5-d2"));
        assertTrue(result.toString().contains("d5-d1"));
    }
    
    
    @Test
    public void testGenerateMovesLeft()
    {         
        Board b = new Board();
        
        Piece p = Piece.fromPositionCode("Rd5");        
        b.insert(p);
        
        b.setWhiteToMove();
        List<Move> result = IteratorUtils.toList(new LineMoveGenerator(new Vector(-1, 0)).generateMoves(b, p));
        assertEquals(3, result.size());
        assertTrue(result.toString().contains("d5-c5"));
        assertTrue(result.toString().contains("d5-b5"));
        assertTrue(result.toString().contains("d5-a5"));
    }
    
    @Test
    public void testGenerateMovesLeftBlockingPiece()
    {         
        Board b = new Board();
        
        Piece p = Piece.fromPositionCode("Rd5");        
        b.insert(p);
        
        // A blocking piece
        b.insert(Piece.fromPositionCode("Pb5"));
        
        b.setWhiteToMove();
        List<Move> result = IteratorUtils.toList(new LineMoveGenerator(new Vector(-1, 0)).generateMoves(b, p));
        assertEquals(1, result.size());
        assertTrue(result.toString().contains("d5-c5"));
    }
    
    @Test
    public void testGenerateDownRightAndCapture()
    {         
        Board b = new Board();
        
        Piece p = Piece.fromPositionCode("Bc5");        
        b.insert(p);
        
        // A piece that can be captured
        b.insert(Piece.fromPositionCode("pe3"));
        
        // Should not be captured
        b.insert(Piece.fromPositionCode("pf2"));
        
        b.setWhiteToMove();
        List<Move> result = IteratorUtils.toList(new LineMoveGenerator(new Vector(1, -1)).generateMoves(b, p));
        assertEquals(2, result.size());
        assertTrue(result.toString().contains("c5-d4"));
        assertTrue(result.toString().contains("c5xe3"));        
    }
    
}
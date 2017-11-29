package com.simplisticjavachess.movegenerator;

import com.simplisticjavachess.board.Board;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.piece.Piece;
import java.util.ArrayList;
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
        
        Piece p = Piece.fromPositionCode("B d5");
        b.insertPiece(p);
        b.setWhiteToMove();
        ArrayList<Move> result = LineMoveGenerator.generateMoves(b, p, 1, 1);
        assertEquals(3, result.size()); // e6, f7, g8
        assertTrue(result.toString().contains("d5-e6"));
        assertTrue(result.toString().contains("d5-f7"));
        assertTrue(result.toString().contains("d5-g8"));
    }
    
    @Test
    public void testGenerateMovesDown()
    {         
        Board b = new Board();
        
        Piece p = Piece.fromPositionCode("R d5");
        b.insertPiece(p);
        b.setWhiteToMove();
        ArrayList<Move> result = LineMoveGenerator.generateMoves(b, p, 0, -1);
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
        
        Piece p = Piece.fromPositionCode("R d5");        
        b.insertPiece(p);              
        
        b.setWhiteToMove();
        ArrayList<Move> result = LineMoveGenerator.generateMoves(b, p, -1, 0);
        assertEquals(3, result.size());
        assertTrue(result.toString().contains("d5-c5"));
        assertTrue(result.toString().contains("d5-b5"));
        assertTrue(result.toString().contains("d5-a5"));
    }
    
    @Test
    public void testGenerateMovesLeftBlockingPiece()
    {         
        Board b = new Board();
        
        Piece p = Piece.fromPositionCode("R d5");        
        b.insertPiece(p);
        
        // A blocking piece
        b.insertPiece(Piece.fromPositionCode("P b5"));
        
        b.setWhiteToMove();
        ArrayList<Move> result = LineMoveGenerator.generateMoves(b, p, -1, 0);
        assertEquals(1, result.size());
        assertTrue(result.toString().contains("d5-c5"));
    }
    
    @Test
    public void testGenerateDownRightAndCapture()
    {         
        Board b = new Board();
        
        Piece p = Piece.fromPositionCode("B c5");        
        b.insertPiece(p);              
        
        // A piece that can be captured
        b.insertPiece(Piece.fromPositionCode("p e3"));
        
        // Should not be captured
        b.insertPiece(Piece.fromPositionCode("p f2"));
        
        b.setWhiteToMove();
        ArrayList<Move> result = LineMoveGenerator.generateMoves(b, p, 1, -1);
        assertEquals(2, result.size());
        assertTrue(result.toString().contains("c5-d4"));
        assertTrue(result.toString().contains("c5xe3"));        
    }
    
}

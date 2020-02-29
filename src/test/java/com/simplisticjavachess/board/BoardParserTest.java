package com.simplisticjavachess.board;

import com.simplisticjavachess.piece.Color;
import com.simplisticjavachess.piece.Piece;
import com.simplisticjavachess.piece.PieceType;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;


public class BoardParserTest
{
    private Board board1;
    
    @Before
    public void before()
    {
        board1 = BoardParser.algebraic("pe4 Bd5 qh1 b");
    }
    
    @Test
    public void testImportExportPosition_1()
    {
        String fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w";
        Board board = BoardParser.FEN(fen);
        String result = BoardParser.exportPosition(board);
        assertEquals(fen, result);
    }
    
    @Test
    public void testImportExportPosition_2()
    {
        String fen = "rnb5/pp4pp/8/8/8/8/PP2P2P/2BQKB2 w";
        Board board = BoardParser.FEN(fen);
        String result = BoardParser.exportPosition(board);
        assertEquals(fen, result);
    }
    
    @Test
    public void testImportExportPosition_3()
    {
        String fen = "8/pp4pp/8/8/8/8/PP2P2P/8 b";
        Board board = BoardParser.FEN(fen);
        String result = BoardParser.exportPosition(board);
        assertEquals(fen, result);
    }

    @Test
    public void testImportExportPosition_Many()
    {
        for (String test_position : FENPositions.POSITIONS)
        {
            Board board = BoardParser.FEN(test_position);
            String result = BoardParser.exportPosition(board);
            assertEquals(test_position, result);
        }
    }
    
    @Test
    public void testImportPosition()
    {
        String test_position = "7k/8/7K/2q5/1P6/8/8/5R2 w - -";
        Board board = BoardParser.FEN(test_position);
        Piece piece1 = board.getPiece(7, 7);
        Piece piece2 = board.getPiece(7, 5);
        assertEquals(Color.BLACK, piece1.getColor());
        assertEquals(Color.WHITE, piece2.getColor());
        assertEquals(PieceType.KING, piece1.getPieceType());
        assertEquals(PieceType.KING, piece2.getPieceType());
    }
    
    @Test
    public void testImportFromPieceListMoveColor()
    {
        assertFalse(board1.isWhiteInMove());
    }
    
    @Test
    public void testImportFromPieceListNumberOfPieces()
    {
        assertEquals(3, board1.getPieces().size());
    }
            
    @Test
    public void testImportFromPieceListActualPieces()
    {
        assertEquals("pe4", board1.getPiece(Location.parse("e4")).asString());
        assertEquals("Bd5", board1.getPiece(Location.parse("d5")).asString());
        assertEquals("qh1", board1.getPiece(Location.parse("h1")).asString());
    }

}

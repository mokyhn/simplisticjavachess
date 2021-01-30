package com.simplisticjavachess.position;

import com.simplisticjavachess.piece.Color;
import com.simplisticjavachess.piece.Piece;
import com.simplisticjavachess.piece.PieceType;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;


public class PositionIOTest
{
    private Position board1;

    @Before
    public void before()
    {
        board1 = PositionIO.algebraic("pe4 Bd5 qh1 b");
    }

    @Test
    public void testImportExportPosition_1()
    {
        String fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w";
        Position board = PositionIO.FEN(fen);
        String result = PositionIO.exportPosition(board);
        assertEquals(fen, result);
    }

    @Test
    public void testImportExportPosition_2()
    {
        String fen = "rnb5/pp4pp/8/8/8/8/PP2P2P/2BQKB2 w";
        Position board = PositionIO.FEN(fen);
        String result = PositionIO.exportPosition(board);
        assertEquals(fen, result);
    }

    @Test
    public void testImportExportPosition_3()
    {
        String fen = "8/pp4pp/8/8/8/8/PP2P2P/8 b";
        Position board = PositionIO.FEN(fen);
        String result = PositionIO.exportPosition(board);
        assertEquals(fen, result);
    }

    @Test
    public void testImportExportPosition_Many()
    {
        for (String test_position : FENPositions.POSITIONS)
        {
            Position board = PositionIO.FEN(test_position);
            String result = PositionIO.exportPosition(board);
            assertEquals(test_position, result);
        }
    }

    @Test
    public void testImportPosition()
    {
        String test_position = "7k/8/7K/2q5/1P6/8/8/5R2 w - - 0 0";
        Position board = PositionIO.FEN(test_position);
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

    @Test
    public void testImportWithEnpassant1()
    {
        Position position = PositionIO.FEN("rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1");
        assertEquals("e2-e4", position.getEnpassantMove().get().toString());
    }

    @Test
    public void testImportWithEnpassant2()
    {
        Position position = PositionIO.FEN("rnbqkbnr/pp1ppppp/8/2p5/4P3/8/PPPP1PPP/RNBQKBNR w KQkq c6 0 2");
        assertEquals("c7-c5", position.getEnpassantMove().get().toString());

    }
}




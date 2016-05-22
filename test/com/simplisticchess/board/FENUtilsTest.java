package com.simplisticchess.board;

import org.junit.Test;
import static org.junit.Assert.*;


public class FENUtilsTest
{
    @Test
    public void testImportExportPosition_1()
    {
        String test_position = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w";
        Board board = new Board();
        FENUtils.importPosition(board, test_position);
        String result = FENUtils.exportPosition(board);
        assertEquals(test_position, result);
    }
    
    @Test
    public void testImportExportPosition_2()
    {
        String test_position = "rnb5/pp4pp/8/8/8/8/PP2P2P/2BQKB2 w";
        Board board = new Board();
        FENUtils.importPosition(board, test_position);
        String result = FENUtils.exportPosition(board);
        assertEquals(test_position, result);
    }
    
    @Test
    public void testImportExportPosition_3()
    {
        String test_position = "8/pp4pp/8/8/8/8/PP2P2P/8 b";
        Board board = new Board();
        FENUtils.importPosition(board, test_position);
        String result = FENUtils.exportPosition(board);
        assertEquals(test_position, result);
    }
    
    @Test
    public void testImportExportPosition_Many()
    {
        for (String test_position : FENPositions.POSITIONS)
        {
            Board board = new Board();
            FENUtils.importPosition(board, test_position);
            String result = FENUtils.exportPosition(board);
            assertEquals(test_position, result);
        }
    }    
}

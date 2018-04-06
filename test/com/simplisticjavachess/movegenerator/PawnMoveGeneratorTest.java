package com.simplisticjavachess.movegenerator;

import com.simplisticjavachess.board.Board;
import com.simplisticjavachess.misc.IteratorUtils;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.piece.Piece;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class PawnMoveGeneratorTest
{
    
    @Test
    public void testLeftFlank()
    {
        Board board = Board.createFromLetters("pa2 w");
        Iterator<Move> moves = PawnMoveGenerator.getIterator(board, Piece.fromPositionCode("pa2"));
        List<Move> movesList = IteratorUtils.toList(moves);
        
        // Two moves in total generated
        assertEquals(movesList.size(), 2);
        
        String movesStr = Arrays.toString(movesList.toArray());
        
        // Validate one move
        assertTrue(movesStr, movesStr.contains("a2-a4"));
        
        // Validate one move
        assertTrue(movesStr, movesStr.contains("a2-a3"));
        
    }

}

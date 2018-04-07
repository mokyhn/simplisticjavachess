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
    public void testLeftFlank()
    {
        Board board = Board.createFromLetters("pa2 w");
        Iterator<Move> moves = PawnMoveGenerator.getIterator(board, Piece.fromPositionCode("pa2"));     
        String movesStr = IteratorUtils.toString(moves);
                
        assertEquals("[a2-a3, a2-a4]", movesStr);
    }

}

/**
 *
 * @author Morten Kühnrich
 */

package com.simplisticchess.movegenerator;

import com.simplisticchess.board.Board;
import com.simplisticchess.board.FENPositions;
import com.simplisticchess.board.FENUtils;
import com.simplisticchess.board.PositionInference;
import com.simplisticchess.move.Move;
import com.simplisticchess.move.Moves;
import com.simplisticchess.piece.Color;
import com.simplisticchess.piece.Piece;
import com.simplisticchess.piece.PieceType;
import com.simplisticchess.position.Location;
import java.util.Iterator;
import org.junit.Test;
import static org.junit.Assert.*;

public class MoveGeneratorTest
{
    @Test
    public void testGenerateMoves()
    {
        MoveGenerator moveGenerator = new MoveGenerator();
        
        for (String fen : FENPositions.POSITIONS) 
        {
            Board board = new Board(fen);
            
            Iterator<Move> moves = moveGenerator.generateMoves(board);
            
            while (moves.hasNext())
            {
                
                Move move = moves.next();
                
                Location from = move.getFrom();
                
                Piece piece = board.getPiece(from);
                
 
                if (piece.getPieceType() == PieceType.PAWN)
                {
                    if (move.aCapture()) 
                    {
                        assertTrue(FENUtils.exportPosition(board), PositionInference.attacks(board.getPosition(), move.getTo(), board.inMove().opponent()));
                    }
                }
                else
                {
                    assertTrue(FENUtils.exportPosition(board), PositionInference.attacks(board.getPosition(), move.getTo(), board.inMove().opponent()));
                }
                
            }
            
        }
        
    }
    
}

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
import com.simplisticchess.piece.Piece;
import com.simplisticchess.piece.PieceType;
import com.simplisticchess.position.Location;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class MoveGeneratorTest
{
    @Test
    public void testGeneratedMovesAlsoAttacks()
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
                        assertTrue(FENUtils.exportPosition(board), PositionInference.attacks(board.getPosition(), move.getTo(), board.inMove().opponent()) != null);
                    }
                }
                else
                {
                    assertTrue(FENUtils.exportPosition(board), PositionInference.attacks(board.getPosition(), move.getTo(), board.inMove().opponent()) != null);
                } 
                
            }
            
        }        
    }
    
    @Test
    public void attackedLocationsAlsoHasMovesThatAttacks()
    {
        MoveGenerator moveGenerator = new MoveGenerator();
        int i = 0;
        for (String fen : FENPositions.POSITIONS) 
        {
            i++;
            Board originalBoard = new Board(fen);
            Board board = new Board(originalBoard);
            Iterator<Move> moveIterator = moveGenerator.generateMoves(board);
            List<Move> moves = new ArrayList<Move>();
            while (moveIterator.hasNext())
            {
                moves.add(moveIterator.next());
            }
            
            for (int x = 0; x < 8; x++)
            {
                for (int y = 0; y < 8; y++)
                {
                    Location location = new Location(x,y);
                    
                    Piece attacker = PositionInference.attacks(board.getPosition(), location, board.inMove().opponent());                                     
                    if (attacker != null && attacker.getPieceType() != PieceType.KING &&
                            attacker.getPieceType() != PieceType.PAWN && 
                            board.getPiece(location) != null && 
                            board.getPiece(location).getColor() ==  board.inMove().opponent())
                    {
                        boolean found = false;
                        for (Move move : moves)
                        {
                            if (move.getTo().equals(location))
                            {
                                found = true;
                                break;
                            } 
                        }                        
                        assertTrue("i= "+i + "   Location = " + location + "\n" + board.getASCIIBoard(), found);
                    }
                }
                
            }
            
        }
    }
    
}

/**
 *
 * @author Morten Kühnrich
 */

package com.simplisticjavachess.movegenerator;

import com.simplisticjavachess.position.PositionIO;
import com.simplisticjavachess.position.FENPositions;
import com.simplisticjavachess.position.Location;
import com.simplisticjavachess.position.Position;
import com.simplisticjavachess.position.PositionInference;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.piece.Piece;
import com.simplisticjavachess.piece.PieceType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class MainMoveGeneratorTest
{
    @Test
    public void testGeneratedMovesAlsoAttacks()
    {
        MainMoveGenerator moveGenerator = new MainMoveGenerator();
        
        for (String fen : FENPositions.POSITIONS) 
        {
            Position board = PositionIO.FEN(fen);
            
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
                        assertNotNull(PositionIO.exportPosition(board), PositionInference.attacks(board, move.getTo(), board.inMove().opponent()));
                    }
                }
                else
                {
                    assertNotNull(PositionIO.exportPosition(board), PositionInference.attacks(board, move.getTo(), board.inMove().opponent()));
                } 
                
            }
            
        }        
    }
    
    @Test
    public void attackedLocationsAlsoHasMovesThatAttacks()
    {
        MainMoveGenerator moveGenerator = new MainMoveGenerator();
        int i = 0;
        for (String fen : FENPositions.POSITIONS) 
        {
            i++;
            Position originalBoard = PositionIO.FEN(fen);
            Position board = originalBoard;
            
            Iterator<Move> moveIterator = moveGenerator.generateMoves(board);
            
            List<Move> moves = new ArrayList<>();
            while (moveIterator.hasNext())
            {
                moves.add(moveIterator.next());
            }
            
            for (int x = 0; x < 8; x++)
            {
                for (int y = 0; y < 8; y++)
                {
                    Location location = new Location(x,y);
                    
                    Piece attacker = PositionInference.attacks(board, location, board.inMove().opponent());
                    if (attacker != null && 
                        attacker.getPieceType() != PieceType.KING &&
                        attacker.getPieceType() != PieceType.PAWN && 
                        attacker.getPieceType() != PieceType.QUEEN &&
                        attacker.getPieceType() != PieceType.ROOK &&
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
                        assertTrue("i= "+i + "   Location = " + location + " HEJ "+ attacker.getPieceType().toString() + "\n" + board.toString(), found);
                    }
                }
                
            }
            
        }
    }
    
}

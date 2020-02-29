/**
 *
 * @author Morten Kühnrich
 */

package com.simplisticjavachess.movegenerator;

import com.simplisticjavachess.board.Board;
import com.simplisticjavachess.board.FENPositions;
import com.simplisticjavachess.board.BoardParser;
import com.simplisticjavachess.board.PositionInference;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.piece.Piece;
import com.simplisticjavachess.piece.PieceType;
import com.simplisticjavachess.board.Location;
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
            Board board = BoardParser.FEN(fen);
            
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
                        assertNotNull(BoardParser.exportPosition(board), PositionInference.attacks(board.getPosition(), move.getTo(), board.inMove().opponent()));
                    }
                }
                else
                {
                    assertNotNull(BoardParser.exportPosition(board), PositionInference.attacks(board.getPosition(), move.getTo(), board.inMove().opponent()));
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
            Board originalBoard = BoardParser.FEN(fen);
            Board board = originalBoard;
            
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
                    
                    Piece attacker = PositionInference.attacks(board.getPosition(), location, board.inMove().opponent());                                     
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
                        assertTrue("i= "+i + "   Location = " + location + " HEJ "+ attacker.getPieceType().toString() + "\n" + board.asASCII(), found);
                    }
                }
                
            }
            
        }
    }
    
}

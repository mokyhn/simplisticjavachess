/**
 *
 * @author Morten Kühnrich
 */

package com.simplisticchess.movegenerator;

import com.simplisticchess.board.Board;
import com.simplisticchess.move.Move;
import com.simplisticchess.piece.Piece;
import java.util.ArrayList;


public class QueenMoveGenerator
{
    BishopMoveGenerator bishopMoveGenerator;
    RookMoveGenerator rookMoveGenerator;
    
    public QueenMoveGenerator() 
    {
        bishopMoveGenerator = new BishopMoveGenerator();
        rookMoveGenerator = new RookMoveGenerator();
    }

    public ArrayList<Move> generateMoves(Board b, Piece p) 
    {
        ArrayList<Move> moves;
        moves = bishopMoveGenerator.generateMoves(b, p);
        moves.addAll(rookMoveGenerator.generateMoves(b, p));
        return moves;
    }

}

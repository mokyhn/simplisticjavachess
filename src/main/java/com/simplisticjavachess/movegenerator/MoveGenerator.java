package com.simplisticjavachess.movegenerator;

import com.simplisticjavachess.misc.IteratorUtils;
import com.simplisticjavachess.board.Board;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.piece.Color;
import com.simplisticjavachess.piece.Piece;
import java.util.ArrayList;
import java.util.Iterator;


//TODO: Turn this into an interface
//Define individual move generators
//Define the composition of move generators into a move generator
//This will allow us to test individual move generators also in combination with searching
//TODO: Even the internal structure of one move generator could be described via a composed move generator.
//The compose move-generator could handle the sub-structure of move generators in a clever way
public class MoveGenerator
{
    
    //TODO: This is probably the public interface!
    // Genereate the possible move iterator for one single piece
    private Iterator<Move> generateMoves(Board board, Piece piece)
    {
        final Color sideToMove = board.inMove();

        if (piece.getColor() != sideToMove)
        {
            return null;
        }

        switch (piece.getPieceType())
        {
            case PAWN:
                return PawnMoveGenerator.getIterator(board, piece);
            case KING:
                return KingMoveGenerator.getIterator(board, piece);
            case KNIGHT:
                return KnightMoveGenerator.getIterator(board, piece);
            case BISHOP:
                return BishopMoveGenerator.getIterator(board, piece);
            case ROOK:
                return RookMoveGenerator.iterator(board, piece);
            case QUEEN:
               return QueenMoveGenerator.getIterator(board, piece);
            default:
                return null; // Not reachable
        }        
    }

    //TODO: This is NOT the public interface! It will be slow
    public Iterator<Move> generateMoves(Board board)
    {
        final ArrayList<Iterator<Move>> moveIterators = new ArrayList<>();

        if (board.isDraw() || board.isMate())
        {
            return IteratorUtils.buildEmptyIterator();
        }
        
        for (Piece piece : board.getPieces())
        {            
            Iterator<Move> it = generateMoves(board, piece);
            if (it != null) 
            {
                moveIterators.add(it);
            }
        }

        return IteratorUtils.compose(moveIterators);
    }
  
}

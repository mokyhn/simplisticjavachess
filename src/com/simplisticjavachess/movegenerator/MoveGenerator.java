package com.simplisticjavachess.movegenerator;

import com.simplisticjavachess.misc.IteratorUtils;
import com.simplisticjavachess.board.Board;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.piece.Color;
import com.simplisticjavachess.piece.Piece;
import java.util.ArrayList;
import java.util.Iterator;



public class MoveGenerator
{
    
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

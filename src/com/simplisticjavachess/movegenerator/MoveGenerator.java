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
    private Iterator<Move> generateMoves(Board b, Piece p)
    {
        final Color sideToMove = b.inMove();

        if (p.getColor() != sideToMove)
        {
            return null;
        }

        switch (p.getPieceType())
        {
            case PAWN:
                return PawnMoveGenerator.getIterator(b, p);
            case KING:
                return KingMoveGenerator.getIterator(b, p);
            case KNIGHT:
                return KnightMoveGenerator.getIterator(b, p);
            case BISHOP:
                return BishopMoveGenerator.getIterator(b, p);
            case ROOK:
                return RookMoveGenerator.iterator(b, p);
            case QUEEN:
               return QueenMoveGenerator.getIterator(b, p);
            default:
                return null; // Not reachable
        }        
    }
    
    public Iterator<Move> generateMoves(Board board)
    {
        final ArrayList<Iterator<Move>> moveIterators = new ArrayList<Iterator<Move>>();

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

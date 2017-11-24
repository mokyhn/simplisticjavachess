package com.simplisticjavachess.movegenerator;

import com.simplisticjavachess.board.Board;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.piece.Color;
import com.simplisticjavachess.piece.Piece;
import java.util.ArrayList;
import java.util.Iterator;



public class MoveGenerator
{
    
    PawnMoveGenerator pawnMoveGenerator;
    KingMoveGenerator kingMoveGenerator;
    KnightMoveGenerator knightMoveGenerator;
    BishopMoveGenerator bishopMoveGenerator;
    RookMoveGenerator rookMoveGenerator;
    QueenMoveGenerator queenMoveGenerator;

    public MoveGenerator()
    {
        pawnMoveGenerator = new PawnMoveGenerator();
        kingMoveGenerator = new KingMoveGenerator();
        knightMoveGenerator = new KnightMoveGenerator();
        bishopMoveGenerator = new BishopMoveGenerator();
        rookMoveGenerator = new RookMoveGenerator();
        queenMoveGenerator = new QueenMoveGenerator();
    }

  
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
                return pawnMoveGenerator.iterator(b, p);
            case KING:
                return kingMoveGenerator.iterator(b, p);
            case KNIGHT:
                return knightMoveGenerator.iterator(b, p);
            case BISHOP:
                return bishopMoveGenerator.iterator(b, p);
            case ROOK:
                return rookMoveGenerator.iterator(b, p);
            case QUEEN:
               return queenMoveGenerator.iterator(b, p);
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

// TODO: Eliminatate generation of a number of moves when king is in check...
// The king is not allowed to be in check while another piece is moved...
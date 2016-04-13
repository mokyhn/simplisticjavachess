package com.simplisticchess.movegenerator;

import com.simplisticchess.board.Board;
import com.simplisticchess.move.Move;
import com.simplisticchess.piece.Color;
import com.simplisticchess.piece.Piece;
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


    private ArrayList<Move> generateAllMoves(Board b)
    {
        final ArrayList<Move> moves = new ArrayList<Move>();
        ArrayList<Move> r;
        Piece p;

        if (b.isDraw() || b.isMate())
        {
            return moves; // I.e. no moves to generate...
        }
        for (int i = 0; i < b.getNumberOfPieces(); i++)
        {
            p = b.getPiece(i);
            r = generateMoves(b, p);
            if (r != null && !r.isEmpty())
            {
                moves.addAll(r);
            }
        }

        return moves;
    }
    
    // Genereate the possible moves for one single piece
    private ArrayList<Move> generateMoves(Board b, Piece p)
    {
        ArrayList<Move> Moves = new ArrayList<Move>();
        final Color sideToMove = b.inMove();

        if (p.getColor() != sideToMove)
        {
            return Moves;
        }

        switch (p.getPieceType())
        {
            case PAWN:
                return pawnMoveGenerator.generateMoves(b, p);
            case KING:
                return kingMoveGenerator.generateMoves(b, p);
            case KNIGHT:
                return knightMoveGenerator.generateMoves(b, p);
            case BISHOP:
                return bishopMoveGenerator.generateMoves(b, p);
            case ROOK:
                return rookMoveGenerator.generateMoves(b, p);
            case QUEEN:
               return queenMoveGenerator.generateMoves(b, p);
        }
        return Moves;
    }
    
    public Iterator<Move> generateMoves(Board b)
    {
        final ArrayList<Move> moves = generateAllMoves(b);
        
        // TODO: This logic should be refined in the future so that we do no
        // generate all moves at once.
        
        return moves.iterator();
    }
}

// TODO: Eliminatate generation of a number of moves when king is in check...
// The king is not allowed to be in check while another piece is moved...

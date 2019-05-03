package com.simplisticjavachess.movegenerator;

import com.simplisticjavachess.misc.IteratorUtils;
import com.simplisticjavachess.board.Board;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.piece.Color;
import com.simplisticjavachess.piece.Piece;
import java.util.ArrayList;
import java.util.Iterator;


public class MoveGenerator implements IMoveGenerator
{
    private static final IMoveGenerator PAWN_MOVE_GENERATOR = new PawnMoveGenerator();
    private static final KingMoveGenerator KING_MOVE_GENERATOR = new KingMoveGenerator();
    private static final KnightMoveGenerator KNIGHT_MOVE_GENERATOR = new KnightMoveGenerator();
    private static final BishopMoveGenerator BISHOP_MOVE_GENERATOR = new BishopMoveGenerator();
    private static final RookMoveGenerator ROOK_MOVE_GENERATOR = new RookMoveGenerator();
    private static final QueenMoveGenerator QUEEN_MOVE_GENERATOR = new QueenMoveGenerator();

    public Iterator<Move> generateMoves(Board board, Piece piece)
    {
        final Color sideToMove = board.inMove();

        if (piece.getColor() != sideToMove)
        {
            return null;
        }

        switch (piece.getPieceType())
        {
            case PAWN:
                return PAWN_MOVE_GENERATOR.generateMoves(board, piece);
            case KING:
                return KING_MOVE_GENERATOR.generateMoves(board, piece);
            case KNIGHT:
                return KNIGHT_MOVE_GENERATOR.generateMoves(board, piece);
            case BISHOP:
                return BISHOP_MOVE_GENERATOR.generateMoves(board, piece);
            case ROOK:
                return ROOK_MOVE_GENERATOR.generateMoves(board, piece);
            case QUEEN:
               return QUEEN_MOVE_GENERATOR.generateMoves(board, piece);
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

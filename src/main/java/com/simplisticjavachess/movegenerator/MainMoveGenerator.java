package com.simplisticjavachess.movegenerator;

import com.simplisticjavachess.misc.IteratorUtils;
import com.simplisticjavachess.board.Board;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.piece.Color;
import com.simplisticjavachess.piece.Piece;
import com.simplisticjavachess.piece.PieceType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class MainMoveGenerator implements MoveGenerator
{
    public static final PieceMoveGenerator PAWN_MOVE_GENERATOR = new PawnMoveGenerator();
    public static final KingMoveGenerator KING_MOVE_GENERATOR = new KingMoveGenerator();
    public static final KnightMoveGenerator KNIGHT_MOVE_GENERATOR = new KnightMoveGenerator();
    public static final BishopMoveGenerator BISHOP_MOVE_GENERATOR = new BishopMoveGenerator();
    public static final RookMoveGenerator ROOK_MOVE_GENERATOR = new RookMoveGenerator();
    public static final QueenMoveGenerator QUEEN_MOVE_GENERATOR = new QueenMoveGenerator();

    private Map<PieceType, PieceMoveGenerator> moveGeneratorMap;

    public MainMoveGenerator()
    {
        this(PAWN_MOVE_GENERATOR, KING_MOVE_GENERATOR, KNIGHT_MOVE_GENERATOR, BISHOP_MOVE_GENERATOR,
                ROOK_MOVE_GENERATOR, QUEEN_MOVE_GENERATOR);
    }

    public MainMoveGenerator(PieceMoveGenerator... moveGenerators)
    {
        moveGeneratorMap = new HashMap<>();
        for (PieceMoveGenerator moveGenerator : moveGenerators) {
            moveGeneratorMap.put(moveGenerator.getPieceType(), moveGenerator);
        }
    }

    public Iterator<Move> generateMoves(Board board, Piece piece)
    {
        final Color sideToMove = board.inMove();

        if (piece.getColor() != sideToMove)
        {
            return null;
        }

        PieceType pieceType = piece.getPieceType();

        if (moveGeneratorMap.containsKey(pieceType)) {
            return moveGeneratorMap.get(pieceType).generateMoves(board, piece);
        } else {
            return Collections.emptyIterator();
        }

    }

    @Override
    public Iterator<Move> generateMoves(Board board)
    {
        final ArrayList<Iterator<Move>> moveIterators = new ArrayList<>();

        if (board.isDraw() || board.isMate())
        {
            return Collections.emptyIterator();
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

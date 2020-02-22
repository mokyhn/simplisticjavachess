/**
 *
 * @author Morten Kühnrich
 */

package com.simplisticjavachess.movegenerator;

import com.simplisticjavachess.board.Board;
import com.simplisticjavachess.board.Vector;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.piece.Piece;
import com.simplisticjavachess.piece.PieceType;

import java.util.Iterator;

import static com.simplisticjavachess.misc.IteratorUtils.compose;
import static com.simplisticjavachess.movegenerator.MoveGeneratorUtil.oneMoveIterator;

public class KnightMoveGenerator implements PieceMoveGenerator
{

    @Override
    public PieceType getPieceType() {
        return PieceType.KNIGHT;
    }

    @Override
    public Iterator<Move> generateMoves(Board board, Piece piece)
    {
        return compose(
                oneMoveIterator(() -> MoveGeneratorUtil.genMove(board, piece, new Vector(-2, 1))),
                oneMoveIterator(() -> MoveGeneratorUtil.genMove(board, piece, new Vector(-2, -1))),
                oneMoveIterator(() -> MoveGeneratorUtil.genMove(board, piece, new Vector(-1, 2))),
                oneMoveIterator(() -> MoveGeneratorUtil.genMove(board, piece, new Vector(1, 2))),
                oneMoveIterator(() -> MoveGeneratorUtil.genMove(board, piece, new Vector(-1, -2))),
                oneMoveIterator(() -> MoveGeneratorUtil.genMove(board, piece, new Vector(1, -2))),
                oneMoveIterator(() -> MoveGeneratorUtil.genMove(board, piece, new Vector(2, 1))),
                oneMoveIterator(() -> MoveGeneratorUtil.genMove(board, piece, new Vector(2, -1)))
        );
    }

}

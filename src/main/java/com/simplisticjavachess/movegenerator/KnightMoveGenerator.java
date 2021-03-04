/**
 *
 * @author Morten Kühnrich
 */

package com.simplisticjavachess.movegenerator;

import com.simplisticjavachess.Immutable;
import com.simplisticjavachess.position.Position;
import com.simplisticjavachess.position.Vector;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.piece.Piece;
import com.simplisticjavachess.piece.PieceType;

import java.util.Iterator;

import static com.simplisticjavachess.misc.IteratorUtils.compose;
import static com.simplisticjavachess.movegenerator.MoveGeneratorUtil.oneMoveIterator;

@Immutable
public final class KnightMoveGenerator implements PieceMoveGenerator
{

    @Override
    public PieceType getPieceType() {
        return PieceType.KNIGHT;
    }

    @Override
    public Iterator<Move> generateMoves(Position position, Piece piece)
    {
        return compose(
                oneMoveIterator(() -> MoveGeneratorUtil.genMove(position, piece, new Vector(-2, 1))),
                oneMoveIterator(() -> MoveGeneratorUtil.genMove(position, piece, new Vector(-2, -1))),
                oneMoveIterator(() -> MoveGeneratorUtil.genMove(position, piece, new Vector(-1, 2))),
                oneMoveIterator(() -> MoveGeneratorUtil.genMove(position, piece, new Vector(1, 2))),
                oneMoveIterator(() -> MoveGeneratorUtil.genMove(position, piece, new Vector(-1, -2))),
                oneMoveIterator(() -> MoveGeneratorUtil.genMove(position, piece, new Vector(1, -2))),
                oneMoveIterator(() -> MoveGeneratorUtil.genMove(position, piece, new Vector(2, 1))),
                oneMoveIterator(() -> MoveGeneratorUtil.genMove(position, piece, new Vector(2, -1)))
        );
    }

}

/**
 * @author Morten Kühnrich
 */

package com.simplisticjavachess.movegenerator;

import com.simplisticjavachess.Immutable;
import com.simplisticjavachess.position.Position;
import com.simplisticjavachess.misc.IteratorUtils;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.piece.Piece;
import com.simplisticjavachess.piece.PieceType;

import java.util.Iterator;


@Immutable
public final class QueenMoveGenerator implements PieceMoveGenerator {
    @Override
    public PieceType getPieceType() {
        return PieceType.QUEEN;
    }

    @Override
    public Iterator<Move> generateMoves(Position position, Piece piece) {
        return IteratorUtils.compose(
                new BishopMoveGenerator().generateMoves(position, piece),
                new RookMoveGenerator().generateMoves(position, piece)
        );
    }

}

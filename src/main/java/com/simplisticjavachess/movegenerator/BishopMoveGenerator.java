package com.simplisticjavachess.movegenerator;

import com.simplisticjavachess.Immutable;
import com.simplisticjavachess.position.Position;
import com.simplisticjavachess.position.Vector;
import com.simplisticjavachess.misc.IteratorUtils;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.piece.Piece;
import com.simplisticjavachess.piece.PieceType;

import java.util.Arrays;
import java.util.Iterator;

/**
 * @author Morten Kühnrich
 */
@Immutable
public final class BishopMoveGenerator implements PieceMoveGenerator
{
    private static final LineMoveGenerator UP_AND_RIGHT =  new LineMoveGenerator(Vector.UP_AND_RIGHT);
    private static final LineMoveGenerator DOWN_AND_RIGHT = new LineMoveGenerator(Vector.DOWN_AND_RIGHT);
    private static final LineMoveGenerator UP_AND_LEFT = new LineMoveGenerator(Vector.UP_AND_LEFT);
    private static final LineMoveGenerator DOWN_AND_LEFT = new LineMoveGenerator(Vector.DOWN_AND_LEFT);

    @Override
    public PieceType getPieceType() {
        return PieceType.BISHOP;
    }

    public Iterator<Move> generateMoves(Position position, Piece piece)
    {
        return IteratorUtils.compose(Arrays.asList(
                UP_AND_RIGHT.generateMoves(position, piece),
                DOWN_AND_RIGHT.generateMoves(position, piece),
                UP_AND_LEFT.generateMoves(position, piece),
                DOWN_AND_LEFT.generateMoves(position, piece)));
    }
}
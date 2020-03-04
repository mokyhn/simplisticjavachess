/**
 *
 * @author Morten Kühnrich
 */

package com.simplisticjavachess.movegenerator;

import com.simplisticjavachess.board.Position;
import com.simplisticjavachess.board.Vector;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.piece.Piece;
import com.simplisticjavachess.piece.PieceType;

import java.util.Iterator;

import static com.simplisticjavachess.misc.IteratorUtils.compose;

public class RookMoveGenerator implements PieceMoveGenerator
{
    private static final LineMoveGenerator LINE_MOVE_GENERATOR_UP = new LineMoveGenerator(Vector.UP);
    private static final LineMoveGenerator LINE_MOVE_GENERATOR_DOWN = new LineMoveGenerator(Vector.DOWN);
    private static final LineMoveGenerator LINE_MOVE_GENERATOR_RIGHT = new LineMoveGenerator(Vector.RIGHT);
    private static final LineMoveGenerator LINE_MOVE_GENERATOR_LEFT = new LineMoveGenerator(Vector.LEFT);

    @Override
    public PieceType getPieceType() {
        return PieceType.ROOK;
    }

    public Iterator<Move> generateMoves(Position position, Piece piece)
    {
        return compose(
                LINE_MOVE_GENERATOR_UP.generateMoves(position, piece),
                LINE_MOVE_GENERATOR_DOWN.generateMoves(position, piece),
                LINE_MOVE_GENERATOR_RIGHT.generateMoves(position, piece),
                LINE_MOVE_GENERATOR_LEFT.generateMoves(position, piece)
        );
    }

}

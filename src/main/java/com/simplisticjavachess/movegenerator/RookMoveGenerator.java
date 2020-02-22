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

    public Iterator<Move> generateMoves(Board board, Piece piece)
    {
        return compose(
                LINE_MOVE_GENERATOR_UP.generateMoves(board, piece),
                LINE_MOVE_GENERATOR_DOWN.generateMoves(board, piece),
                LINE_MOVE_GENERATOR_RIGHT.generateMoves(board, piece),
                LINE_MOVE_GENERATOR_LEFT.generateMoves(board, piece)
        );
    }

}

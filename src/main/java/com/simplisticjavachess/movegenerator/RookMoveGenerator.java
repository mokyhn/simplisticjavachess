/**
 *
 * @author Morten Kühnrich
 */

package com.simplisticjavachess.movegenerator;

import com.simplisticjavachess.board.Board;
import com.simplisticjavachess.board.Vector;
import com.simplisticjavachess.misc.IteratorUtils;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.piece.Piece;
import java.util.Arrays;
import java.util.Iterator;

public class RookMoveGenerator implements IMoveGenerator
{
    private static final LineMoveGenerator LINE_MOVE_GENERATOR_UP = new LineMoveGenerator(Vector.UP);
    private static final LineMoveGenerator LINE_MOVE_GENERATOR_DOWN = new LineMoveGenerator(Vector.DOWN);
    private static final LineMoveGenerator LINE_MOVE_GENERATOR_RIGHT = new LineMoveGenerator(Vector.RIGHT);
    private static final LineMoveGenerator LINE_MOVE_GENERATOR_LEFT = new LineMoveGenerator(Vector.LEFT);

    public Iterator<Move> generateMoves(Board board, Piece piece)
    {
        return IteratorUtils.compose(Arrays.asList(
                LINE_MOVE_GENERATOR_UP.generateMoves(board, piece),
                LINE_MOVE_GENERATOR_DOWN.generateMoves(board, piece),
                LINE_MOVE_GENERATOR_RIGHT.generateMoves(board, piece),
                LINE_MOVE_GENERATOR_LEFT.generateMoves(board, piece)
        ));
    }

}

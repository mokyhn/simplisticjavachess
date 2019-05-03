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
    public Iterator<Move> generateMoves(Board board, Piece piece)
    {
        return IteratorUtils.compose(Arrays.asList(
                new LineMoveGenerator(Vector.UP).generateMoves(board, piece),
                new LineMoveGenerator(Vector.DOWN).generateMoves(board, piece),
                new LineMoveGenerator(Vector.RIGHT).generateMoves(board, piece),
                new LineMoveGenerator(Vector.LEFT).generateMoves(board, piece)
        ));
    }

}

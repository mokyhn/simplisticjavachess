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

public class BishopMoveGenerator implements IMoveGenerator
{
    public Iterator<Move> generateMoves(Board board, Piece piece)
    {
        return IteratorUtils.compose(Arrays.asList(
                new LineMoveGenerator(Vector.UP_AND_RIGHT).generateMoves(board, piece),
                new LineMoveGenerator(Vector.DOWN_AND_RIGHT).generateMoves(board, piece),
                new LineMoveGenerator(Vector.UP_AND_LEFT).generateMoves(board, piece),
                new LineMoveGenerator(Vector.DOWN_AND_LEFT).generateMoves(board, piece)));
    }
}
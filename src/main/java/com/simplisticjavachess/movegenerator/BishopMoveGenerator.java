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
    private static IMoveGenerator UP_AND_RIGHT =  new LineMoveGenerator(Vector.UP_AND_RIGHT);
    private static IMoveGenerator DOWN_AND_RIGHT = new LineMoveGenerator(Vector.DOWN_AND_RIGHT);
    private static IMoveGenerator UP_AND_LEFT = new LineMoveGenerator(Vector.UP_AND_LEFT);
    private static IMoveGenerator DOWN_AND_LEFT = new LineMoveGenerator(Vector.DOWN_AND_LEFT);

    public Iterator<Move> generateMoves(Board board, Piece piece)
    {
        return IteratorUtils.compose(Arrays.asList(
                UP_AND_RIGHT.generateMoves(board, piece),
                DOWN_AND_RIGHT.generateMoves(board, piece),
                UP_AND_LEFT.generateMoves(board, piece),
                DOWN_AND_LEFT.generateMoves(board, piece)));
    }
}
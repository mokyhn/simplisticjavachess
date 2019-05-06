package com.simplisticjavachess.movegenerator;

import com.simplisticjavachess.board.Board;
import com.simplisticjavachess.board.Vector;
import com.simplisticjavachess.misc.IteratorUtils;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.piece.Piece;
import com.simplisticjavachess.piece.PieceType;

import java.util.Arrays;
import java.util.Iterator;

/**
 * @author Morten Kühnrich
 */
public class BishopMoveGenerator implements IMoveGenerator
{
    private static final LineMoveGenerator UP_AND_RIGHT =  new LineMoveGenerator(Vector.UP_AND_RIGHT);
    private static final LineMoveGenerator DOWN_AND_RIGHT = new LineMoveGenerator(Vector.DOWN_AND_RIGHT);
    private static final LineMoveGenerator UP_AND_LEFT = new LineMoveGenerator(Vector.UP_AND_LEFT);
    private static final LineMoveGenerator DOWN_AND_LEFT = new LineMoveGenerator(Vector.DOWN_AND_LEFT);

    @Override
    public PieceType getPieceType() {
        return PieceType.BISHOP;
    }

    public Iterator<Move> generateMoves(Board board, Piece piece)
    {
        return IteratorUtils.compose(Arrays.asList(
                UP_AND_RIGHT.generateMoves(board, piece),
                DOWN_AND_RIGHT.generateMoves(board, piece),
                UP_AND_LEFT.generateMoves(board, piece),
                DOWN_AND_LEFT.generateMoves(board, piece)));
    }
}
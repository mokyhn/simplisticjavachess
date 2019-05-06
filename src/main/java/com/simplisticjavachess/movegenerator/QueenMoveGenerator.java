/**
 *
 * @author Morten Kühnrich
 */

package com.simplisticjavachess.movegenerator;

import com.simplisticjavachess.misc.IteratorUtils;
import com.simplisticjavachess.board.Board;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.piece.Piece;
import com.simplisticjavachess.piece.PieceType;

import java.util.Iterator;


public class QueenMoveGenerator implements IMoveGenerator
{
    @Override
    public PieceType getPieceType() {
        return PieceType.QUEEN;
    }

    @Override
    public Iterator<Move> generateMoves(Board board, Piece piece)
    {
        return IteratorUtils.compose(
                new BishopMoveGenerator().generateMoves(board, piece),
                new RookMoveGenerator().generateMoves(board, piece)
        );
    }
   
}

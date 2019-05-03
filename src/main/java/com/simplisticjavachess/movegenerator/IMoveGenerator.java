package com.simplisticjavachess.movegenerator;

import com.simplisticjavachess.board.Board;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.piece.Piece;

import java.util.Iterator;

public interface IMoveGenerator {
    /**
     *
     * @param board the board to generate moves in context of
     * @param piece the piece to generate moves for
     * @return an iterator of moves
     */
    Iterator<Move> generateMoves(Board board, Piece piece);
}

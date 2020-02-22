package com.simplisticjavachess.movegenerator;

import com.simplisticjavachess.board.Board;
import com.simplisticjavachess.move.Move;

import java.util.Iterator;

public interface MoveGenerator
{
    Iterator<Move> generateMoves(Board board);

}

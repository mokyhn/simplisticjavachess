package com.simplisticjavachess.movegenerator;

import com.simplisticjavachess.board.Position;
import com.simplisticjavachess.move.Move;

import java.util.Iterator;

public interface MoveGenerator
{
    Iterator<Move> generateMoves(Position position);

}

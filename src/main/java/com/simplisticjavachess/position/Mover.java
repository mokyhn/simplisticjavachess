package com.simplisticjavachess.position;

import com.simplisticjavachess.move.Move;

public interface Mover {
    Position doMove(Position position, Move move) throws IllegalMoveException;

    Position doMove(Position position, String moves) throws IllegalMoveException;
}

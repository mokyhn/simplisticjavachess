package com.simplisticjavachess.movegenerator;

import com.simplisticjavachess.board.Board;
import com.simplisticjavachess.board.BoardParser;
import com.simplisticjavachess.board.Position;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.move.MoveParser;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class OpeningMoveGenerator implements MoveGenerator
{
    HashMap<Position, Move> moves = new HashMap<>();

    public void add(String FEN, String moveStr)
    {
        Board board = BoardParser.parseFEN(FEN);
        Move move = MoveParser.parse(board, moveStr);

        if (move == null)
        {
            throw new IllegalArgumentException("Cannot accept a move of color opponent is to move");
        }

        Position position = board.getPosition();
        moves.put(position, move);
    }

    @Override
    public Iterator<Move> generateMoves(Board board) {
        Move move = moves.get(board.getPosition());

        if (move == null)
        {
            return Collections.emptyIterator();
        }
        else
        {
            return Collections.singleton(move).iterator();
        }
    }
}

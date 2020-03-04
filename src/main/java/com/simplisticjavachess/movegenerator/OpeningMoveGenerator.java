package com.simplisticjavachess.movegenerator;

import com.simplisticjavachess.board.BoardParser;
import com.simplisticjavachess.board.MoveResult;
import com.simplisticjavachess.board.Mover;
import com.simplisticjavachess.board.Position;
import com.simplisticjavachess.engine.MoveSequence;
import com.simplisticjavachess.misc.IteratorUtils;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.move.MoveParser;
import com.simplisticjavachess.piece.Color;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class OpeningMoveGenerator implements MoveGenerator
{
    HashMap<Position, Move> moves = new HashMap<>();

    public void add(String FEN, String moveStr)
    {
        Position position = BoardParser.FEN(FEN);
        Move move = MoveParser.parse(position, moveStr);

        if (move == null)
        {
            throw new IllegalArgumentException("Cannot accept a move of color opponent is to move");
        }

        add(position, move);
    }

    private void add(Position position, Move move)
    {
        if (!moves.containsKey(position))
        {
            moves.put(position, move);
        }
    }

    public void addFromMoves(String FEN, String moves, Color sideToRecord)
    {
        Position position = BoardParser.FEN(FEN);
        MoveSequence moveSequence = MoveSequence.parse(position, moves);

        List<Move> moveList = IteratorUtils.toList(moveSequence.iterator());

        for (Move move : moveList)
        {
            MoveResult moveResult = Mover.doMove(position, move);
            if (moveResult.isMoveLegal())
            {
                if (sideToRecord == move.getWhoMoves())
                {
                    add(position, move);
                }
                position = moveResult.getPosition();
            }
            else
            {
                throw new IllegalArgumentException("Can not use illegal moves");
            }
        }
    }

    @Override
    public Iterator<Move> generateMoves(Position position) {
        Move move = moves.get(position);

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

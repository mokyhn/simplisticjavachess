package com.simplisticjavachess.movegenerator;

import com.google.common.collect.ImmutableMap;
import com.simplisticjavachess.Immutable;
import com.simplisticjavachess.move.MoveSequence;
import com.simplisticjavachess.misc.IteratorUtils;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.move.MoveParser;
import com.simplisticjavachess.piece.Color;
import com.simplisticjavachess.position.ChessMover;
import com.simplisticjavachess.position.IllegalMoveException;
import com.simplisticjavachess.position.Position;
import com.simplisticjavachess.position.PositionIO;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Fixed move generator. For a given position it checks, if it has a move in stock and returns that.
 */
@Immutable
public final class FixedMoveGenerator implements MoveGenerator {
    private final ImmutableMap<Position, Move> moveMap;

    public FixedMoveGenerator() {
        moveMap = ImmutableMap.of();
    }

    private FixedMoveGenerator(ImmutableMap<Position, Move> moveMap) {
        this.moveMap = moveMap;
    }

    public FixedMoveGenerator add(String FEN, String moveStr) {
        Position position = PositionIO.FEN(FEN);
        Move move = MoveParser.parse(position, moveStr);

        if (move == null) {
            throw new IllegalArgumentException("Cannot accept a move of color opponent is to move");
        }

        return add(position, move);
    }

    private FixedMoveGenerator add(Position position, Move move) {
        if (moveMap.containsKey(position)) {
            return this;
        } else {
            ImmutableMap.Builder<Position, Move> builder = ImmutableMap.builder();
            return new FixedMoveGenerator(
                    builder.
                    putAll(moveMap).
                    put(position, move).
                    build());
        }
    }

    public FixedMoveGenerator addFromMoves(String FEN, String moves, Color sideToRecord) {
        Position position = PositionIO.FEN(FEN);
        MoveSequence moveSequence = MoveSequence.parse(position, moves);

        List<Move> moveList = IteratorUtils.toList(moveSequence.iterator());

        FixedMoveGenerator result = this;

        for (Move move : moveList) {
            try {
                Position moveResult = new ChessMover().doMove(position, move);
                if (sideToRecord == move.getWhoMoves()) {
                    result = result.add(position, move);
                }
                position = moveResult;

            } catch (IllegalMoveException e) {
                throw new IllegalArgumentException("Can not use illegal moves");
            }
        }

        return result;
    }

    /**
     * Kathe Spracklen parenthesis variant format
     */
    public void addFromVariantTree(String FEN, String variants) {
        //TODO
    }

    @Override
    public Iterator<Move> generateMoves(Position position) {
        Move move = moveMap.get(position);

        if (move == null) {
            return Collections.emptyIterator();
        } else {
            return Collections.singleton(move).iterator();
        }
    }
}

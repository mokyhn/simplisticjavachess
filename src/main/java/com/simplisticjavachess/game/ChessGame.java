/**
 * @author Morten Kühnrich
 */


package com.simplisticjavachess.game;

import com.simplisticjavachess.engine.AlphaBetaEngine;
import com.simplisticjavachess.evaluation.IntegerEvaluator;
import com.simplisticjavachess.move.MoveParser;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.movegenerator.MainMoveGenerator;
import com.simplisticjavachess.engine.SearchResult;
import com.simplisticjavachess.movegenerator.MoveGenerator;
import com.simplisticjavachess.movegenerator.OpeningBook;
import com.simplisticjavachess.piece.Color;
import com.simplisticjavachess.position.ChessMover;
import com.simplisticjavachess.position.History;
import com.simplisticjavachess.position.IllegalMoveException;
import com.simplisticjavachess.position.Mover;
import com.simplisticjavachess.position.PositionIO;
import com.simplisticjavachess.position.PositionInference;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static com.simplisticjavachess.misc.IteratorUtils.toList;

public class ChessGame {
    private static final String INITIAL_POSITION = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 0";

    private History history;
    private final Mover mover = new ChessMover();
    private final MoveGenerator moveGenerator = new MainMoveGenerator();
    private int searchDepth;
    private Color computerColor;

    public ChessGame() {
        history = new History(INITIAL_POSITION);
        searchDepth = 4;
    }

    public void setPosition(String fen) {
        this.history = new History(fen);
    }

    public void setComputerColor(Color color) {
        this.computerColor = color;
    }

    public boolean isTheComputerToMove() {
        return this.computerColor.equals(history.getCurrent().inMove());
    }

    public void newGame() {
        history = new History(INITIAL_POSITION);
        computerColor = null;
    }

    public void print() {
        System.out.println(history.getCurrent().toString());
        System.out.println(PositionIO.exportPosition(history.getCurrent()));
    }

    public void setSd(int depth) {
        this.searchDepth = depth;
    }

    public void go() {
        Optional<Move> optionalMove = search();

        if (optionalMove.isPresent()) {
            Move move = optionalMove.get();
            try {
                history = history.add(mover.doMove(history.getCurrent(), move));
            } catch (IllegalMoveException e) {
                e.printStackTrace();
            }
        } else {
        }

        switch (PositionInference.getGameResult(history)) {
            case NO_RESULT:
                break;
            case DRAW:
                System.out.println("Draw!");
                break;
            case MATE:
                System.out.println("Mate!");
                break;
        }
        print();

    }

    public Optional<Move> search() {
        Iterator<Move> openingMove = OpeningBook.get().generateMoves(history.getCurrent());
        Move move;

        if (openingMove.hasNext()) {
            move = openingMove.next();
            System.out.println("Book move " + move.toString());
        } else {
            SearchResult searchResult = new AlphaBetaEngine().search(history.getCurrent(), mover,
                    new MainMoveGenerator(), IntegerEvaluator.variant(), searchDepth);
            if (searchResult.getMoveSequence().iterator().hasNext()) {
                move = searchResult.getMoveSequence().getFirst();
            } else {
                return Optional.empty();
            }
            System.out.println(searchResult.toString());
        }

        return Optional.of(move);
    }

    public void move(String str) {
        try {
            Move move = MoveParser.parse(history.getCurrent(), str);
            List<Move> possibleMoves = toList(moveGenerator.generateMoves(history.getCurrent()));
            if (possibleMoves.contains(move)) {
                try {
                    history = history.add(mover.doMove(history.getCurrent(), move));
                    switch (PositionInference.getGameResult(history)) {
                        case NO_RESULT:
                            break;
                        case DRAW:
                            System.out.println("Draw!");
                            break;
                        case MATE:
                            System.out.println("Mate!");
                            break;
                    }
                } catch (IllegalMoveException e) {
                    throw new IllegalArgumentException("Invalid move " + str);
                }
            } else {
                throw new IllegalArgumentException("Invalid move " + str);
            }

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid move " + str);
        }
        print();
    }

    public void telnetMove(String str) {
        try {
            Move move = MoveParser.parse(history.getCurrent(), str);
            history = history.add(mover.doMove(history.getCurrent(), move));
        } catch (IllegalMoveException e) {
            throw new IllegalArgumentException("Invalid move " + str);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid move " + str);
        }
    }


    public void undo() {
        history = history.undo();
        print();
    }
}

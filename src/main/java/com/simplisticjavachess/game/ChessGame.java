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
    private boolean gameOver;
    private Color computerColor;

    public ChessGame() {
        history = new History(INITIAL_POSITION);
        searchDepth = 4;
        gameOver = false;
    }

    public void setPosition(String fen) {
        this.history = new History(fen);
        this.gameOver = false;
    }

    public void setComputerColor(Color color) {
        this.computerColor = color;
    }

    public boolean isTheComputerToMove() {
        return this.computerColor.equals(history.getCurrent().inMove());
    }

    public void newGame() {
        history = new History(INITIAL_POSITION);
        gameOver = false;
        computerColor = null;
    }

    public void print() {
        System.out.println(history.getCurrent().toString());
    }

    public void setSd(int depth) {
        this.searchDepth = depth;
    }

    public Optional<String> go() {
        Optional<Move> optionalMove = search();

        Move move = optionalMove.get();

        try {
            history = history.add(mover.doMove(history.getCurrent(), move));
            switch (PositionInference.getGameResult(history)) {
                case NO_RESULT:
                    break;
                case DRAW:
                    System.out.println("Draw!");
                    gameOver = true;
                    break;
                case MATE:
                    System.out.println("Mate!");
                    gameOver = true;
                    break;
            }
            return Optional.of(move.toString());
        } catch (IllegalMoveException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<Move> search() {
        if (gameOver) {
            return Optional.empty();
        }

        Iterator<Move> openingMove = OpeningBook.get().generateMoves(history.getCurrent());
        Move move;

        if (openingMove.hasNext()) {
            move = openingMove.next();
            System.out.println("Book move " + move.toString());
        } else {
            SearchResult searchResult = new AlphaBetaEngine().search(history.getCurrent(), mover, new MainMoveGenerator(), new IntegerEvaluator(), searchDepth);
            move = searchResult.getMoveSequence().getFirst();
            System.out.println(searchResult.toString());
        }

        return Optional.of(move);
    }

    public void move(String str) {
        if (gameOver) {
            return;
        }

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
                            gameOver = true;
                            break;
                        case MATE:
                            System.out.println("Mate!");
                            gameOver = true;
                            break;
                    }
                } catch (IllegalMoveException e) {
                    throw new IllegalArgumentException("Invalid move " + str);
                }
            } else {
                throw new IllegalArgumentException("Invalid move " + str );
            }

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid move " + str);
        }

        print();
    }

    public void undo() {
        history = history.undo();
        print();
    }
}

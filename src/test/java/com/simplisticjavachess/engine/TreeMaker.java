package com.simplisticjavachess.engine;

import com.simplisticjavachess.evaluation.Evaluation;
import com.simplisticjavachess.evaluation.Evaluator;
import com.simplisticjavachess.evaluation.IntegerEvaluation;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.movegenerator.MoveGenerator;
import com.simplisticjavachess.piece.Color;
import com.simplisticjavachess.position.IllegalMoveException;
import com.simplisticjavachess.position.Mover;
import com.simplisticjavachess.position.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TreeMaker {

    private Position root;
    private final MoveGenerator moveGenerator;
    private final Mover mover;
    private final Evaluator evaluator;


    public TreeMaker(MoveGenerator moveGenerator, Mover mover, Evaluator evaluator) {
        this.moveGenerator = moveGenerator;
        this.mover = mover;
        this.evaluator = evaluator;
    }

    private TreeMaker(Position root, MoveGenerator moveGenerator, Mover mover, Evaluator evaluator) {
        this.root = root;
        this.moveGenerator = moveGenerator;
        this.mover = mover;
        this.evaluator = evaluator;
    }

    public TreeMaker getDrawBy50MoveRule(Evaluation evaluation) {
        Position newRoot = mock(Position.class);
        when(newRoot.isDrawBy50Move()).thenReturn(true);
        when(evaluator.getEqual()).thenReturn(evaluation);
        return new TreeMaker(newRoot, moveGenerator, mover, evaluator);
    }

    public TreeMaker mate(Color color, Evaluation evaluation)
    {
        Position newRoot = mock(Position.class);
        when(newRoot.isDrawBy50Move()).thenReturn(false);
        when(moveGenerator.generateMoves(newRoot)).thenReturn(Collections.emptyIterator());
        when(newRoot.isInCheck()).thenReturn(true);
        when(newRoot.inMove()).thenReturn(color);
        when(color.isWhite() ? evaluator.getWhiteIsMate() : evaluator.getBlackIsMate()).thenReturn(evaluation);
        return new TreeMaker(newRoot, moveGenerator, mover, evaluator);
    }

    public TreeMaker staleMate(Color color, Evaluation evaluation)
    {
        Position newRoot = mock(Position.class);
        when(newRoot.isDrawBy50Move()).thenReturn(false);
        when(moveGenerator.generateMoves(newRoot)).thenReturn(Collections.emptyIterator());
        when(newRoot.isInCheck()).thenReturn(false);
        when(newRoot.inMove()).thenReturn(color);
        when(evaluator.getEqual()).thenReturn(evaluation);
        return new TreeMaker(newRoot, moveGenerator, mover, evaluator);
    }

    public TreeMaker leafs(Color color, Integer... evaluations) throws IllegalMoveException {
        Position newRoot = mock(Position.class);
        when(newRoot.isDrawBy50Move()).thenReturn(false);
        when(newRoot.inMove()).thenReturn(color);
        when(evaluator.getNone()).thenReturn(new IntegerEvaluation());
        List<Move> moves = new ArrayList<>();
        for (Integer evaluation : evaluations) {
            Position leaf = mock(Position.class);
            when(leaf.isDrawBy50Move()).thenReturn(false);
            Move move = mock(Move.class);
            moves.add(move);
            when(mover.doMove(newRoot, move)).thenReturn(leaf);
            when(evaluator.evaluate(leaf)).thenReturn(IntegerEvaluation.of(evaluation));
        }
        when(moveGenerator.generateMoves(newRoot)).thenReturn(moves.iterator());

        return new TreeMaker(newRoot, moveGenerator, mover, evaluator);
    }

    public TreeMaker compose(Color color, TreeMaker... treeMakers) throws IllegalMoveException {
        Position newRoot = mock(Position.class);
        when(newRoot.isDrawBy50Move()).thenReturn(false);
        when(newRoot.inMove()).thenReturn(color);
        when(evaluator.getNone()).thenReturn(new IntegerEvaluation());
        List<Move> moves = new ArrayList<>();
        for (TreeMaker treeMaker : treeMakers) {
            Move move = mock(Move.class);
            when(mover.doMove(newRoot, move)).thenReturn(treeMaker.root);
            moves.add(move);
        }
        when(moveGenerator.generateMoves(newRoot)).thenReturn(moves.iterator());

        return new TreeMaker(newRoot, moveGenerator, mover, evaluator);
    }


    public Position getRoot() {
        return root;
    }

    public MoveGenerator getMoveGenerator() {
        return moveGenerator;
    }

    public Evaluator getEvaluator() {
        return evaluator;
    }

    public Mover getMover() {
        return mover;
    }
}

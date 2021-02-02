/**
 * @author Morten Kühnrich
 */


package com.simplisticjavachess.engine;

import com.simplisticjavachess.position.Mover;
import com.simplisticjavachess.position.IllegalMoveException;
import com.simplisticjavachess.position.Position;
import com.simplisticjavachess.evaluation.Evaluation;
import com.simplisticjavachess.evaluation.Evaluator;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.move.MoveSequence;
import com.simplisticjavachess.movegenerator.MoveGenerator;
import com.simplisticjavachess.piece.Color;

import java.util.Iterator;

public class MinMaxEngine implements Engine {

    @Override
    public SearchResult search(Position position, Mover mover, MoveGenerator moveGenerator, Evaluator evaluator, int depth) {
        if (position.isDrawBy50Move()) {
            return new SearchResult(evaluator.getEqual());
        }

        if (depth == 0) {
            return new SearchResult(evaluator.evaluate(position));
        }

        Iterator<Move> moves = moveGenerator.generateMoves(position);

        Evaluation bestScore = evaluator.getNone();
        MoveSequence moveSequence = new MoveSequence();
        Color inMove = position.inMove();
        boolean thereWasALegalMove = false;
        while (moves.hasNext()) {
            Move move = moves.next();
            try {
                Position next = mover.doMove(position, move);
                thereWasALegalMove = true;
                SearchResult score = search(next, mover, moveGenerator, evaluator, depth - 1);
                if (bestScore.isAnImprovement(inMove, score.getEvaluation())) {
                    bestScore = score.getEvaluation();
                    moveSequence = score.getMoveSequence().add(move);
                }
            } catch (IllegalMoveException e) {
                // In case of an illegal move then the loop continues in search for another legal move
            }
        }

        if (!thereWasALegalMove) {
            if (position.isInCheck()) {
                // mate
                return position.inMove().isWhite() ?
                        new SearchResult(evaluator.getWhiteIsMate()) :
                        new SearchResult(evaluator.getBlackIsMate());
            } else {
                // stalemate
                return new SearchResult(evaluator.getEqual());
            }
        }

        return new SearchResult(moveSequence, bestScore);
    }

}

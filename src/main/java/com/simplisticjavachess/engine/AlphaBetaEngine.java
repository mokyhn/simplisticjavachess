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

public class AlphaBetaEngine implements Engine {
    private static int positions = 0;
    private static int cutOffs = 0;

    @Override
    public SearchResult search(Position position, Mover mover, MoveGenerator moveGenerator, Evaluator evaluator,
                               int depth) {
        positions = 0;
        cutOffs = 0;
        SearchResult searchResult = alphaBeta(position, mover, moveGenerator, evaluator, evaluator.getNone(),
                evaluator.getNone(), depth);
        System.out.println("Cutoffs " + cutOffs + " of " + positions + " positions");
        return searchResult;
    }

    public SearchResult alphaBeta(Position position, Mover mover, MoveGenerator moveGenerator, Evaluator evaluator,
                                  Evaluation alpha,
                                  Evaluation beta,
                                  int depth) {
        if (position.isDrawBy50Move()) {
            return new SearchResult(evaluator.getEqual());
        }

        if (depth == 0) {
            return new SearchResult(evaluator.evaluate(position));
        }

        Iterator<Move> moves = moveGenerator.generateMoves(position);
        if (!moves.hasNext()) {
            if (position.isInCheck()) {
                return new SearchResult(position.inMove().isWhite() ?
                        evaluator.getWhiteIsMate() : evaluator.getBlackIsMate()); // Mate
            } else {
                return new SearchResult(evaluator.getEqual());
            }
        }


        MoveSequence moveSequence = new MoveSequence();
        boolean thereWasALegalMove = false;

        Color inMove = position.inMove();


        Evaluation bestScore = evaluator.getNone();
        while (moves.hasNext()) {
            Move move = moves.next();
            positions++;
            // Do not search capture moves as the last move
//            if (depth == 1 && move.getMoveType().isCapture() && !move.getMoveType().isCapturePromotion())
//            {
//                continue;
//            }

            try {
                Position next = mover.doMove(position, move);
                thereWasALegalMove = true;

                SearchResult score = alphaBeta(next, mover, moveGenerator, evaluator, alpha, beta, depth - 1);
                if (bestScore.isAnImprovement(inMove, score.getEvaluation())) {
                    bestScore = score.getEvaluation();
                    moveSequence = score.getMoveSequence().add(move);
                }

                // Update alpha / beta
                if (inMove.isWhite()) {
                    alpha = bestScore;
                } else {
                    beta = bestScore;
                }

                // Learning: The cut off must not happen from None to a value!!!!! !evaluator.getNone().equals(alpha) &&...
                if (alpha.equals(beta) || (!evaluator.getNone().equals(alpha) && alpha.isAnImprovement(inMove, beta))) {
                    cutOffs++;
                    // Cut-off
                    break;
                }
            } catch (IllegalMoveException e) {
            } catch (IllegalArgumentException e) {
                throw new IllegalStateException();
            }
        }


        if (!thereWasALegalMove) {
            if (position.isInCheck()) {
                // mate
                return position.inMove().isWhite() ? new SearchResult(evaluator.getWhiteIsMate()) :
                        new SearchResult(evaluator.getBlackIsMate());
            } else {
                // draw
                return new SearchResult(evaluator.getEqual());
            }
        }

        return new SearchResult(moveSequence, bestScore);

    }

}

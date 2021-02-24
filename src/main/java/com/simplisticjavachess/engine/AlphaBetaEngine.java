/**
 * @author Morten Kühnrich
 */


package com.simplisticjavachess.engine;

import com.simplisticjavachess.evaluation.IntegerEvaluation;
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
import java.util.logging.Logger;

public class AlphaBetaEngine implements Engine {
    private static int positions = 0;
    private static int cutOffs = 0;

    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @Override
    public SearchResult search(Position position, Mover mover, MoveGenerator moveGenerator, Evaluator evaluator,
                               int depth) {
        if (depth % 2 != 0) {
            LOGGER.warning("Warning: Do not use an uneven search depth as one player will be favored.");
        }

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

        MoveSequence moveSequence = new MoveSequence();
        boolean thereWasALegalMove = false;

        Color inMove = position.inMove();


        Evaluation best = new IntegerEvaluation();
        Evaluation evaluation;

        while (moves.hasNext()) {
            Move move = moves.next();
            positions++;
            try {
                Position next = mover.doMove(position, move);
                thereWasALegalMove = true;

                SearchResult searchResult;
                searchResult = alphaBeta(next, mover, moveGenerator, evaluator, alpha, beta, depth-1);

                evaluation = searchResult.getEvaluation();

                if (best.isWorseThan(inMove, evaluation)) {
                    best = evaluation;
                    moveSequence = searchResult.getMoveSequence().add(move);
                }


                if (inMove.isWhite() && beta.isSomething() && (alpha.equals(beta) || evaluation.isWorseThan(inMove, beta))) {
                    cutOffs++;
                    // Cut-off
                    break;
                }

                if (inMove.isBlack() && alpha.isSomething() &&  (alpha.equals(beta) || alpha.isWorseThan(inMove, evaluation))) {
                    cutOffs++;
                    // Cut-off
                    break;
                }

                // Update alpha or beta
                if (inMove.isWhite()) {
                    if (alpha.isWorseThan(inMove, evaluation)) {
                        alpha = evaluation;
                    }
                } else {
                    if (evaluation.isWorseThan(inMove, beta)) {
                        beta = evaluation;
                    }
                }

            } catch (IllegalMoveException e) {
            } catch (IllegalStateException e) {
                System.out.println("Encountered problem in this position: " + position + " with move " +
                        move + " with this number of position searched: " + positions + " at a depth of " + depth + "move " + move.getMoveType() + " " + move.getFrom() + " " + move.getTo());
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

        return new SearchResult(moveSequence, best);

    }

}

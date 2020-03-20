/**
 *
 * @author Morten Kühnrich
 */


package com.simplisticjavachess.engine;

import com.simplisticjavachess.board.IllegalMoveException;
import com.simplisticjavachess.board.Mover;
import com.simplisticjavachess.board.Position;
import com.simplisticjavachess.evaluation.Evaluation;
import com.simplisticjavachess.evaluation.EvaluationConstantsFactoryImpl;
import com.simplisticjavachess.evaluation.Evaluator;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.movegenerator.MoveGenerator;
import com.simplisticjavachess.piece.Color;

import java.util.Iterator;

public class MinMaxEngine implements Engine
{
    private final static Evaluation EQUAL = EvaluationConstantsFactoryImpl.instance().getEqual();
    private final static Evaluation BLACK_MATE = EvaluationConstantsFactoryImpl.instance().getBlackIsMate();
    private final static Evaluation WHITE_MATE = EvaluationConstantsFactoryImpl.instance().getWhiteIsMate();

    @Override
    public SearchResult search(Position position, MoveGenerator moveGenerator, Evaluator evaluator, int plyDepth)
    {
        if (position.isDrawBy50Move())
        {
            return new SearchResult(EQUAL);
        }

        if (plyDepth == 0)
        {
            return new SearchResult(evaluator.evaluate(position));
        }

        Iterator<Move> moves = moveGenerator.generateMoves(position);
        if (!moves.hasNext())
        {
            if (position.isInCheck())
            {
                return new SearchResult(WHITE_MATE.equals(position.inMove()) ? WHITE_MATE : BLACK_MATE); // Mate
            }
            else
            {
                return new SearchResult(EQUAL);
            }
        }


        Evaluation bestScore = EvaluationConstantsFactoryImpl.instance().getNone();
        MoveSequence moveSequence = new MoveSequence();
        boolean thereWasALegalMove = false;

        Color inMove = position.inMove();


        while (moves.hasNext())
        {
            Move move = moves.next();
            try {
                Position next = Mover.doMove(position, move);
                thereWasALegalMove = true;

                SearchResult score = search(next,  moveGenerator, evaluator, plyDepth - 1);


                if (bestScore.isAnImprovement(inMove, score.getEvaluation()))
                {
                    bestScore = score.getEvaluation();
                    moveSequence = score.getMoveSequence().add(move);
                }

            }
            catch (IllegalMoveException e)
            {
            }
        }


        if (!thereWasALegalMove)
        {
            if (position.isInCheck())
            {
                // mate
                return position.inMove().isWhite() ? new SearchResult(EvaluationConstantsFactoryImpl.instance().getWhiteIsMate()) :
                                                     new SearchResult(EvaluationConstantsFactoryImpl.instance().getBlackIsMate());
            } else
            {
                // draw
                return new SearchResult(EvaluationConstantsFactoryImpl.instance().getEqual());
            }
        }

        return new SearchResult(moveSequence, bestScore);

    }

}

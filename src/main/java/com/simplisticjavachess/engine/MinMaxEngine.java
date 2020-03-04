/**
 *
 * @author Morten Kühnrich
 */


package com.simplisticjavachess.engine;

import com.simplisticjavachess.board.MoveResult;
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
                return new SearchResult(EQUAL); // A draw
            }
        }


        Evaluation bestScore = EvaluationConstantsFactoryImpl.instance().getNone();
        MoveSequence moveSequence = new MoveSequence();
        boolean thereWasALegalMove = false;

        Color inMove = position.inMove();


        while (moves.hasNext())
        {
            Move move = moves.next();
            MoveResult moveResult = Mover.doMove(position, move);
            boolean legal = moveResult.isMoveLegal();

            if (legal)
            {
                Position next = moveResult.getPosition();
                thereWasALegalMove = true;

                SearchResult score = search(next,  moveGenerator, evaluator, plyDepth - 1);


                if (bestScore.isAnImprovement(inMove, score.getEvaluation()))
                {
                    bestScore = score.getEvaluation();
                    moveSequence = score.getMoveSequence().add(move);
                }
            }
        }


        // Mate or draw
        if (!thereWasALegalMove)
        {
            if (position.isInCheck())
            {

                if (position.inMove() == Color.WHITE)
                {
                    return new SearchResult(EvaluationConstantsFactoryImpl.instance().getWhiteIsMate());
                } else
                {
                    return new SearchResult(EvaluationConstantsFactoryImpl.instance().getBlackIsMate());
                }
            } else
            {
                return new SearchResult(EvaluationConstantsFactoryImpl.instance().getEqual());
            } // draw
        }

        return new SearchResult(moveSequence, bestScore);

    }

}

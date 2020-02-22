/**
 *
 * @author Morten Kühnrich
 */


package com.simplisticjavachess.engine;

import com.simplisticjavachess.board.Board;
import com.simplisticjavachess.board.MoveResult;
import com.simplisticjavachess.evaluation.Evaluation;
import com.simplisticjavachess.evaluation.EvaluationConstantsFactoryImpl;
import com.simplisticjavachess.evaluation.Evaluator;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.movegenerator.MoveGenerator;
import com.simplisticjavachess.piece.Color;
import java.util.Iterator;

public class MinMaxEngine implements Engine
{
    @Override
    public SearchResult search(Board board, MoveGenerator moveGenerator, Evaluator evaluator, int depthToGo)
    {     
        if (depthToGo == 0)
        {
            return new SearchResult(
                    board.isDraw() ?
                            EvaluationConstantsFactoryImpl.instance().getEqual() :
                    evaluator.evaluate(board)
            );
        }

        Iterator<Move> moves = moveGenerator.generateMoves(board);

        Color inMove = board.inMove();

        if (evaluator.evaluate(board).equals(EvaluationConstantsFactoryImpl.instance().getBlackIsMate())
         || evaluator.evaluate(board).equals(EvaluationConstantsFactoryImpl.instance().getWhiteIsMate()))
        {
            return new SearchResult(evaluator.evaluate(board));
        }

        if (!moves.hasNext())
        {
            return new SearchResult(EvaluationConstantsFactoryImpl.instance().getEqual()); // A draw
        }
       
        boolean thereWasALegalMove = false;

        SearchResult score;
        Evaluation bestScore = EvaluationConstantsFactoryImpl.instance().getNone();
        MoveSequence moveSequence = new MoveSequence();
        
        while (moves.hasNext())
        {
            Move move = moves.next();
            MoveResult moveResult = board.doMove(move);
            boolean legal = moveResult.isMoveLegal();
            Board next = moveResult.getBoard();

            if (!legal)
            {
                continue; // The pseudo legal move m turned out to be illegal.
            }

            thereWasALegalMove = true;

            score = search(next,  moveGenerator, evaluator, depthToGo - 1);

            if (bestScore.isAnImprovement(inMove, score.getEvaluation()))
            {
                bestScore = score.getEvaluation();
                moveSequence = score.getMoveSequence().add(move);
            }
        }
        
        // Mate or draw
        if (!thereWasALegalMove)
        {
            if (board.isInCheck(inMove))
            {

                if (inMove == Color.WHITE)
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

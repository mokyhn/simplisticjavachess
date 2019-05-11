/**
 *
 * @author Morten Kühnrich
 */


package com.simplisticjavachess.engine;

import com.simplisticjavachess.board.Board;
import com.simplisticjavachess.evaluator.Evaluation;
import com.simplisticjavachess.game.GameResult;
import com.simplisticjavachess.evaluator.Evaluator;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.movegenerator.MoveGenerator;
import com.simplisticjavachess.piece.Color;
import java.util.Iterator;

public class MinMaxEngine implements Engine
{
    private final MoveGenerator moveGenerator;
    private final Evaluator evaluator;

    private Move strongestMove;

    public MinMaxEngine(MoveGenerator moveGenerator, Evaluator evaluator)
    {
        this.moveGenerator = moveGenerator;
        this.evaluator = evaluator;
    }

    @Override
    public final SearchResult search(Board board, int plyDepth)
    {
        return minMaxSearch(board, plyDepth);
    }
   
    private SearchResult minMaxSearch(Board board, int depthToGo)
    {     
        if (depthToGo == 0)
        {
            return new SearchResult(
                    board.isDraw() ?
                    Evaluation.EQUAL : 
                    evaluator.evaluate(board)
            );
        }

        Iterator<Move> moves = moveGenerator.generateMoves(board);

        Color inMove = board.inMove();

        if (evaluator.evaluate(board).equals(Evaluation.BLACK_IS_MATED)
         || evaluator.evaluate(board).equals(Evaluation.WHITE_IS_MATED))
        {
            return new SearchResult(evaluator.evaluate(board));
        }

        if (!moves.hasNext())
        {
            return new SearchResult(Evaluation.EQUAL); // A draw
        }
       
        boolean thereWasALegalMove = false;

        SearchResult score;
        Evaluation bestScore = Evaluation.NONE;
        MoveSequence moveSequence = new MoveSequence();
        
        while (moves.hasNext())
        {
            Move move = moves.next();
            Board next = new Board(board);
            boolean legal = next.doMove(move);

            if (!legal)
            {
                continue; // The pseudo legal move m turned out to be illegal.
            }

            thereWasALegalMove = true;

            score = minMaxSearch(next, depthToGo - 1);

            if (bestScore.isAnImprovement(inMove, score.getEvaluation()))
            {
                strongestMove = move;
                bestScore = score.getEvaluation();
                moveSequence = score.getMoveSequence().add(move);
            }
        }
        
        // Mate or draw
        if (!thereWasALegalMove)
        {
            if (board.isInCheck(inMove))
            {
                    
                board.setGameResult(GameResult.MATE);

                if (inMove == Color.WHITE)
                {
                    return new SearchResult(Evaluation.WHITE_IS_MATED);
                } else
                {
                    return new SearchResult(Evaluation.BLACK_IS_MATED);
                }
            } else
            {
                board.setGameResult(GameResult.STALE_MATE);
                return new SearchResult(Evaluation.EQUAL);
            } // draw
        }

        return new SearchResult(moveSequence, bestScore);

    }

    public Move getStrongestMove()
    {
        return strongestMove;
    }

}

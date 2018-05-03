/**
 *
 * @author Morten Kühnrich
 */
package com.simplisticjavachess.search;

import com.simplisticjavachess.board.Board;
import com.simplisticjavachess.evaluator.Evaluation;
import com.simplisticjavachess.game.GameResult;
import com.simplisticjavachess.evaluator.Evaluator;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.movegenerator.MoveGenerator;
import com.simplisticjavachess.piece.Color;
import java.util.Iterator;

public class AlphaBetaSearch implements Search
{
    MoveGenerator moveGenerator = new MoveGenerator();

    Board analyzeBoard;
    Move strongestMove;
    
    @Override
    public SearchResult search(Board board, int plyDepth)
    {
        analyzeBoard = new Board(board);
        int evaluation = alphaBetaSearch(plyDepth, plyDepth, Integer.MIN_VALUE, Integer.MAX_VALUE);
        return new SearchResult(strongestMove, new Evaluation(evaluation));
    }
  
    private int alphaBetaSearch(int currentPlyDepth, int totalPlyDepth, int alpha, int beta)
    {
        Boolean thereWasALegalMove = false;
        final Color inMove = analyzeBoard.inMove();

        if (analyzeBoard.isDraw())
        {
            return 0;
        }

        if (currentPlyDepth == 0)
        {
            return Evaluator.evaluate(analyzeBoard);
        }

        Iterator<Move> moveIterator = moveGenerator.generateMoves(analyzeBoard);

        boolean legal;

        while (moveIterator.hasNext())
        {
            Move m = moveIterator.next();

            legal = analyzeBoard.doMove(m);

            if (!legal)
            {
                analyzeBoard.undo();
                continue; // The pseudo legal move m turned out to be illegal.
                
            }
            thereWasALegalMove = true;

            int variantEvaluation = alphaBetaSearch(currentPlyDepth - 1, totalPlyDepth, alpha, beta);

            analyzeBoard.undo();

            if (inMove == Color.WHITE)
            {
                if (variantEvaluation > alpha)
                {
                    alpha = variantEvaluation;
                    if (currentPlyDepth == totalPlyDepth)
                    {

                        strongestMove = m;
                    }
                }
                if (alpha >= beta)
                {
                    return beta;
                }

            } else
            {
                if (variantEvaluation < beta)
                {
                    if (currentPlyDepth == totalPlyDepth)
                    {
                        strongestMove = m;
                    }
                    beta = variantEvaluation;
                }
                if (beta <= alpha)
                {
                    return alpha;
                }
            }
        }

        // Mate or draw
        if (!thereWasALegalMove)
        {
            if (analyzeBoard.isInCheck(inMove))
            {
                analyzeBoard.setGameResult(GameResult.MATE);

                if (inMove == Color.WHITE)
                {
                    return Evaluator.WHITE_IS_MATED;
                } else
                {
                    return Evaluator.BLACK_IS_MATED;
                }
            } 
            else
            {
                analyzeBoard.setGameResult(GameResult.STALE_MATE);
                return 0;
            }
        }

        return inMove == Color.WHITE ? alpha : beta;
    }

    public Move getStrongestMove()
    {
        return strongestMove;
    }
}


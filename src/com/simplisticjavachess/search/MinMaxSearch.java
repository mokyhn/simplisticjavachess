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

public class MinMaxSearch implements Search
{
    MoveGenerator moveGenerator = new MoveGenerator();
    
    Board analyzeBoard;
    Move strongestMove;

    @Override
    public final SearchResult search(Board board, int plyDepth)
    {
        analyzeBoard = new Board(board);
        return minMaxSearch(plyDepth);
    }
   
    private SearchResult minMaxSearch(int depthToGo)
    {     
        if (depthToGo == 0)
        {
            return new SearchResult(
                    analyzeBoard.isDraw() ? 
                    Evaluation.EQUAL : 
                    Evaluator.evaluate(analyzeBoard)
            );
        }

        Iterator<Move> moves = moveGenerator.generateMoves(analyzeBoard);

        Color inMove = analyzeBoard.inMove();

        if (Evaluator.evaluate(analyzeBoard).equals(Evaluation.BLACK_IS_MATED)
         || Evaluator.evaluate(analyzeBoard).equals(Evaluation.WHITE_IS_MATED))
        {
            return new SearchResult(Evaluator.evaluate(analyzeBoard));
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
            boolean legal = analyzeBoard.doMove(move);

            if (!legal)
            {
                analyzeBoard.undo();
                continue; // The pseudo legal move m turned out to be illegal.
            }

            thereWasALegalMove = true;
            score = minMaxSearch(depthToGo - 1);
            analyzeBoard.undo();

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
            if (analyzeBoard.isInCheck(inMove))
            {
                    
                analyzeBoard.setGameResult(GameResult.MATE);               

                if (inMove == Color.WHITE)
                {
                    return new SearchResult(Evaluation.WHITE_IS_MATED);
                } else
                {
                    return new SearchResult(Evaluation.BLACK_IS_MATED);
                }
            } else
            {
                analyzeBoard.setGameResult(GameResult.STALE_MATE);
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

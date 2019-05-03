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

    private Board analysisBoard;
    private Move strongestMove;

    public MinMaxEngine(MoveGenerator moveGenerator, Evaluator evaluator)
    {
        this.moveGenerator = moveGenerator;
        this.evaluator = evaluator;
    }

    @Override
    public final SearchResult search(Board board, int plyDepth)
    {
        analysisBoard = new Board(board);
        return minMaxSearch(plyDepth);
    }
   
    private SearchResult minMaxSearch(int depthToGo)
    {     
        if (depthToGo == 0)
        {
            return new SearchResult(
                    analysisBoard.isDraw() ?
                    Evaluation.EQUAL : 
                    evaluator.evaluate(analysisBoard)
            );
        }

        Iterator<Move> moves = moveGenerator.generateMoves(analysisBoard);

        Color inMove = analysisBoard.inMove();

        if (evaluator.evaluate(analysisBoard).equals(Evaluation.BLACK_IS_MATED)
         || evaluator.evaluate(analysisBoard).equals(Evaluation.WHITE_IS_MATED))
        {
            return new SearchResult(evaluator.evaluate(analysisBoard));
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
            boolean legal = analysisBoard.doMove(move);

            if (!legal)
            {
                analysisBoard.undo();
                continue; // The pseudo legal move m turned out to be illegal.
            }

            thereWasALegalMove = true;
            score = minMaxSearch(depthToGo - 1);
            analysisBoard.undo();

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
            if (analysisBoard.isInCheck(inMove))
            {
                    
                analysisBoard.setGameResult(GameResult.MATE);

                if (inMove == Color.WHITE)
                {
                    return new SearchResult(Evaluation.WHITE_IS_MATED);
                } else
                {
                    return new SearchResult(Evaluation.BLACK_IS_MATED);
                }
            } else
            {
                analysisBoard.setGameResult(GameResult.STALE_MATE);
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

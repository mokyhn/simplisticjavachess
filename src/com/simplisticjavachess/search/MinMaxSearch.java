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
import java.util.Optional;

public class MinMaxSearch implements Search
{
    MoveGenerator moveGenerator = new MoveGenerator();
    
    Board analyzeBoard;
    Move strongestMove;

    @Override
    public final SearchResult search(Board board, int plyDepth)
    {
        analyzeBoard = new Board(board);
        int evaluation = minMaxSearch(plyDepth, plyDepth);
        return new SearchResult(new MoveSequence(strongestMove), new Evaluation(evaluation));
    }
   
    private int minMaxSearch(int plyDepth, int depthToGo)
    {     
        if (depthToGo == 0)
        {
            return analyzeBoard.isDraw() ? 0 : Evaluator.evaluate(analyzeBoard);
        }

        Iterator<Move> moves = moveGenerator.generateMoves(analyzeBoard);

        Color inMove = analyzeBoard.inMove();

        if (Evaluator.evaluate(analyzeBoard) == Evaluator.BLACK_IS_MATED
         || Evaluator.evaluate(analyzeBoard) == Evaluator.WHITE_IS_MATED)
        {
            return Evaluator.evaluate(analyzeBoard);
        }

        if (!moves.hasNext())
        {
            return 0; // A draw
        }
       
        boolean thereWasALegalMove = false;

        int score;
        Optional<Integer> bestScore = Optional.empty();
           
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
            score = minMaxSearch(plyDepth, depthToGo - 1);
            analyzeBoard.undo();

            if (bestScore.isPresent())
            {
                switch (inMove) 
                {
                    case WHITE:
                        if (score > bestScore.get())
                        {
                            bestScore = Optional.of(score);
                            if (plyDepth == depthToGo)
                            {
                                strongestMove = move;
                            }
                        }
                    break;
                    case BLACK:
                        if (score < bestScore.get())
                        {
                            bestScore = Optional.of(score);
                            if (plyDepth == depthToGo)
                            {
                                strongestMove = move;
                            }
                        }
                }
            }
            else
            {
                bestScore = Optional.of(score);
                if (plyDepth == depthToGo)
                {
                    strongestMove = move;
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
            } else
            {
                analyzeBoard.setGameResult(GameResult.STALE_MATE);
                return 0;
            } // draw
        }

        return bestScore.get();

    }

    public Move getStrongestMove()
    {
        return strongestMove;
    }

}

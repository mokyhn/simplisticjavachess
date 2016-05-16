/**
 *
 * @author Morten Kühnrich
 */


package com.simplisticchess.search;

import com.simplisticchess.GameResult;
import com.simplisticchess.evaluator.Evaluator;
import com.simplisticchess.move.Move;
import com.simplisticchess.piece.Color;
import java.util.Iterator;

public class MinMaxSearch extends AbstractSearch
{
    public final int search()
    {
        System.out.println("MIN-MAX search...");
        return minMaxSearch(_plyDepth, _plyDepth);
    }
   
    private int minMaxSearch(int plyDepth, int depthToGo)
    {     
        boolean firstCalculation = true;

        if (depthToGo == 0)
        {
            noPositions++;
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
        int bestScore = 0;
           
        while (moves.hasNext())
        {
            Move move = moves.next();
            boolean legal = analyzeBoard.doMove(move);

            if (!legal)
            {
                analyzeBoard.undo();
                continue; // The pseudo legal move m turned out to be illegal.
            }                //Nice verbose trace: System.out.print("(" + (plyDepth-depthToGo) +": "+ m.toString());

            thereWasALegalMove = true;
            score = minMaxSearch(plyDepth, depthToGo - 1);
            analyzeBoard.undo();
            //Nice verbose trace: System.out.println(")");
            if (firstCalculation)
            {
                bestScore = score;
                if (plyDepth == depthToGo)
                {
                    strongestMove = move;
                }
                firstCalculation = false;
            } 
            else
            {
                switch (inMove) 
                {
                    case WHITE:
                        if (score > bestScore)
                        {
                            bestScore = score;
                            if (plyDepth == depthToGo)
                            {
                                strongestMove = move; // Used to extract strongest move
                            }
                        }
                    break;
                    case BLACK:
                        if (score < bestScore)
                        {
                            bestScore = score;
                            if (plyDepth == depthToGo)
                            {
                                strongestMove = move; // Used to extract strongest move
                            }
                        }
                }
            }
        }
        
        int distanceToRoot = plyDepth - depthToGo;
        // Mate or draw
        if (!thereWasALegalMove)
        {
            if (analyzeBoard.isInCheck(inMove))
            {
                analyzeBoard.setGameResult(GameResult.MATE);               
                //System.out.println("Matefound:\n" + analyzeBoard.toString());
                if (inMove == Color.WHITE)
                {
                    return Evaluator.WHITE_IS_MATED + distanceToRoot;
                } else
                {
                    return Evaluator.BLACK_IS_MATED - distanceToRoot;
                }
            } else
            {
                analyzeBoard.setGameResult(GameResult.STALE_MATE);
                return 0;
            } // draw
        }

        return bestScore;

    }

}

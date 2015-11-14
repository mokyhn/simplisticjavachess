package com.simplisticchess.search;

import com.simplisticchess.evaluator.Evaluator;
import com.simplisticchess.movegenerator.MoveGenerator;
import com.simplisticchess.move.Move;
import com.simplisticchess.piece.Color;
import java.util.ArrayList;

/**
 *
 * @author Morten Kühnrich
 */
public class MinMaxSearch extends AbstractSearch
{

    /**
     * Reference implementation of Min-Max search without fancy optimizations
     * and tricks. This method can be used to compare the soundness of other
     * search methods
     *
     * @param plyDepth The overall search depth in ply's
     * @param depthToGo The currently searched depth
     * @return The score. A positive value means white advantage, a negative
     * denotes black advantage and the score 0 denotes equal play.
     */
    private int minMaxSearch(int plyDepth, int depthToGo)
    {
        ArrayList<Move> moves;
        Move m;
        int score = 0,
                bestScore = 0;
        
        boolean firstCalculation = true;
        int distanceToRoot = plyDepth - depthToGo;

        if (depthToGo == 0)
        {
            noPositions++;
            return (analyzeBoard.drawBy3RepetionsRule() || analyzeBoard.drawBy50MoveRule()) ? 0 : Evaluator.evaluate(analyzeBoard);
        }

        moves = MoveGenerator.generateAllMoves(analyzeBoard);

        Color inMove = analyzeBoard.inMove();

        if (Evaluator.evaluate(analyzeBoard) == Evaluator.BLACK_IS_MATED
                || Evaluator.evaluate(analyzeBoard) == Evaluator.WHITE_IS_MATED)
        {
            return Evaluator.evaluate(analyzeBoard);
        }

        if (moves.isEmpty())
        {
            return 0; // A draw
        }
        boolean result;
        boolean thereWasALegalMove = false;
        for (int i = 0; i < moves.size(); i++)
        {

            m = moves.get(i);
            result = analyzeBoard.performMove(m);

            if (result == false)
            {
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
                    strongestMove = m;
                }
                firstCalculation = false;
            } else
            {
                if (inMove == Color.WHITE)
                {
                    if (score > bestScore)
                    {
                        bestScore = score;
                        if (plyDepth == depthToGo)
                        {
                            strongestMove = m; // Used to extract strongest move
                        }
                    }
                }

                if (inMove == Color.BLACK)
                {
                    if (score < bestScore)
                    {
                        bestScore = score;
                        if (plyDepth == depthToGo)
                        {
                            strongestMove = m; // Used to extract strongest move
                        }
                    }
                }
            }

        }
        // Mate or draw
        if (!thereWasALegalMove)
        {
            if (analyzeBoard.isInCheck(inMove))
            {
                analyzeBoard.setMate();
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
                analyzeBoard.setDraw();
                return 0;
            } // draw
        }

        return bestScore;

    }

    public final int search()
    {
        System.out.println("MIN-MAX search...");
        return minMaxSearch(_plyDepth, _plyDepth);
    }
}

/*
 * @author Morten Kühnrich
 */

package com.simplisticjavachess.search;

import com.simplisticjavachess.board.Board;
import com.simplisticjavachess.movegenerator.MoveGenerator;
import com.simplisticjavachess.move.Move;

public abstract class AbstractSearch
{
    protected Board analyzeBoard;
    protected int plyDepth;
    protected Move strongestMove;
    private int finalEvaluation;

    protected MoveGenerator moveGenerator = new MoveGenerator();
    
    private long startTime;
    private long endTime;
    protected int noPositions;
    protected int noCutOffs;

    public AbstractSearch()
    {
        plyDepth = 3;
    }    
    
    public void setBoard(Board b)
    {
        analyzeBoard = new Board(b);
    }

    public void setPlyDepth(int pd)
    {
        assert (pd >= 0 && pd <= 20);
        plyDepth = pd;
    }
    
    public abstract int search();

    public int dosearch() throws Exception
    {
        if (analyzeBoard == null)
        {
            throw new Exception("Supply a board to be analyzed first.");
        }

        finalEvaluation = 0;
        noPositions = 0;
        noCutOffs = 0;
        startTime = System.nanoTime();

        strongestMove = null;

        if (analyzeBoard.isDraw() || analyzeBoard.isMate())
        {
            return 0;
        }

        finalEvaluation = search();

        endTime = System.nanoTime();

        return finalEvaluation;
    }
    
    public Move getStrongestMove()
    {
        return strongestMove;
    }

    public int getNoPositions()
    {
        return noPositions;
    }

    public long getTimeUsage()
    {
        return Math.abs(endTime - startTime) / 1000000;
    }
  
    public String getStatistics()
    {
        String strongestMoveStr = "null";

        if (strongestMove != null)
        {
            strongestMoveStr = strongestMove.toString();
        }

        return ("move " + strongestMoveStr
                + " Evaluation " + finalEvaluation
                + " at " + plyDepth
                + " ply in " + noPositions
                + " positions in " + getTimeUsage()
                + " mSecs = " + ((float) noPositions / (float) getTimeUsage())
                + " kN/s with " + noCutOffs
                + " cutoffs ");
    } 
}

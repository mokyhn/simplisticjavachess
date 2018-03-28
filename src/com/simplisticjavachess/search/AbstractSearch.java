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
        plyDepth = pd;
    }
    
    public abstract int search();

    public int dosearch() throws Exception
    {
        finalEvaluation = 0;
        
        strongestMove = null;

        if (analyzeBoard.isDraw() || analyzeBoard.isMate())
        {
            return 0;
        }

        finalEvaluation = search();

        return finalEvaluation;
    }
    
    public Move getStrongestMove()
    {
        return strongestMove;
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
                + " ply" );
    } 
}

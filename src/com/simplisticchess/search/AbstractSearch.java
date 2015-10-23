/*
 * @author Morten Kühnrich
 * 2013
 */
package com.simplisticchess.search;

import com.simplisticchess.board.Board;
import com.simplisticchess.movegenerator.Movegenerator;
import com.simplisticchess.move.Move;
import java.util.Iterator;

public abstract class AbstractSearch {
    // Main variables used in the search
    protected Board analyzeBoard;
    protected int   _plyDepth;
    protected Move  strongestMove;
    private int     _finalEvaluation;


    // For statistical pusposes
    private long   startTime;
    private long   endTime;
    protected int  noPositions;
    protected int  noCutOffs;
    protected int  wastedGeneratedMoves;

    // Setters
    public void setBoard(Board b) 
    { 
        analyzeBoard = new Board(b);
    }
    
    // Getters
    public Move getStrongestMove() { return strongestMove;  }
    public int  getNoPositions()   { return noPositions;    }
    public long getTimeUsage()     { return Math.abs(endTime-startTime)/1000000;}

    public String getStatistics() {
        String strongestMoveStr = "null";
        
        if (strongestMove != null) 
        {
            strongestMoveStr = strongestMove.toString();
        }
        
        return("move "          + strongestMoveStr +
               " Evaluation "   + _finalEvaluation +
               " at "           + _plyDepth +
               " ply in "       + noPositions +
               " positions in " + getTimeUsage() 
               + " mSecs = "    + ((float) noPositions/(float) getTimeUsage())
               + " kN/s with "  + noCutOffs +
               " cutoffs " +
               " wasted moves " + wastedGeneratedMoves );
    }   
 
    // Constructor
    public AbstractSearch() {
          noPositions          = 0;
          noCutOffs            = 0;
          _finalEvaluation     = 0;
          wastedGeneratedMoves = 0;
          strongestMove        = null;
    }

    public void setPlyDepth(int pd) {
         assert(pd >= 0 && pd <= 20);
        _plyDepth = pd;
    }
    
    public int dosearch() throws Exception {
        if (analyzeBoard == null) 
        {
          throw new Exception("Supply a board to be analyzed first.");
        }
        
        _finalEvaluation     = 0;
        noPositions          = 0;
        noCutOffs            = 0;
        wastedGeneratedMoves = 0;        
        startTime            = System.nanoTime();

        strongestMove        = null;
        
        
        
        if (analyzeBoard.isDraw() || analyzeBoard.isMate()) {
         System.out.println("Sorry, the game has ended...");
         return 0;
        }        
        
        
        
        _finalEvaluation = search();        
        
        endTime = System.nanoTime();
        
        
        return _finalEvaluation;
    }

    // Abstract method that is requiered from the implementor.
    public abstract int search();

    public String findBranchingFactor(Board b, int ply) {
        analyzeBoard  = new Board(b);
        noPositions   = 0;

        countNodesTmp(ply);
        return "#Nodes "                   + noPositions +
               " at plydepth "             + ply +
               " = a branching factor of " + Math.exp(Math.log(noPositions)/ply) +
               " nodes";
    }
   
    private void countNodesTmp(int plydepth) {
        Iterator<Move>          moves;

        if (plydepth == 0) {
          noPositions++;
          return;
        }
        
        moves = Movegenerator.generateAllMoves(analyzeBoard).listIterator();
        while (moves.hasNext()) {
            analyzeBoard.performMove(moves.next());
            countNodesTmp(plydepth - 1);
            analyzeBoard.retractMove();
        }
    }    
}

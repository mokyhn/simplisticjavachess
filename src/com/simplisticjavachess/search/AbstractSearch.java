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
    protected Move strongestMove;
    
    protected MoveGenerator moveGenerator = new MoveGenerator();
       
    public void setBoard(Board b)
    {
        analyzeBoard = new Board(b);
    }
 
    public abstract int search(int plyDepth);
  
    public Move getStrongestMove()
    {
        return strongestMove;
    }
}

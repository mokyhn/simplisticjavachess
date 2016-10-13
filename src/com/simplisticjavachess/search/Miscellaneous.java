/**
 *
 * @author Morten Kühnrich
 */
package com.simplisticjavachess.search;

import com.simplisticjavachess.board.Board;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.movegenerator.MoveGenerator;
import java.util.Iterator;

public class Miscellaneous
{

    int noPositions = 0;
    Board analyzeBoard;
    MoveGenerator moveGenerator;

    public Miscellaneous()
    {
        moveGenerator = new MoveGenerator();
    }

    public String findBranchingFactor(Board b, int ply)
    {
        analyzeBoard = new Board(b);
        noPositions = 0;

        countNodesTmp(ply);
        return "#Nodes " + noPositions
                + " at plydepth " + ply
                + " = a branching factor of " + Math.exp(Math.log(noPositions) / ply)
                + " nodes";
    }

    private void countNodesTmp(int plydepth)
    {
        Iterator<Move> moves;

        if (plydepth == 0)
        {
            noPositions++;
            return;
        }

        moves = moveGenerator.generateMoves(analyzeBoard);
        while (moves.hasNext())
        {
            analyzeBoard.doMove(moves.next());
            countNodesTmp(plydepth - 1);
            analyzeBoard.undo();
        }
    }
}

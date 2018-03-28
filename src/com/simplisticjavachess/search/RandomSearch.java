/**
 *
 * @author Morten Kühnrich
 */

package com.simplisticjavachess.search;

import com.simplisticjavachess.game.GameResult;
import com.simplisticjavachess.evaluator.Evaluator;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.movegenerator.MoveGenerator;
import com.simplisticjavachess.piece.Color;
import java.util.ArrayList;
import java.util.Iterator;

public class RandomSearch extends AbstractSearch
{
    @Override
    public final int search(int plyDepth)
    {
        return randomSearch();
    }
    
    private int randomSearch()
    {
        Iterator<Move> movesTmp = new MoveGenerator().generateMoves(analyzeBoard);
        ArrayList<Move> moves = new ArrayList<Move>();
        
        while (movesTmp.hasNext()) 
        {
            moves.add(movesTmp.next());
        }
        
        int n = moves.size();

        if (n == 0)
        {
            return 0;
        }

        if (analyzeBoard.isDraw() || analyzeBoard.isMate())
        {
            System.out.println("Game over...");
            System.exit(0);
        }

        final Color whoMoves = analyzeBoard.inMove();
        boolean legal;

        boolean retry = true;

        while (retry && n > 0)
        {
            double r = Math.random();
            Move theMove = moves.get((int) Math.ceil((n - 1) * r));
            legal = analyzeBoard.doMove(theMove);

            if (legal)
            {
                retry = false;
                strongestMove = theMove;
                analyzeBoard.undo();
            } 
            else
            {
                analyzeBoard.undo();
                moves.remove(theMove);
                n = moves.size();
                System.out.println("Neglected: " + theMove.toString());
            }
        }

        if (retry == true)
        {
            if (analyzeBoard.isInCheck(whoMoves))
            {
                analyzeBoard.setGameResult(GameResult.MATE);               
                if (whoMoves == Color.WHITE)
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
            }
        }

        return Evaluator.evaluate(analyzeBoard);
    }

}

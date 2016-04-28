/**
 *
 * @author Morten Kühnrich
 */

package com.simplisticchess.search;

import com.simplisticchess.GameResult;
import com.simplisticchess.evaluator.Evaluator;
import com.simplisticchess.move.Move;
import com.simplisticchess.movegenerator.MoveGenerator;
import com.simplisticchess.piece.Color;
import java.util.ArrayList;
import java.util.Iterator;

public class RandomSearch extends AbstractSearch
{

    private int randomSearch()
    {
        strongestMove = null;

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
        boolean result;

        boolean retry = true;

        while (retry && n > 0)
        {
            double r = Math.random();
            Move theMove = moves.get((int) Math.ceil((n - 1) * r));
            result = analyzeBoard.doMove(theMove);

            if (result == false)
            {
                moves.remove(theMove);
                n = moves.size();
                System.out.println("Neglected: " + theMove.toString());
            } else
            {
                retry = false;
                strongestMove = theMove;
                analyzeBoard.undo();
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

    public final int search()
    {
        System.out.println("Random search...");
        return randomSearch();
    }
}

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
public class RandomSearch extends AbstractSearch
{

    private int randomSearch()
    {
        strongestMove = null;

        ArrayList<Move> moves = MoveGenerator.generateAllMoves(analyzeBoard);
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
            result = analyzeBoard.performMove(theMove);

            if (result == false)
            {
                moves.remove(theMove);
                n = moves.size();
                System.out.println("Neglected: " + theMove.toString());
            } else
            {
                retry = false;
                strongestMove = theMove;
                analyzeBoard.retractMove();
            }
        }

        if (retry == true)
        {
            if (analyzeBoard.isInCheck(whoMoves))
            {
                analyzeBoard.setMate();
                if (whoMoves == Color.WHITE)
                {
                    return Evaluator.WHITE_IS_MATED;
                } else
                {
                    return Evaluator.BLACK_IS_MATED;
                }
            } else
            {
                analyzeBoard.setDraw();
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

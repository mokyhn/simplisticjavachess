/**
 *
 * @author Morten Kühnrich
 */
package com.simplisticchess.search;

import com.simplisticchess.evaluator.Evaluator;
import com.simplisticchess.move.Move;
import com.simplisticchess.piece.Color;
import java.util.Iterator;

public class AlphaBetaSearch extends AbstractSearch
{

    private int alphaBetaSearch(int currentPlyDepth, int totalPlyDepth, int alpha, int beta)
    {
        Boolean thereWasALegalMove = false;
        final Color inMove = analyzeBoard.inMove();
        int distanceToRoot = totalPlyDepth - currentPlyDepth;

        // Assertions
        assert (currentPlyDepth <= totalPlyDepth);
        assert (currentPlyDepth >= 0 && totalPlyDepth >= 0);

        //--------------------Part 1. Immediate evaluations of different kinds
        // If the game has ended return immediately.
        if (analyzeBoard.isDraw())
        {
            return 0;
        }

        if (analyzeBoard.isMate())
        {
            if (inMove == Color.WHITE)
            {
                return Evaluator.WHITE_IS_MATED + distanceToRoot;
            } else
            {
                return Evaluator.BLACK_IS_MATED - distanceToRoot;
            }
        }

        // Test for other kinds of draw.
        if (analyzeBoard.drawBy50MoveRule() || analyzeBoard.drawBy3RepetionsRule())
        {
            analyzeBoard.setDraw();
            return 0;
        }

        if (currentPlyDepth == 0)
        {
            noPositions++;
            //System.out.print("<d0 " );

            //System.out.print(" evaluation=\""+ humanReadable(Evaluator.evaluate(analyzeBoard)) + "," + humanReadable(alpha) + "," + humanReadable(beta) + "\">");
            //System.out.print("</d0>");
            return Evaluator.evaluate(analyzeBoard);
        }

        //--------------------Part 2. Move generation and variation search
        Iterator<Move> moveIterator = moveGenerator.generateMoves(analyzeBoard);

        boolean result;

        while (moveIterator.hasNext())
        {
            Move m = moveIterator.next();

            result = analyzeBoard.doMove(m);

            if (result == false)
            {
                continue; // The pseudo legal move m turned out to be illegal.
            }
            thereWasALegalMove = true;

            //String movestr = m.toString(); //m.toString().replace('-', 'z')).replace('=', 'z');

            //String sb = "<" + movestr + ">";
            //String eb = "</" + movestr + ">";

            //System.out.print(sb + m.toString() );
            int variantEvaluation = alphaBetaSearch(currentPlyDepth - 1, totalPlyDepth, alpha, beta);

             //System.out.print(" is " + humanReadable(variantEvaluation) + "," + humanReadable(alpha) + "," + humanReadable(beta));
            //System.out.print(eb);
            analyzeBoard.undo();

            if (inMove == Color.WHITE)
            {
                if (variantEvaluation > alpha)
                {
                    alpha = variantEvaluation;
                    if (currentPlyDepth == totalPlyDepth)
                    {

                        strongestMove = m;
                    }
                }
                if (alpha >= beta)
                {
                    noCutOffs++;
                    return beta;
                }

            } else
            {
                if (variantEvaluation < beta)
                {
                    if (currentPlyDepth == totalPlyDepth)
                    {
                        strongestMove = m;
                    }
                    beta = variantEvaluation;
                }
                if (beta <= alpha)
                {
                    noCutOffs++;
                    return alpha;
                }
            }
        }
        //System.out.println();
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

        return inMove == Color.WHITE ? alpha : beta;
    }

    public final int search()
    {
        System.out.println("Alpha-Beta search...");
        return alphaBetaSearch(_plyDepth, _plyDepth, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
}

                             //if (m.toString().contains("e5")) { 
//    System.out.printf("\nBLACK: move = %s, Eval=%d, alpha=%d, beta=%d, sd = %d\n", m.toString(), eval , alpha, beta, currentPlyDepth);
// }
//if (m.toString().contains("h6") ) { 
//    System.out.printf("\nBLACK: move = %s, Eval=%d, alpha=%d, beta=%d, sd = %d\n", m.toString(), eval , alpha, beta, currentPlyDepth);
//}
/* if (m.toString().contains("a5") && m.toString().contains("a6")) {
 System.out.println("WHITE: Eval, alpha = " + alpha +
 " beta = " + beta +
 " currentDepth " + currentPlyDepth +
 " evaluation = " + evaluation + " " +
 " move " + m.toString() +
 analyzeBoard.toString() + "\n\n\n\n");
 }*/

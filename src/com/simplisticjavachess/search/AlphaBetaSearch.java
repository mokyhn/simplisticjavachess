/**
 *
 * @author Morten Kühnrich
 */
package com.simplisticjavachess.search;

import com.simplisticjavachess.game.GameResult;
import com.simplisticjavachess.evaluator.Evaluator;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.piece.Color;
import java.util.Iterator;

public class AlphaBetaSearch extends AbstractSearch
{
    @Override
    public final int search()
    {
        return alphaBetaSearch(plyDepth, plyDepth, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
  
    private int alphaBetaSearch(int currentPlyDepth, int totalPlyDepth, int alpha, int beta)
    {
        Boolean thereWasALegalMove = false;
        final Color inMove = analyzeBoard.inMove();
        int distanceToRoot = totalPlyDepth - currentPlyDepth;

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

        if (currentPlyDepth == 0)
        {
            noPositions++;
            //System.out.print("<d0 " );

            //System.out.print(" evaluation=\""+ humanReadable(Evaluator.evaluate(analyzeBoard)) + "," + humanReadable(alpha) + "," + humanReadable(beta) + "\">");
            //System.out.print("</d0>");
            return Evaluator.evaluate(analyzeBoard);
        }

        Iterator<Move> moveIterator = moveGenerator.generateMoves(analyzeBoard);

        boolean legal;

        while (moveIterator.hasNext())
        {
            Move m = moveIterator.next();

            legal = analyzeBoard.doMove(m);

            if (!legal)
            {
                analyzeBoard.undo();
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
                analyzeBoard.setGameResult(GameResult.MATE);
                //System.out.println("Matefound:\n" + analyzeBoard.toString());
                if (inMove == Color.WHITE)
                {
                    return Evaluator.WHITE_IS_MATED + distanceToRoot;
                } else
                {
                    return Evaluator.BLACK_IS_MATED - distanceToRoot;
                }
            } 
            else
            {
                analyzeBoard.setGameResult(GameResult.STALE_MATE);
                return 0;
            }
        }

        return inMove == Color.WHITE ? alpha : beta;
    }

}


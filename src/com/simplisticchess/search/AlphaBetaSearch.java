package com.simplisticchess.search;

/**
 *
 * @author Morten Kühnrich
 */
import com.simplisticchess.evaluate.Evaluator;
import com.simplisticchess.movegenerator.Movegenerator;
import com.simplisticchess.move.Move;
import com.simplisticchess.piece.Piece.Color;
import java.util.ArrayList;

public class AlphaBetaSearch extends AbstractSearch
{

    private int alphaBetaSearchOld(int currentPlyDepth, int totalPlyDepth, int alpha, int beta)
    {
        ArrayList<Move> moves;
        Move m;
        int evaluation;
        Boolean thereWasALegalMove = false;
        final Color inMove = analyzeBoard.inMove();
        int distanceToRoot = totalPlyDepth - currentPlyDepth;

        // Assertions
        assert (currentPlyDepth <= totalPlyDepth);
        assert (currentPlyDepth >= 0 && totalPlyDepth >= 0);

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
            return Evaluator.evaluate(analyzeBoard);
        }

        /* Not needed...
         if (Evaluator.evaluate(analyzeBoard) == Evaluator.BLACK_IS_MATED || 
         Evaluator.evaluate(analyzeBoard) == Evaluator.WHITE_IS_MATED)  {               
         return Evaluator.evaluate(analyzeBoard);
         }*/
        moves = Movegenerator.generateAllMoves(analyzeBoard);

        /* Not needed...
         if (moves.isEmpty()) {  // A draw
         return 0;
         } */
        evaluation = 0;
        int i;
        for (i = 0; (i < moves.size() && alpha < beta); i++)
        {  // TODO: Is this condition right?
            m = moves.get(i);
            /*if (m.toString().contains("a5") && m.toString().contains("a6")) {
             System.out.println("WHITE: Eval, alpha = " + evaluation + ", " + alpha + " " + m.toString());
             }*/
            analyzeBoard.performMove(m);
            if (analyzeBoard.isInCheck(m.whoMoves))
            {
                analyzeBoard.retractMove(); // The move was not legal
                continue;                   // Try next pseudolegal move
            }
            thereWasALegalMove = true;

            //System.out.print("(" + m.toString());
            evaluation = alphaBetaSearch(currentPlyDepth - 1, totalPlyDepth, alpha, beta);
            //System.out.println(")");
            /* if (m.toString().contains("a5") && m.toString().contains("a6")) {
             System.out.println("WHITE: Eval, alpha = " + alpha +
             " beta = " + beta +
             " currentDepth " + currentPlyDepth +
             " evaluation = " + evaluation + " " +
             " move " + m.toString() +
             analyzeBoard.toString() + "\n\n\n\n");
             }*/
            analyzeBoard.retractMove();

            if (inMove == Color.WHITE)
            {
                if (evaluation > alpha)
                {
                    alpha = evaluation;
                    if (currentPlyDepth == totalPlyDepth)
                    {
                        //System.out.println("WHITE: Eval, alpha = " + eval + ", " + alpha + " " + m.toString());
                        strongestMove = m;
                    }
                }
            } else
            {
                if (evaluation < beta)
                {
                    if (currentPlyDepth == totalPlyDepth)
                    {
                             //if (m.toString().contains("e5")) { 
                        //    System.out.printf("\nBLACK: move = %s, Eval=%d, alpha=%d, beta=%d, sd = %d\n", m.toString(), eval , alpha, beta, currentPlyDepth);
                        // }
                        //if (m.toString().contains("h6") ) { 
                        //    System.out.printf("\nBLACK: move = %s, Eval=%d, alpha=%d, beta=%d, sd = %d\n", m.toString(), eval , alpha, beta, currentPlyDepth);
                        //}
                        strongestMove = m;
                    }
                    beta = evaluation;
                }
            }
        }

        // For the purpose of statistics
        if (alpha >= beta)
        {
            wastedGeneratedMoves = wastedGeneratedMoves + (moves.size()) - i;
            noCutOffs++;
        }

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

    private String humanReadable(Integer v)
    {

        if (v == Integer.MAX_VALUE)
        {
            return "+inf";
        }
        if (v == Integer.MIN_VALUE)
        {
            return "-inf";
        }
        return v.toString();
    }

    //TODO: Rework this algorithm. The algorithm above is the "old" almost working...
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
        ArrayList<Move> possibleMoves = Movegenerator.generateAllMoves(analyzeBoard);

        int i;

        boolean result;

        for (i = 0; (i < possibleMoves.size()); i++)
        {
            Move m = possibleMoves.get(i);

            result = analyzeBoard.performMove(m);

            if (result == false)
            {
                continue; // The pseudo legal move m turned out to be illegal.
            }
            thereWasALegalMove = true;

            String movestr = m.toString(); //m.toString().replace('-', 'z')).replace('=', 'z');

            String sb = "<" + movestr + ">";
            String eb = "</" + movestr + ">";

            //System.out.print(sb + m.toString() );
            int variantEvaluation = alphaBetaSearch(currentPlyDepth - 1, totalPlyDepth, alpha, beta);

             //System.out.print(" is " + humanReadable(variantEvaluation) + "," + humanReadable(alpha) + "," + humanReadable(beta));
            //System.out.print(eb);
            analyzeBoard.retractMove();

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
                    wastedGeneratedMoves = wastedGeneratedMoves + (possibleMoves.size()) - i;
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
                    wastedGeneratedMoves = wastedGeneratedMoves + (possibleMoves.size()) - i;
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

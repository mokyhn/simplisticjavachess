/**
 *
 * @author moku
 * 2013
 */

package sjc;

import java.util.ArrayList;

public class AlphaBetaSearch extends AbstractSearch {
        private int alphaBetaSearch(int currentPlyDepth, int totalPlyDepth, int alpha, int beta) {
        ArrayList<Move> moves;
        Move            m;
        int             evaluation;
        Boolean         thereWasALegalMove = false;
        final int       inMove = analyzeBoard.inMove();
        int             distanceToRoot = totalPlyDepth - currentPlyDepth;
        
        // Assertions
        assert(currentPlyDepth <= totalPlyDepth);
        assert(currentPlyDepth >= 0 && totalPlyDepth >= 0);
        
        // If the game has ended return immediately.
        if (analyzeBoard.isDraw()) return 0;
        if (analyzeBoard.isMate()) {
            if (inMove == Piece.WHITE) return Evaluator.WHITE_IS_MATED+distanceToRoot;
            else return Evaluator.BLACK_IS_MATED-distanceToRoot;
        }
        
        // Test for other kinds of draw.
        if (analyzeBoard.drawBy50MoveRule() || analyzeBoard.drawBy3RepetionsRule()) {
             analyzeBoard.setDraw();
             return 0;
        }
        
        if (currentPlyDepth == 0) {
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
        for (i = 0; (i < moves.size() && alpha < beta ); i++) {  
            m = moves.get(i);
            /*if (m.toString().contains("a5") && m.toString().contains("a6")) {
             System.out.println("WHITE: Eval, alpha = " + evaluation + ", " + alpha + " " + m.toString());
            }*/
            analyzeBoard.performMove(m);
            if (analyzeBoard.isInCheck(m.whoMoves)) {
              analyzeBoard.retractMove(); // The move was not legal
              continue;                   // Try next pseudolegal move
            }
            thereWasALegalMove = true;
            
            //System.out.print("(" + m.toString());
            evaluation = alphaBetaSearch(currentPlyDepth-1, totalPlyDepth, alpha, beta);
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
            
            if (inMove == Piece.WHITE) {               
                if (evaluation > alpha) {
                 alpha = evaluation;
                 if (currentPlyDepth == totalPlyDepth)  {
                     //System.out.println("WHITE: Eval, alpha = " + eval + ", " + alpha + " " + m.toString());
                     strongestMove = m;
                 }
                }
            } else {
             if (evaluation < beta) {
                 if (currentPlyDepth == totalPlyDepth)  {
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
        if (alpha >=  beta) {
                wastedGeneratedMoves = wastedGeneratedMoves + (moves.size()) - i;
                noCutOffs++;
        }
        
        // Mate or draw
        if (!thereWasALegalMove) {
           if (analyzeBoard.isInCheck(inMove)) {
            analyzeBoard.setMate();            
            //System.out.println("Matefound:\n" + analyzeBoard.toString());
            if (inMove == Piece.WHITE) return Evaluator.WHITE_IS_MATED+distanceToRoot;
            else return Evaluator.BLACK_IS_MATED-distanceToRoot;
           } else {
               analyzeBoard.setDraw();
               return 0;
           } // draw
         }
        
        return inMove == Piece.WHITE ? alpha : beta;
    }

        
    public int search() {
      System.out.println("Alpha-Beta search...");
      return alphaBetaSearch(_plyDepth, _plyDepth, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
}

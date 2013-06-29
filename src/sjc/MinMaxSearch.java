/**
 *
 * @author Morten Kühnrich
 * 2013
 */
package sjc;

import java.util.ArrayList;


public class MinMaxSearch extends AbstractSearch {
     
    /**
     * Reference implementation of Min-Max search witout fancy optimizations and
     * tricks. This method can be used to compare the soundess of other 
     * search methods
     * @param plyDepth The overall search depth in ply's
     * @param depthToGo The currently searched depth
     * @return The score. A positive value means white advantage, a negative
     * denotes black advantage and the score 0 denotes equal play.
     */
    private int minMaxSearch(int plyDepth, int depthToGo) {
       ArrayList<Move> moves;
       Move            m                = null;
       int             score            = 0,
                       bestScore        = 0;
       int             inMove;
       boolean         firstCalculation = true;
             
        if (depthToGo == 0) {
            noPositions++;
            return (analyzeBoard.drawBy3RepetionsRule() || analyzeBoard.drawBy50MoveRule()) ? 0 : Evaluator.evaluate(analyzeBoard);
        }

        moves = Movegenerator.generateAllMoves(analyzeBoard);

        inMove = analyzeBoard.inMove();

        if (Evaluator.evaluate(analyzeBoard) == Evaluator.BLACK_IS_MATED ||
            Evaluator.evaluate(analyzeBoard) == Evaluator.WHITE_IS_MATED)  {
          return Evaluator.evaluate(analyzeBoard);
        }
                
        if (moves.isEmpty()) return 0; // A draw

        for (int i = 0; i < moves.size(); i++) {
                m = moves.get(i);
                analyzeBoard.performMove(m);                
                //Nice verbose trace: System.out.print("(" + (plyDepth-depthToGo) +": "+ m.toString());
                score = minMaxSearch(plyDepth, depthToGo - 1);
                analyzeBoard.retractMove();
                //Nice verbose trace: System.out.println(")");
                if (firstCalculation) {
                 bestScore        = score;
                 if (plyDepth == depthToGo) strongestMove = m;
                 firstCalculation = false;
                } else {
                    if (inMove == Piece.WHITE) {
                     if (score > bestScore) {
                      bestScore = score;
                      if (plyDepth == depthToGo) strongestMove = m; // Used to extract strongest move
                     }
                    }
                    
                    if (inMove == Piece.BLACK) {
                     if (score < bestScore) {
                      bestScore = score;
                      if (plyDepth == depthToGo) strongestMove = m; // Used to extract strongest move
                     }
                    }   
                }

                
        }

        return bestScore;

    }

    public int search() {
     System.out.println("MIN-MAX search...");
     return minMaxSearch(_plyDepth, _plyDepth);
    }
}

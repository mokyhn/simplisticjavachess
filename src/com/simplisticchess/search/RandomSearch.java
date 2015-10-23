package com.simplisticchess.search;

import com.simplisticchess.evaluate.Evaluator;
import com.simplisticchess.movegenerator.Movegenerator;
import com.simplisticchess.piece.Piece;
import com.simplisticchess.move.Move;
import java.util.ArrayList;

/**
 *
 * @author Morten Kühnrich
 */
public class RandomSearch extends AbstractSearch {
   private int randomSearch() {
      ArrayList<Move> moves;
      int             n;
      final int whoMoves;
      double          r = Math.random();
      boolean retry = true;
      Move theMove;
      
      strongestMove = null;
      
      moves = Movegenerator.generateAllMoves(analyzeBoard);
      n     = moves.size();
      
      if (n == 0) return 0;

      if (analyzeBoard.isDraw() || analyzeBoard.isMate()) {
       System.out.println("Game over...");
       System.exit(0);
      }
      
      whoMoves = analyzeBoard.inMove();
      boolean result;
      while (retry && n > 0) {
          r = Math.random();
          theMove = moves.get((int) Math.ceil((n-1)*r));
          result = analyzeBoard.performMove(theMove);
   
          if (result == false)
           {
             moves.remove(theMove);
             n = moves.size();
             System.out.println("Neglected: " + theMove.toString());
          } else {
              retry = false;
              strongestMove = theMove;
              analyzeBoard.retractMove();
          }
      }
      
      if (retry == true) {
          if (analyzeBoard.isInCheck(whoMoves)) {
           analyzeBoard.setMate();
           if (whoMoves == Piece.WHITE) {
            return Evaluator.WHITE_IS_MATED;          
           } else return Evaluator.BLACK_IS_MATED;
          } else {
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

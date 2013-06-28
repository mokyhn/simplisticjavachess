package sjc;

import java.util.ArrayList; 
import java.util.Iterator;

public final class Search {
    // Various serach methods
    public final static int ALPHABETA = 1,
                            MINMAX    = 2,
                            RANDOM    = 3;

   
    // Main variables used in the search
    private Board analyzeBoard;
    private int   _plyDepth;
    private Move  strongestMove;
    private int   searchResult;


    // For statistical pusposes
    private long startTime;
    private long endTime;
    private int  noPositions;
    private int  noCutOffs;
    private int  wastedGeneratedMoves;
    
    public Search() {
          noPositions          = 0;
          noCutOffs            = 0;
          searchResult         = 0;
          wastedGeneratedMoves = 0;
          strongestMove        = null;
    }

    public void setPlyDepth(int pd) {
         assert(pd >= 0 && pd <= 20);
        _plyDepth = pd;
    }
    
    public int dosearch(Board b, int method) {
        analyzeBoard         = b.clone();

        searchResult         = 0;
        noPositions          = 0;
        noCutOffs            = 0;
        wastedGeneratedMoves = 0;        
        startTime           = System.nanoTime();

        strongestMove = null;
        
        if (b.isDraw() || b.isMate()) {
         System.out.println("Sorry, the game has ended...");
         return 0;
        }
        
        switch (method) {
            case ALPHABETA:
                System.out.println("Alpha-Beta search...");
                searchResult = alphaBetaSearch(_plyDepth, _plyDepth, Integer.MIN_VALUE, Integer.MAX_VALUE);

                break;
            case MINMAX:
                System.out.println("MIN-MAX search...");
                searchResult = minMaxSearch(_plyDepth, _plyDepth);
                break;
            case RANDOM:
                System.out.println("Random search...");
                searchResult = randomSearch();
                break;
        }

        if (analyzeBoard.isDraw()) b.setDraw(); 
        if (analyzeBoard.isMate()) b.setMate();
        
        endTime = System.nanoTime();
        return searchResult;
    }

    public Move getStrongestMove() { return strongestMove;  }
    public int  getNoPositions()   { return noPositions;    }
    public long getTimeUsage()     { return Math.abs(endTime-startTime)/1000000;}

    public String moveAndStatistics() {
        String strongestMoveStr = "null";
        
        if (strongestMove != null) strongestMoveStr = strongestMove.toString();
        
        return("move "          + strongestMoveStr +
               " Evaluation "   + searchResult +
               " at "           + _plyDepth +
               " ply in "       + noPositions +
               " positions in " + getTimeUsage() 
               + " mSecs = "    + ((float) noPositions/(float) getTimeUsage())
               + " kN/s with "  + noCutOffs +
               " cutoffs " +
               " wasted moves " + wastedGeneratedMoves );
    }
    
    public int alphaBetaSearch(int currentPlyDepth, int totalPlyDepth, int alpha, int beta) {
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
            if (m.toString().contains("a5") && m.toString().contains("a6")) {
             System.out.println("WHITE: Eval, alpha = " + evaluation + ", " + alpha + " " + m.toString());
            }
            analyzeBoard.performMove(m);
            if (analyzeBoard.isInCheck(m.whoMoves)) {
              analyzeBoard.retractMove(); // The move was not legal
              continue;                   // Try next pseudolegal move
            }
            thereWasALegalMove = true;
            
            //System.out.print("(" + m.toString());
            evaluation = alphaBetaSearch(currentPlyDepth-1, totalPlyDepth, alpha, beta);
            //System.out.println(")");
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

    /**
     * Reference implementation of Min-Max search witout fancy optimizations and
     * tricks. This method can be used to compare the soundess of other 
     * search methods
     * @param plyDepth The overall search depth in ply's
     * @param depthToGo The currently searched depth
     * @return The score. A positive value means white advantage, a negative
     * denotes black advantage and the score 0 denotes equal play.
     */
    public int minMaxSearch(int plyDepth, int depthToGo) {
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

    public int randomSearch() {
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
      while (retry && n > 0) {
          r = Math.random();
          theMove = moves.get((int) Math.ceil((n-1)*r));
          analyzeBoard.performMove(theMove);
          if (analyzeBoard.isInCheck(whoMoves)) {
           analyzeBoard.retractMove();
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

    
    
    public String findBranchingFactor(Board b, int ply) {
        analyzeBoard  = b.clone();
        noPositions   = 0;

        countNodesTmp(ply);
        return "#Nodes "                   + noPositions +
               " at plydepth "             + ply +
               " = a branching factor of " + Math.exp(Math.log(noPositions)/ply) +
               " nodes";
    }
    
    
    private void countNodesTmp(int plydepth) {
        Iterator<Move>          moves;

        if (plydepth == 0) {
          noPositions++;
          return;
        }
        
        moves = Movegenerator.generateAllMoves(analyzeBoard).listIterator();
        while (moves.hasNext()) {
            analyzeBoard.performMove(moves.next());
            countNodesTmp(plydepth - 1);
            analyzeBoard.retractMove();
        }
    }
}
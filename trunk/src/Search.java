import java.util.ArrayList; 
import java.util.Iterator;

//TODO: A result record class with value and move as fields. Maby also comparison methods?
//TODO: Add recording of the principal variations.

class Search {
    // Various serach methods
    final static int ALPHABETA = 1,
                     MINMAX    = 2,
                     RANDOM    = 3;

   
    // Main variables used in the search
    Board        analyzeBoard;
    private int  plyDepth;
    private Move strongestMove;
    private int  searchResult;


    // For statistical pusposes
    private long start_time;
    private long end_time;
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

    public int dosearch(Board b, int plyDepth, int method) {
        analyzeBoard         = b.clone();

        searchResult         = 0;
        noPositions          = 0;
        noCutOffs            = 0;
        wastedGeneratedMoves = 0;
        this.plyDepth        = plyDepth;
        start_time           = System.nanoTime();

        switch (method) {
            case ALPHABETA:
                System.out.println("Alpha-Beta search...");
                searchResult = alphaBetaSearch(plyDepth, plyDepth, Evaluator.WHITE_IS_MATED, Evaluator.BLACK_IS_MATED);

                break;
            case MINMAX:
                System.out.println("MIN-MAX search...");
                searchResult = minMaxSearch(plyDepth, plyDepth);
                break;
            case RANDOM:
                System.out.println("Random search...");
                searchResult = randomSearch();
                break;
        }

        end_time = System.nanoTime();
        return searchResult;
    }

    public Move getStrongestMove() { return strongestMove;  }
    public int  getNoPositions()   { return noPositions;    }
    public long getTimeUsage()     { return Math.abs(end_time-start_time)/1000000;}

    public String moveAndStatistics() {
        String strongestMoveStr = "null";
        
        if (strongestMove != null) strongestMoveStr = strongestMove.toString();
        
        return("move "          + strongestMoveStr +
               " Evaluation "   + searchResult +
               " at "           + plyDepth +
               " ply in "       + noPositions +
               " positions in " + getTimeUsage() 
               + " mSecs = "    + ((float) noPositions/(float) getTimeUsage())
               + " kN/s with "  + noCutOffs +
               " cutoffs " +
               " wasted moves " + wastedGeneratedMoves );
    }

    // 1n2k1n1/pppppppp/8/8/8/8/PPPPPPPP/1N2K1N1 w - - 0 1
    public int alphaBetaSearch(int ply, int depthToGo, int alpha, int beta) {
        ArrayList<Move> moves;
        Move m;
        int best = Integer.MIN_VALUE;
        int eval;

        if (ply == 0) {
            noPositions++;
            return (analyzeBoard.drawBy50MoveRule() ||
                    analyzeBoard.drawBy3RepetionsRule()) ? 
                    0 : Evaluator.evaluate(analyzeBoard);
        }

        /*
        if (Evaluator.evaluate(analyzeBoard) == Evaluator.BLACK_IS_MATED || 
            Evaluator.evaluate(analyzeBoard) == Evaluator.WHITE_IS_MATED)  { 
              //if (plyDepth == depthToGo) strongestMove = null;              
              return Evaluator.evaluate(analyzeBoard);
        }
        */
        
        moves = Movegenerator.generateAllMoves(analyzeBoard);
        
        if (moves.isEmpty()) {  // A draw
            return 0;
        } 
        
        for (int i = 0; i < moves.size(); i++) {
            m = moves.get(i);
            analyzeBoard.performMove(m);
            eval = -alphaBetaSearch(ply - 1, depthToGo, -beta, -alpha);
            analyzeBoard.retractMove();

            if (eval > best) {
                best = eval;
                if (ply == depthToGo)  strongestMove = m;   
            }

            if (best > alpha) { alpha = best;   }

            if (alpha >= beta) { 
                wastedGeneratedMoves = wastedGeneratedMoves + (moves.size()-(i+1));
                noCutOffs++;
                return alpha; }
        }
        
        return best;
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

                score = minMaxSearch(plyDepth, depthToGo - 1);
               
                        
                analyzeBoard.retractMove();
                
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
      double          r = Math.random();
      
      moves = Movegenerator.generateAllMoves(analyzeBoard);
      n     = moves.size();
      
      if (n == 0) return 0;
      
      strongestMove = moves.get((int) Math.ceil((n-1)*r));
      return Evaluator.evaluate(analyzeBoard);
    }

    
    
    public String findBranchingFactor(Board b, int ply) {
        analyzeBoard  = b.clone();
        noPositions   = 0;

        CountNodesTmp(ply);
        return "#Nodes "                   + noPositions +
               " at plydepth "             + ply +
               " = a branching factor of " + Math.exp(Math.log(noPositions)/ply) +
               " nodes";
    }
    
    
    private void CountNodesTmp(int plydepth) {
        Iterator<Move>          moves;

        if (plydepth == 0) {
          noPositions++;
          return;
        }
        
        moves = Movegenerator.generateAllMoves(analyzeBoard).listIterator();
        while (moves.hasNext()) {
            analyzeBoard.performMove(moves.next());
            CountNodesTmp(plydepth - 1);
            analyzeBoard.retractMove();
        }
    }
}
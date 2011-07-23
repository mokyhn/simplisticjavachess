import java.util.ArrayList; 
import java.util.Iterator;

//TODO: A result record class with value and move as fields. Maby also comparison methods?
//TODO: Add recording of the principal variations.
/*
 * int minimax(int ply, int depth)
{
       best = -LARGE_NUMBER;
       triangularLength[ply] = ply;                                          // current PV will start here
       if (depth == 0) return eval();
       movegen();
       for (i = firstmove; i < lastmove; i++)
       {
              makeMove(moveBuffer[i]);
              val = -minimax(ply+1, depth-1);                               
              unmakeMove(moveBuffer[i]);
              if (val > best)                                                
              {
                  best = val;
                  triangularArray[ply][ply] = moveBuffer[i];                 // save this move
                  for (j = ply + 1; j < triangularLength[ply + 1]; j++)
                  {
                      triangularArray[ply][j] = triangularArray[ply+1][j];   // and append the latest best PV from deeper plies
                  }
                  triangularLength[ply] = triangularLength[ply + 1];
              }
       }
       return best;
}
This code will collect the PV in  triangularArray[0][0..triangularLength[0]].
 */
class Search {
    // Various serach methods
    final static int ALPHABETA = 1,
                     MINMAX    = 2,
                     RANDOM    = 3;


    private final static int MINSCORE = Integer.MIN_VALUE + 1,
                             MAXSCORE = Integer.MAX_VALUE - 1;
    
    // Main variables used in the search
    Board        analyzeBoard;
    private int  plyDepth;
    private Move strongestMove;
    private int  searchResult;


    // For statistical pusposes
    private long start_time;
    private long end_time;
    private int  noPositions;
    private int  noBetaCutOffs;
    private int  wastedGeneratedMoves;
    
    public Search() {
          noPositions          = 0;
          noBetaCutOffs        = 0;
          searchResult         = 0;
          wastedGeneratedMoves = 0;
          strongestMove        = null;
    }

    public int dosearch(Board b, int plyDepth, int method) {
        analyzeBoard         = b.clone();

        searchResult         = 0;
        noPositions          = 0;
        noBetaCutOffs        = 0;
        wastedGeneratedMoves = 0;
        this.plyDepth        = plyDepth;
        start_time           = System.nanoTime();

        switch (method) {
            case ALPHABETA:
                System.out.println("Alpha-Beta search...");
                searchResult = alphaBetaSearch(plyDepth, plyDepth, MINSCORE, MAXSCORE); 

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
        return("move "          + strongestMove.toString() +
               " Evaluation "   + searchResult +
               " at "           + plyDepth +
               " ply in "       + noPositions +
               " positions in " + getTimeUsage() 
               + " mSecs = "    + ((float) noPositions/(float) getTimeUsage())
               + " kN/s with "  + noBetaCutOffs +
               " Beta-cutoffs " +
               " wasted moves " + wastedGeneratedMoves );
    }
    
    public int alphaBetaSearch(int plyDepth, int depthToGo, int alpha, int beta) {
        ArrayList<Move> moves;
        Move            m                     = null;
        boolean         firstCalculationWhite = true;
        boolean         firstCalculationBlack = true;
        int e = 0;
        int newAlpha = alpha;
        int newBeta  = beta;
        int inMove =    analyzeBoard.inMove();

        
        // Return board evaluation immediately
        if (depthToGo == 0) {
            noPositions++;
            System.out.println("Evaluation " + Evaluator.evaluate(analyzeBoard));
            return Evaluator.evaluate(analyzeBoard);
        }

        moves = Movegenerator.generateAllMoves(analyzeBoard); 

        
        if (Evaluator.evaluate(analyzeBoard) == Evaluator.BLACK_IS_MATED || 
            Evaluator.evaluate(analyzeBoard) == Evaluator.WHITE_IS_MATED)  { 
              strongestMove = null;              
              return Evaluator.evaluate(analyzeBoard);
        }
            
        if (moves.isEmpty()) {  // A draw
            strongestMove = null;
            return 0;
        } 

        for (int i = 0; i < moves.size(); i++) {
            m = moves.get(i);
            analyzeBoard.performMove(m);
            
            if (analyzeBoard.drawBy50MoveRule() || 
                analyzeBoard.drawBy3RepetionsRule()) {
                 analyzeBoard.retractMove();
                 strongestMove = null;
                 return 0;
            }
           
           e = alphaBetaSearch(plyDepth, depthToGo - 1, newAlpha, newBeta);
           
           if (inMove == Piece.WHITE) {
                if (firstCalculationWhite) {
                  if (plyDepth == depthToGo) strongestMove = m;
                  newAlpha              = e;
                  firstCalculationWhite = false;
             } else {
                 if (e > newAlpha) {
                  newAlpha = e; 
                  System.out.println(newAlpha);
                  if (plyDepth == depthToGo) strongestMove = m;                                       
                 }
                 else if (e < newAlpha) {
                  System.out.println("Cutof (e, newAlpha) = " + e + ", " + newAlpha);
                  analyzeBoard.retractMove();
                  wastedGeneratedMoves = wastedGeneratedMoves + (moves.size()-(i+1));
                  noBetaCutOffs++;
                  break;
                 }
               }
           }

           if (inMove == Piece.BLACK) {
             if (firstCalculationBlack) {
                  if (plyDepth == depthToGo) strongestMove    = m;
                  newBeta         = e;
                  firstCalculationBlack = false;                     
             } else {
                 if (e < newBeta) {
                  newBeta = e;
                  if (plyDepth == depthToGo) strongestMove = m; 
                 } else if (e > newBeta) {
                  analyzeBoard.retractMove(); 
                  wastedGeneratedMoves = wastedGeneratedMoves + (moves.size()-(i+1));
                  noBetaCutOffs++;
                  break;
                 }  
              }
           }
            

            analyzeBoard.retractMove();
                
            }
        
        return inMove == Piece.WHITE ? newAlpha : newBeta;
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
            return Evaluator.evaluate(analyzeBoard);
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

                if (analyzeBoard.drawBy3RepetionsRule() ||
                    analyzeBoard.drawBy50MoveRule()) {
                    score = 0;
                } else {
                 score = minMaxSearch(plyDepth, depthToGo - 1);
                }
                        
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
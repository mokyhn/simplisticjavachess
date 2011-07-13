import java.util.ArrayList;
import java.util.Iterator;

class Search {
    // Various serach methods
    final static int ALPHABETA = 1,
                     MINMAX    = 2,
                     RANDOM    = 3;


    private final static int MINSCORE = -10000,
                             MAXSCORE =  10000;
    
    
    // Main variables used in the search
    Board        analyzeBoard;
    private int  plyDepth;
    private Move strongestMove        = new Move();
    private int  searchResult         = 0;


    // For statistical pusposes
    private long start_time;
    private long end_time;
    private int  noPositions          = 0;
    private int  noBetaCutOffs        = 0;
    private int  wastedGeneratedMoves = 0;


    public Search() {
          noPositions          = 0;
          noBetaCutOffs        = 0;
          searchResult         = 0;
          wastedGeneratedMoves = 0;
    }

    public int dosearch(Board b, int plyDepth, int method) {
        analyzeBoard = b.clone();

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

    public String findBranchingFactor(Board b, int ply) {
        analyzeBoard  = b.clone();
        noPositions   = 0;

        CountNodesTmp(ply);
        return "#Nodes "                   + noPositions +
               " at plydepth "             + ply +
               " = a branching factor of " + Math.exp(Math.log(noPositions)/ply) +
               " nodes";
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
        Move m        = null;
        int score     = 0;
        int best      = MINSCORE;
        int i;

        // Return board evaluation immediately
        if (depthToGo == 0) {
            noPositions++;
            return Evaluator.evaluate(analyzeBoard);
        }

        moves = Movegenerator.generateAllMoves(analyzeBoard); 

        if (moves.isEmpty()) {  // TODO: Seems wrong these conditions
            if (Math.abs(Evaluator.evaluate(analyzeBoard)) == 1000)  { 
              return Evaluator.evaluate(analyzeBoard);
            }
            else {
             return 0; // A draw
            }
        }

        for (i = 0; i < moves.size(); i++) {
            m = moves.get(i);
            analyzeBoard.performMove(m);

            if (analyzeBoard.drawBy50MoveRule() ||
                analyzeBoard.drawBy3RepetionsRule()) {
               score = 0;              
            }
            else {
             score = -alphaBetaSearch(plyDepth, depthToGo - 1, -beta, -Math.max(alpha, best));
            }

            analyzeBoard.retractMove();

            if (score > best) {
                best = score;
                if (plyDepth == depthToGo) strongestMove = m;
                if (best >= beta) {
                    wastedGeneratedMoves = wastedGeneratedMoves + (moves.size()-(i+1));
                    noBetaCutOffs++;
                    return beta;
                }
            }
        }
        return best;
    }

    public int minMaxSearch(int plyDepth, int depthToGo) {
       ArrayList<Move>        moves;
       Move m                = null;
       int  i;
       int score      = 0,
           bestscore  = Integer.MIN_VALUE;


        if (depthToGo == 0) {
            noPositions++;
            return Evaluator.evaluate(analyzeBoard);
        }

        moves = Movegenerator.generateAllMoves(analyzeBoard);

        if (moves.isEmpty()) {
            if (Math.abs(Evaluator.evaluate(analyzeBoard)) == 1000)  {
              return Evaluator.evaluate(analyzeBoard);
            }
            else
            return 0; // A draw
        }

        for (i = 0; i < moves.size(); i++) {
                m = moves.get(i);
                analyzeBoard.performMove(m);

                if (analyzeBoard.drawBy3RepetionsRule() ||
                    analyzeBoard.drawBy50MoveRule()) {
                    score = 0;
                } else {
                 score = -minMaxSearch(plyDepth, depthToGo - 1);
                }

                analyzeBoard.retractMove();

                if (score >= bestscore) {
                    bestscore = score;
                    if (plyDepth == depthToGo) strongestMove = m; // Used to extract strongest move
                }
        }

        return bestscore;

    }

   
    public int randomSearch() {
      ArrayList<Move>           moves;
      int n;
      double r = Math.random();
      moves    = Movegenerator.generateAllMoves(analyzeBoard);

      n = Math.abs(moves.size()-1);
      strongestMove = moves.get((int) Math.ceil(n*r));
      return Evaluator.evaluate(analyzeBoard);
    }

    private void CountNodesTmp(int plydepth) {
        Iterator<Move>          moves;

        if (plydepth == 0) {
          noPositions++;
          return;
        }
        
        moves = Movegenerator.generateAllMoves(analyzeBoard).listIterator(); // Could we avoid this listIterator conversion?
        while (moves.hasNext()) {
            analyzeBoard.performMove(moves.next());
            CountNodesTmp(plydepth - 1);
            analyzeBoard.retractMove();
        }
    }
}
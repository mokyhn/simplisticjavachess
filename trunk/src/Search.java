import java.util.ArrayList;
import java.util.Iterator;

class Search {
    Board        analyzeBoard;
    private long start_time;
    private long end_time;
    private Move strongestMove = null;
    private int  searchResult  = 0;
    int          noPositions   = 0;
    int          plyDepth;
    String[]     principalVariant;

    final static int ALPHABETA = 1,
                     MINMAX    = 2,
                     RANDOM    = 3;

    public Search() {
          noPositions   = 0;
          searchResult  = 0;
    }

    public int dosearch(Board b, int plyDepth, int method) {
      searchResult  = 0;
      analyzeBoard  = (Board) b.clone();
      noPositions   = 0;
      this.plyDepth = plyDepth;
      start_time    = System.nanoTime();
      int i;

      principalVariant = new String[plyDepth];
      for (i = 0; i < plyDepth; i++) principalVariant[i] = "";

      if (method == ALPHABETA) {
          System.out.println("Alpha-Beta search...");
          searchResult  = alphaBetaSearch(plyDepth, plyDepth, -30000, 30000);
      }
      if (method == MINMAX)    {
          System.out.println("MIN-MAX search...");
          searchResult  = minMaxSearch(plyDepth, plyDepth);
      }
      if (method == RANDOM)    {
          System.out.println("Random search...");
          searchResult  = randomSearch();
      }
      end_time      = System.nanoTime();

      for (i = plyDepth-1; i > 0; i--) System.out.println(principalVariant[i]);

      return searchResult;
    }

    public Move getStrongestMove() { return strongestMove;  }

    public int getNoPositions()    { return noPositions;    }

    public long getTimeUsage() { return Math.abs(end_time-start_time)/1000000;}

    public String moveAndStatistics() {
        return("move " + strongestMove.toString() +
               " Evaluation " + searchResult + " at " +
               plyDepth + " ply in " + noPositions +
               " positions in " + getTimeUsage() + " mSecs = "
               + ((float) noPositions/(float) getTimeUsage())
               + " kN/s");
    }

    public int alphaBetaSearch(int plyDepth, int depthToGo, int alpha, int beta) {
            Iterator<Move>          moves;
            Move m                = null;
            int score             = 0;
            int best = 0;

            // Return board evaluation immediately
            if (depthToGo == 0) {
                noPositions++;
                return Evaluator.evaluate(analyzeBoard);
            }

            // Otherwise generate legal moves
            moves = Movegenerator.generateAllMoves(analyzeBoard).listIterator();

            //Movegenerator.printMoves(movegen.generateAllMoves(analyzeBoard));

            best = -30000;

            // Traverse the legal moves
            while (moves.hasNext()) {
                m = moves.next();
                    //System.out.print("Inspecting " + m.toString());
                    //analyzeBoard.print();
                    analyzeBoard.performMove(m);
                    score = -alphaBetaSearch(plyDepth, depthToGo - 1, -beta, -Math.max(alpha, best));

                    //System.out.println("Retracting: " + m.toString());
                    analyzeBoard.retractMove();

                    if (score > best) {
                            best = score;
                            if (plyDepth == depthToGo) strongestMove = m;
                            if (best >= beta) return beta;
                    }
            }
            return best;
    }

    // TODO: Implement for test/reference purposes
    public int minMaxSearch(int plyDepth, int depthToGo) {
           Iterator<Move>          moves;
           Move m                = null;
           int score      = 0,
               bestscore  = -1000; // Minus infinity
           
            // Return board evaluation immediately
            if (depthToGo == 0) {
                noPositions++;
                return Evaluator.evaluate(analyzeBoard);
            }

            // Otherwise generate legal moves
            moves = Movegenerator.generateAllMoves(analyzeBoard).listIterator(); // Could we avoid this listIterator conversion?

            //Movegenerator.printMoves(movegen.generateAllMoves(analyzeBoard));

            // Traverse the legal moves
            while (moves.hasNext()) {
                m = moves.next();
                    //System.out.print("Inspecting " + m.toString());
                    //analyzeBoard.print();
                    analyzeBoard.performMove(m);

                    score = -minMaxSearch(plyDepth, depthToGo - 1);

                    //System.out.println("Retracting: " + m.toString());
                    analyzeBoard.retractMove();

                    if (score >= bestscore) {
                        principalVariant[depthToGo-1] = "( " + m.toString() + " " + score + " )";
                        bestscore = score;
                        if (plyDepth == depthToGo) strongestMove = m; // Used to extract strongest move
                    }
            }
            
            return bestscore;

    }

   
    public int randomSearch() {
            Movegenerator movegen = new Movegenerator();
            ArrayList<Move>           moves;
            int n;
            double r = Math.random();
            moves = movegen.generateAllMoves(analyzeBoard);

            n = Math.abs(moves.size()-1);
           strongestMove = moves.get((int) Math.ceil(n*r));
        return Evaluator.evaluate(analyzeBoard);
    }
}
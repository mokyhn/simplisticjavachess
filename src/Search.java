import java.util.Iterator;

class Search {
	Evaluator    eval;
	Board        analyzeBoard;
        private long start_time;
        private long end_time;
	private Move strongestMove = new Move();
        private int  searchResult  = 0;
	int          noPositions   = 0;
        int          plyDepth;

	public Search() {
              noPositions   = 0;
              searchResult  = 0;
              eval          = new Evaluator();
	}

	public int dosearch(Board b, int plyDepth) {
          searchResult  = 0;
          analyzeBoard  = (Board) b.clone();
	  noPositions   = 0;
          this.plyDepth = plyDepth;
          start_time    = System.nanoTime();
          searchResult  = alphaBetaSearch(plyDepth, plyDepth, -30000, 30000);
          end_time      = System.nanoTime();
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
                   + 1000 * ((float) noPositions/(float) getTimeUsage())
                   + " nodes pr. sec");
        }

	public int alphaBetaSearch(int plyDepth, int depthToGo, int alpha, int beta) {
		Movegenerator movegen = new Movegenerator();
		Iterator<Move>          moves;
		Move m                = new Move();
		int score             = 0;
		int localAlpha        = alpha;


		// Return board evaluation immediately
		if (depthToGo == 0) {
                    noPositions++;
                    return eval.evaluate(analyzeBoard);
                }

		// Otherwise generate legal moves
		moves = movegen.generateAllMoves(analyzeBoard).listIterator();

                Movegenerator.printMoves(movegen.generateAllMoves(analyzeBoard));

		// Traverse the legal moves
		while (moves.hasNext()) {
                    m = moves.next();

                        System.out.println("Trying: " + m.toString());
                        analyzeBoard.print();
                        analyzeBoard.performMove(m);
			score = -alphaBetaSearch(plyDepth, depthToGo - 1, -beta, -localAlpha);

                        System.out.println("Retracting: " + m.toString());
                        //analyzeBoard.print();
                        analyzeBoard.retractMove();

			if (score > localAlpha) {
				if (plyDepth == depthToGo) strongestMove = m;
				if (score > beta) return beta;
				localAlpha = score;
			}
		}
		return localAlpha;
	}
}
package com.simplisticjavachess.search;

import com.simplisticjavachess.move.Move;

public class SearchResult {
    private final Move move;
    private final int evaluation;
    
    public SearchResult(Move move, int evaluation)
    {
        this.move = move;
        this.evaluation = evaluation;
    }  

    /**
     * @return the move
     */
    public Move getMove() {
        return move;
    }

    /**
     * @return the evaluation
     */
    public int getEvaluation() {
        return evaluation;
    }
}

package com.simplisticjavachess.search;

import com.simplisticjavachess.evaluator.Evaluation;
import com.simplisticjavachess.move.Move;

public class SearchResult {
    private final Move move;
    private final Evaluation evaluation;
    
    public SearchResult(Move move, Evaluation evaluation)
    {
        this.move = move;
        this.evaluation = evaluation;
    }  

    /**
     * @return the move
     */
    public Move getMove()
    {
        return move;
    }

    /**
     * @return the evaluation
     */
    public Evaluation getEvaluation()
    {
        return evaluation;
    }
    
    @Override
    public String toString()
    {
        return "Move " + move + " with evaluation " + evaluation.toString();

    }
}

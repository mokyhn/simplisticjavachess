package com.simplisticjavachess.engine;

import com.simplisticjavachess.evaluation.Evaluation;

public class SearchResult {
    private final MoveSequence moveSequence;
    private final Evaluation evaluation;

    public SearchResult(Evaluation evaluation)
    {
        this(new MoveSequence(), evaluation);
    }
    
    public SearchResult(MoveSequence moveSequence, Evaluation evaluation)
    {
        this.moveSequence = moveSequence;
        this.evaluation = evaluation;
    }  

    /**
     * @return the move sequence
     */
    public MoveSequence getMoveSequence()
    {
        return moveSequence;
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
        return "Move " + moveSequence + " with evaluation " + evaluation.toString();
    }
}

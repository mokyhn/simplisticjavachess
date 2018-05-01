package com.simplisticjavachess.evaluator;

public interface Improver
{
    /**
     * The new evaluation that improves some other evaluation
     * @param evaluation 
     */
    void run(Evaluation evaluation);
}

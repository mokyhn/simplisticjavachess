package com.simplisticjavachess.evaluation;

import com.simplisticjavachess.piece.Color;

public abstract class Evaluation
{
    /**
     * @param color the perspective the comparison is seen from
     * @param other the other evaluation to try to improve with
     * @return this if this is better and else return other
     */
    public Evaluation improveWith(Color color, Evaluation other)
    {
        return this.isAnImprovement(color, other) ? other : this;
    }

    /**
     * @param color the perspective the comparison is seen from
     * @param other the candidate that may improve this
     * @return true if the other improves this
     */
    public abstract boolean isAnImprovement(Color color, Evaluation other);


}

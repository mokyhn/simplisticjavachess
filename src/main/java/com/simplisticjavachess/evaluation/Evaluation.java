package com.simplisticjavachess.evaluation;

import com.simplisticjavachess.piece.Color;

public abstract class Evaluation
{

    public abstract boolean isSomething();

    /**
     * @param color the perspective the comparison is seen from
     * @param other the other evaluation to try to improve with
     * @return other if better and else return this
     */
    public Evaluation improveWith(Color color, Evaluation other)
    {
        return this.isWorseThan(color, other) ? other : this;
    }

    /**
     * @param color the perspective the comparison is seen from
     * @param other the candidate that may improve this
     * @return true if the other improves this
     */
    public abstract boolean isWorseThan(Color color, Evaluation other);

}

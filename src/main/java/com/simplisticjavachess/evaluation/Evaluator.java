package com.simplisticjavachess.evaluation;

import com.simplisticjavachess.position.Position;

public interface Evaluator {
    Evaluation evaluate(Position position);

    /**
     * @return The un-evaluated evaluation of something
     */
    Evaluation getNone();

    /**
     * @return the equal un-decided game score which is in progress
     */
    Evaluation getEqual();

    /**
     * @return The evaluation when white is mate
     */
    Evaluation getWhiteIsMate();

    /**
     * @return The evaluation when black is mate
     */
    Evaluation getBlackIsMate();
}

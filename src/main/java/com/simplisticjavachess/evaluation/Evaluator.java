package com.simplisticjavachess.evaluation;

import com.simplisticjavachess.position.Position;

/**
 * The evaluation heuristic function interface
 */

public interface Evaluator {
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

    /**
     * @param position to evaluation
     * @return evaluation result of the position
     */
    Evaluation evaluate(Position position);


}

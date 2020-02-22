package com.simplisticjavachess.evaluation;

import com.simplisticjavachess.piece.Color;

public interface Evaluation
{
    Evaluation improveWith(Color color, Evaluation other);

    boolean isAnImprovement(Color color, Evaluation other);


}

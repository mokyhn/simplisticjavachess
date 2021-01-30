package com.simplisticjavachess.evaluation;

import com.simplisticjavachess.position.Position;

public interface Evaluator
{
    Evaluation evaluate(Position position);
}

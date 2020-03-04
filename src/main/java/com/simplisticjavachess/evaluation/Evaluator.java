package com.simplisticjavachess.evaluation;

import com.simplisticjavachess.board.Position;

public interface Evaluator
{
    Evaluation evaluate(Position position);
}

package com.simplisticjavachess.evaluation;

import com.simplisticjavachess.board.Board;

public interface Evaluator
{
    Evaluation evaluate(Board b);
}

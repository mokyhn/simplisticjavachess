/*
 * @author Morten Kühnrich
 */

package com.simplisticjavachess.engine;

import com.simplisticjavachess.board.Board;
import com.simplisticjavachess.evaluation.Evaluator;
import com.simplisticjavachess.movegenerator.MoveGenerator;

public interface Engine
{
    /**
     * @param board the board to search for moves in
     * @param moveGenerator the move generator
     * @param evaluator the evaluator that evaluate resulting positions
     * @param plyDepth the depth to search in
     * @return
     */
    SearchResult search(Board board, MoveGenerator moveGenerator, Evaluator evaluator, int plyDepth);
}

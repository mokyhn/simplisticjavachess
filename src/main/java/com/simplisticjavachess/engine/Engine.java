/*
 * @author Morten Kühnrich
 */

package com.simplisticjavachess.engine;

import com.simplisticjavachess.position.Mover;
import com.simplisticjavachess.position.Position;
import com.simplisticjavachess.evaluation.Evaluator;
import com.simplisticjavachess.movegenerator.MoveGenerator;

public interface Engine
{
    /**
     * @param position the board to search for moves in
     * @param mover the move mechanism that transforms positions into positions though a move
     * @param moveGenerator the move generator
     * @param evaluator the evaluator that evaluate resulting positions
     * @param depth the ply depth to search in
     * @return
     */
    SearchResult search(Position position, Mover mover, MoveGenerator moveGenerator, Evaluator evaluator, int depth);
}

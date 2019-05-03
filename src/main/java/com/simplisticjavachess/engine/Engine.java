/*
 * @author Morten Kühnrich
 */

package com.simplisticjavachess.engine;

import com.simplisticjavachess.board.Board;

public interface Engine
{
    //TODO: Supply a move generator (see todo in MoveGenerator class).
    //This will help us to integration test engine a lot better
    //TODO: We also would like to supply the evaluator.
    //That will allow us to work with different evaluation functions.
    SearchResult search(Board b, int plyDepth);
}

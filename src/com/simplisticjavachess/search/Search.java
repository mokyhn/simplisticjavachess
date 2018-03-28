/*
 * @author Morten Kühnrich
 */

package com.simplisticjavachess.search;

import com.simplisticjavachess.board.Board;
import com.simplisticjavachess.move.Move;

public interface Search
{
    int search(Board b, int plyDepth);
    public Move getStrongestMove();
}

/*
 * @author Morten Kühnrich
 */

package com.simplisticjavachess.search;

import com.simplisticjavachess.board.Board;

public interface Search
{
    SearchResult search(Board b, int plyDepth);
}

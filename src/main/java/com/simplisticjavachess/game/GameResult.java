/**
 *
 * @author Morten Kühnrich
 */

package com.simplisticjavachess.game;

public enum GameResult
{
    NO_RESULT,
    DRAW_STALE_MATE,
    MATE, 
    DRAW_BY_50_MOVE_RULE, 
    DRAW_BY_REPETITION 
}
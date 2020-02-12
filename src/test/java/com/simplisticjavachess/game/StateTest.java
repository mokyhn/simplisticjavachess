/**
 *
 * @author Morten Kühnrich
 */


package com.simplisticjavachess.game;

import com.simplisticjavachess.move.Moves;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class StateTest
{

    private State createDummyState()
    {
        // Setup
        State state = new State();
        state = state.setGameResult(GameResult.DRAW_BY_50_MOVE_RULE);
        state = state.setHalfMoveClock(42);
        state = state.setHalfMovesIndex3PosRepetition(41);
        //state.moveNumber = 49;
        state = state.setMove(Moves.BLACK_LONG_CASTLING());
        return state;
    }

    @Test
    public void testEquals() 
    {
        State state1 = createDummyState();
        State state2 = createDummyState();
        assertTrue(state1.equals(state2));
    }
    
     
}

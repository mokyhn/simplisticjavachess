/**
 *
 * @author Morten Kühnrich
 */


package com.simplisticjavachess.game;

import com.simplisticjavachess.move.Moves;
import com.simplisticjavachess.piece.Color;
import org.junit.Test;
import static org.junit.Assert.*;

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
    public void testSetCanCastleLong()
    {
        State state = new State();
        state = state.setCanCastleLong(true, Color.WHITE);
        assertTrue(state.getCanCastleLong(Color.WHITE));
        assertFalse(state.getCanCastleLong(Color.BLACK));
    }

    @Test
    public void testGetCanCastleShort_Color()
    {
        State state = new State();
		state = state.setCanCastleShort(true, Color.BLACK);
        assertTrue(state.getCanCastleShort(Color.BLACK));
        
        state = new State();
        state = state.setCanCastleShort(true, Color.WHITE);
        assertTrue(state.getCanCastleShort(Color.WHITE));
    }

     
    @Test
    public void testGetCanCastleLong_Color()
    {
        State state = new State();
        state = state.setCanCastleLong(true, Color.BLACK);
        assertTrue(state.getCanCastleLong(Color.BLACK));
        
        state = new State();
        state = state.setCanCastleLong(true, Color.WHITE);
        assertTrue(state.getCanCastleLong(Color.WHITE));
    }

    @Test
    public void testEquals() 
    {
        State state1 = createDummyState();
        State state2 = createDummyState();
        assert(state1.equals(state2));
    }
    
 
    
    @Test
    public void testNotEquals2()
    {
        State state1 = createDummyState();
        State state2 = createDummyState();
        
        state1 = state1.setCanCastleLong(true, Color.BLACK);
        assertNotEquals(state1, state2);
    }
    
     
}

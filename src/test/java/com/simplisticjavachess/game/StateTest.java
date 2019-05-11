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
    
    @Test
    public void testCopyConstructor()
    {
        State state = createDummyState();
        
        State stateClone = new State(state);
        assertSame(stateClone.gameResult, GameResult.DRAW_BY_50_MOVE_RULE);
        assertEquals(42, stateClone.halfMoveClock);
        assertEquals(41, stateClone.halfMovesIndex3PosRepetition);
        assertSame(stateClone.getInMove(), Color.BLACK);
        //assertEquals(49, stateClone.moveNumber);
        assertEquals(stateClone.getMove(), Moves.BLACK_LONG_CASTLING());
        
    }

    private State createDummyState()
    {
        // Setup
        State state = new State();
        state.gameResult = GameResult.DRAW_BY_50_MOVE_RULE;
        state.halfMoveClock = 42;
        state.halfMovesIndex3PosRepetition = 41;
        state.setInMove(Color.BLACK);
        //state.moveNumber = 49;
        state.setMove(Moves.BLACK_LONG_CASTLING());
        return state;
    }

    @Test
    public void testSetCanCastleLong()
    {
        State state = new State();
        state.setInMove(Color.WHITE);
        state = state.setCanCastleLong(true, Color.WHITE);
        assertTrue(state.getCanCastleLong());

        state.setInMove(Color.BLACK);
        assertFalse(state.getCanCastleLong());
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

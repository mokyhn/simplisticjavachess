/**
 *
 * @author Morten Kühnrich
 */


package com.simplisticjavachess.game;

import com.simplisticjavachess.move.Moves;
import com.simplisticjavachess.piece.Color;
import com.simplisticjavachess.board.InvalidLocationException;
import org.junit.Test;
import static org.junit.Assert.*;

public class StateTest
{
    
    @Test
    public void testCopyConstructor() throws InvalidLocationException 
    {
        State state = createDummyState();
        
        State stateClone = new State(state);
        assertTrue(stateClone.gameResult == GameResult.DRAW_BY_50_MOVE_RULE);
        assertTrue(stateClone.halfMoveClock == 42);
        assertTrue(stateClone.halfMovesIndex3PosRepition == 41);
        assertTrue(stateClone.inMove == Color.BLACK);
        assertTrue(stateClone.moveNumber == 49);
        assertTrue(stateClone.move.equals(Moves.BLACK_LONG_CASTLING()));
        
    }

    private State createDummyState()
    {
        // Setup
        State state = new State();
        state.gameResult = GameResult.DRAW_BY_50_MOVE_RULE;
        state.halfMoveClock = 42;
        state.halfMovesIndex3PosRepition = 41;
        state.inMove = Color.BLACK;
        state.moveNumber = 49;
        state.move = Moves.BLACK_LONG_CASTLING();
        return state;
    }

    @Test
    public void testSetCanCastleLong()
    {
        State state = new State();
        state.inMove = Color.WHITE;
        state.setCanCastleLong(true, Color.WHITE);
        assertTrue(state.getCanCastleLong());

        state.inMove = Color.BLACK;
        assertFalse(state.getCanCastleLong());
    }

    @Test
    public void testGetCanCastleShort_Color()
    {
        State state = new State();
        state.setCanCastleShort(true, Color.BLACK);
        assertTrue(state.getCanCastleShort(Color.BLACK));
        
        state = new State();
        state.setCanCastleShort(true, Color.WHITE);
        assertTrue(state.getCanCastleShort(Color.WHITE));
    }

     
    @Test
    public void testGetCanCastleLong_Color()
    {
        State state = new State();
        state.setCanCastleLong(true, Color.BLACK);
        assertTrue(state.getCanCastleLong(Color.BLACK));
        
        state = new State();
        state.setCanCastleLong(true, Color.WHITE);
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
        
        state1.setCanCastleLong(true, Color.BLACK);
        assertFalse(state1.equals(state2));
    }
    
     
}
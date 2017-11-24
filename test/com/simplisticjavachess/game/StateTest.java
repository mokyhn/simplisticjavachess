/**
 *
 * @author Morten Kühnrich
 */


package com.simplisticjavachess.game;

import com.simplisticjavachess.board.BitBoard;
import com.simplisticjavachess.move.Moves;
import com.simplisticjavachess.piece.Color;
import com.simplisticjavachess.piece.Piece;
import com.simplisticjavachess.piece.PieceType;
import com.simplisticjavachess.position.InvalidLocationException;
import com.simplisticjavachess.position.Locations;
import org.junit.Test;
import static org.junit.Assert.*;

public class StateTest
{
    
    @Test
    public void testCopyConstructor() throws InvalidLocationException 
    {
        State state = createDummyState();
        
        State stateClone = new State(state);
        assertTrue(stateClone.bbposition.hasPiece(Locations.D4, Color.BLACK, PieceType.KNIGHT));
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
        BitBoard bitBoard = new BitBoard();
        bitBoard.insertPiece(new Piece(Locations.D4, Color.BLACK, PieceType.KNIGHT));
        state.bbposition = bitBoard;
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
    public void testNotEquals1()
    {
        State state1 = createDummyState();
        State state2 = createDummyState();
        
        state2.bbposition.removePiece(Locations.D4);
        assertFalse(state1.equals(state2));
    }
    
    @Test
    public void testNotEquals2()
    {
        State state1 = createDummyState();
        State state2 = createDummyState();
        
        state1.setCanCastleLong(true, Color.BLACK);
        assertFalse(state1.equals(state2));
    }
    
    
    @Test
    public void testNotEqualsNullBitboard2()
    {
        State state1 = createDummyState();
        State state2 = createDummyState();
        
        state2.bbposition = null;
        assertFalse(state1.equals(state2));
    }
     
}

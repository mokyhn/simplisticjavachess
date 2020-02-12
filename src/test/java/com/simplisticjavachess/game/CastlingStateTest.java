package com.simplisticjavachess.game;

import org.junit.Test;

import java.util.EnumSet;

import static com.simplisticjavachess.piece.Color.BLACK;
import static com.simplisticjavachess.piece.Color.WHITE;
import static org.junit.Assert.*;

public class CastlingStateTest {

	@Test
	public void setCanCastleShort()
	{
		CastlingState state = CastlingState.NOBODY_CAN_CASTLE;
		state = state.setCanCastleShort(WHITE, BLACK);
		assertTrue(state.canCastleShort(WHITE));
		assertTrue(state.canCastleShort(BLACK));

		state = state.setCannotCastleShort(WHITE);
		assertFalse(state.canCastleShort(WHITE));

	}

	@Test
	public void setCanCastleLong() {
		CastlingState state = CastlingState.NOBODY_CAN_CASTLE;
		state = state.setCanCastleLong(WHITE, BLACK);
		assertTrue(state.canCastleLong(WHITE));
		assertTrue(state.canCastleLong(BLACK));

		state = state.setCannotCastleLong(WHITE);
		assertFalse(state.canCastleLong(WHITE));

	}


	@Test
	public void testOrderAndHashing() {
		CastlingState state1 = new CastlingState(EnumSet.of(WHITE, BLACK), EnumSet.of(WHITE));
		CastlingState state2 = new CastlingState(EnumSet.of(BLACK, WHITE), EnumSet.of(WHITE));
		CastlingState state3 = new CastlingState(EnumSet.of(WHITE), EnumSet.of(WHITE, BLACK));

		assertEquals(state1.getChessHashCode(), state2.getChessHashCode());
		assertNotEquals(state1.getChessHashCode(), state3.getChessHashCode());
	}

}
package com.simplisticjavachess.game;

import org.junit.Test;

import static com.simplisticjavachess.piece.Color.BLACK;
import static com.simplisticjavachess.piece.Color.WHITE;
import static org.junit.Assert.*;

public class CastlingStateTest {

	@Test
	public void setCanCastleShort()
	{
		CastlingState state = CastlingState.NOBODY_CAN_CASTLE;
		state = state.setCanCastleShort(WHITE, true);
		state = state.setCanCastleShort(BLACK, true);

		assertTrue(state.canCastleShort(WHITE));
		assertTrue(state.canCastleShort(BLACK));

		state = state.setCanCastleShort(WHITE, false);
		assertFalse(state.canCastleShort(WHITE));

	}

	@Test
	public void setCanCastleLong() {
		CastlingState state = CastlingState.NOBODY_CAN_CASTLE;
		state = state.setCanCastleLong(WHITE, true);
		state = state.setCanCastleLong(BLACK, true);
		assertTrue(state.canCastleLong(WHITE));
		assertTrue(state.canCastleLong(BLACK));

		state = state.setCanCastleLong(WHITE, false);
		assertFalse(state.canCastleLong(WHITE));

	}


}
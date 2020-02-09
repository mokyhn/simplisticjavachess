package com.simplisticjavachess.game;

import org.junit.Test;

import static com.simplisticjavachess.piece.Color.BLACK;
import static com.simplisticjavachess.piece.Color.WHITE;
import static org.junit.Assert.*;

public class CastlingStateTest {

	@Test
	public void setCanCastleShort()
	{
		CastlingState state = new CastlingState();
		state = state.setCanCastleShort(WHITE, BLACK);
		assertTrue(state.canCastleShort(WHITE));
		assertTrue(state.canCastleShort(BLACK));

		state = state.setCannotCastleShort(WHITE);
		assertFalse(state.canCastleShort(WHITE));

	}

	@Test
	public void setCanCastleLong() {
		CastlingState state = new CastlingState();
		state = state.setCanCastleLong(WHITE, BLACK);
		assertTrue(state.canCastleLong(WHITE));
		assertTrue(state.canCastleLong(BLACK));

		state = state.setCannotCastleLong(WHITE);
		assertFalse(state.canCastleLong(WHITE));

	}

}
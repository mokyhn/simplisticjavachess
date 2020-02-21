package com.simplisticjavachess.game;

import com.simplisticjavachess.piece.Color;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

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

	/**
	 * @param number number to get bit from
 	 * @param position the index to read the bit
	 * @return a bit
	 */
	private boolean getBit(int number, int position)
	{
		return ((number >> position) & 1) == 1;
	}

	@Test
	public void testCastling()
	{
		Set<CastlingState> result = new HashSet<>();
		Set<Integer> hashes = new HashSet<>();

		for (int i = 0; i < 2*2*2*2; i++)
		{
			CastlingState state = new CastlingState(getBit(i, 0), getBit(i, 1),
					getBit(i, 2), getBit(i, 3));
			result.add(state);
			hashes.add(state.getChessHashCode());
		}

		assertEquals(16, result.size());
		assertEquals(16, hashes.size());
	}


}
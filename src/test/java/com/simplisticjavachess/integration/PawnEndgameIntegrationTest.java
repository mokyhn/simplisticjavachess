package com.simplisticjavachess.integration;

import org.junit.*;

import static com.simplisticjavachess.integration.TestSearch.search;
import static org.junit.Assert.assertTrue;

public class PawnEndgameIntegrationTest {

	/**
	 *  _______________
	 *  . . . . . . . .     8
	 *  . . . . . . . .     7
	 *  . . . . . . . .     6
	 *  . . . k . . . .     5
	 *  . . . P . . . .     4
	 *  . . . . . . . .     3
	 *  . . . K . . . .     2
	 *  . . . . . . . .     1
	 *  _______________
	 *  a b c d e f g h
	 *   White to move
	 */
	@Test
	public void avoidLoosingPawn1() throws Exception
	{
		assertTrue(search("8/8/8/3k4/3P4/8/3K4/8 w", "", 2, "d2d3 d2c3 d2e3"));
	}


	/**
	 *  _______________
	 *  . . . . . . . .     8
	 *  . . . k . . . .     7
	 *  . . . . . . . .     6
	 *  . . . p . . . .     5
	 *  . . . K . . . .     4
	 *  . . . . . . . .     3
	 *  . . . . . . . .     2
	 *  . . . . . . . .     1
	 *  _______________
	 *  a b c d e f g h
	 *   Black to move
	 */
	@Test
	public void avoidLoosingPawn2() throws Exception
	{
		assertTrue(search("8/3k4/8/3p4/3K4/8/8/8 b", "",  2, "d7d6 d7c6 d7e6"));
	}


	/**
	 *  _______________
	 *  . . . k . . . .     8
	 *  . . . P . . . .     7
	 *  . . . K . . . .     6
	 *  . . . . . . . .     5
	 *  . . . . . . . .     4
	 *  . . . . . . . .     3
	 *  . . . . . . . .     2
	 *  . . . . . . . .     1
	 *  _______________
	 *  a b c d e f g h
	 *   White to move
	 */
	@Test
	public void avoidLoosingPawn3() throws Exception
	{
		assertTrue(search("3k4/3P4/3K4/8/8/8/8/8 w", "",  4, "d6e6 d6c6"));
	}

	/**
	 *  _______________
	 *  . . . . . . . .     8
	 *  . . . . . . . .     7
	 *  . . . . . . . .     6
	 *  . . . . . . . .     5
	 *  . . . . . . . .     4
	 *  . . . k . . . .     3
	 *  . . . p . . . .     2
	 *  . . . K . . . .     1
	 *  _______________
	 *  a b c d e f g h
	 *   Black to move
	 */
	@Test
	public void simpleTest4() throws Exception
	{
		assertTrue(search("8/8/8/8/8/3k4/3p4/3K4 b", "",  4, "d3e3 d3c3"));
	}

	/**
	 *  _______________
	 *  . . . k . . . .     8
	 *  . . . P . . . .     7
	 *  . . . . . . . .     6
	 *  . . . K . . . .     5
	 *  . . . . . . . .     4
	 *  . . . . . . . .     3
	 *  . . . . . . . .     2
	 *  . . . . . . . .     1
	 *  _______________
	 *  a b c d e f g h
	 *   White to move
	 */
	@Test
	public void avoidStaleMate1() throws Exception
	{
		assertTrue(search("3k4/3P4/8/3K4/8/8/8/8 w", "",  4, "d5c6 d5e6"));
	}

	/**
	 * _______________
	 *  . . . . . . . .     8
	 *  . . . . . . . .     7
	 *  . . . . . . . .     6
	 *  . . . . . . . .     5
	 *  . . . k . . . .     4
	 *  . . . . . . . .     3
	 *  . . . p . . . .     2
	 *  . . . K . . . .     1
	 *  _______________
	 *  a b c d e f g h
	 *   Black to move
	 *
	 */
	@Test
	public void avoidStaleMate2() throws Exception
	{
		assertTrue(search("8/8/8/8/3k4/8/3p4/3K4 b", "",  4, "d4e3 d4c3"));
	}

}

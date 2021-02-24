package com.simplisticjavachess.end2end;

import com.simplisticjavachess.End2EndTest;
import org.junit.Test;

import static com.simplisticjavachess.end2end.TestSearch.assertMove;

@End2EndTest
public class PawnEndgamesTest {

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
	public void avoidLoosingPawn1()
	{
		assertMove("8/8/8/3k4/3P4/8/3K4/8 w", "", "d2d3 d2c3 d2e3", 2);
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
	public void avoidLoosingPawn2()
	{
		assertMove("8/3k4/8/3p4/3K4/8/8/8 b", "", "d7d6 d7c6 d7e6", 2);
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
	public void avoidLoosingPawn3()
	{
		assertMove("3k4/3P4/3K4/8/8/8/8/8 w", "", "d6e6 d6c6", 4);
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
	public void simpleTest4()
	{
		assertMove("8/8/8/8/8/3k4/3p4/3K4 b", "", "d3e3 d3c3", 4);
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
	public void avoidStaleMate1()
	{
		assertMove("3k4/3P4/8/3K4/8/8/8/8 w", "", "d5c6 d5e6", 4);
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
	public void avoidStaleMate2()
	{
		assertMove("8/8/8/8/3k4/8/3p4/3K4 b", "", "d4e3 d4c3", 6);
	}

	/**
	 *  _______________
	 *  . . . . . . . .     8
	 *  . . . . . . . .     7
	 *  . . . . . . . .     6
	 *  . . . . . p p p     5
	 *  . . . . . p k p     4
	 *  . . . . . . . .     3
	 *  . . . . . . K P     2
	 *  . . . . . . . .     1
	 *  _______________
	 *  a b c d e f g h
	 *   White to move
	 */
	@Test
	public void testSimplePawnMoves2()
	{
		// Mate in one
		assertMove("8/8/8/5ppp/5pkp/8/6KP/8 w", "", "h2h3", 4);
	}

	/**
	 * _______________
	 *  . . . . k . . .     8
	 *  . . . . . . . .     7
	 *  . . . . . . . .     6
	 *  . . . . . . . .     5
	 *  . . . . . . . .     4
	 *  . . . . . . . p     3
	 *  . . . . . P P P     2
	 *  . . . . K . . .     1
	 *  _______________
	 *  a b c d e f g h
	 *   Black to move
	 */
	@Test
	public void testPawnCaptures()
	{
		String fen = "4k3/8/8/8/8/7p/5PPP/4K3 b";
		String expected = "h3g2";
		assertMove(fen, "", expected, 1);
		assertMove(fen, "", expected, 2);
		assertMove(fen, "", expected, 3);

	}

	/**
	 * _______________
	 *  . . . . k . . .     8
	 *  . . . . . . . .     7
	 *  . . . . . . . P     6
	 *  . . . . . . . .     5
	 *  . . . . . . . .     4
	 *  . . . . . . . .     3
	 *  . . . . . . . .     2
	 *  . . . . . . . K     1
	 *  _______________
	 *  a b c d e f g h
	 *   White to move
	 */
	@Test
	public void testPawnPromotion()
	{
		assertMove("4k3/8/7P/8/8/8/8/7K w", "", "h6h7", 6);
	}

	/**
	 *  _______________
	 *  . . . . k . . .     8
	 *  . . . . . . . .     7
	 *  . . . . . . . .     6
	 *  . . . . . . . .     5
	 *  . . . . . . . .     4
	 *  . . . . . . K .     3
	 *  . . . . . . . p     2
	 *  . . . . . . . .     1
	 *  _______________
	 *  a b c d e f g h
	 *   White to move
	 */
	@Test
	public void avoidOpponentPromotion()
	{
		//TODO: Some bug exists in the engine. It should not be needed to supply g3g2 as an option.
		assertMove("4k3/8/8/8/8/6K1/7p/8 w", "", "g3g2 g3h2", 3);
	}


	/**
	 *  _______________
	 *  . . . . k . . .     8
	 *  P . . . . . . .     7
	 *  . . . . K . . .     6
	 *  . . . . . . . .     5
	 *  . . . . . . . .     4
	 *  . . . . . . . .     3
	 *  . . . . . . . .     2
	 *  . . . . . . . .     1
	 *  _______________
	 *  a b c d e f g h
	 *   White to move
	 *
	 */
	@Test
	public void mateWithPawnPromotion()
	{
		assertMove("4k3/P7/4K3/8/8/8/8/8 w", "", "a7a8Q", 1);
		assertMove("4k3/P7/4K3/8/8/8/8/8 w", "", "a7a8Q", 2);
		assertMove("4k3/P7/4K3/8/8/8/8/8 w", "", "a7a8Q", 3);
	}
}

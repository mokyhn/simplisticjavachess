/**
 * @author Morten Kühnrich
 */

package com.simplisticjavachess.integration;

import static com.simplisticjavachess.integration.TestSearch.assertMove;

import com.simplisticjavachess.End2EndTest;
import org.junit.Test;

@End2EndTest
public class KnightTest {
    private static final String KNIGHT_TAKES_PAWN_RIGHT = "k7/4n3/8/5P2/8/8/8/K7 b";
    private static final String KNIGHT_TAKES_PAWN_LEFT = "k7/4n3/8/3P4/8/8/8/K7 b";
    private static final String KNIGHT_TAKE_ROOK_OR_QUEEN = "k7/4R3/8/3n4/8/2Q5/8/K7 b";

    @Test
    public void knightTest1() {
        assertMove(KNIGHT_TAKE_ROOK_OR_QUEEN, "", "d5c3", 3);
    }

    @Test
    public void knightTest2() {
        assertMove(KNIGHT_TAKE_ROOK_OR_QUEEN, "", "d5e7 d5c3", 5);
    }

    @Test
    public void knightTest3() {
        assertMove(KNIGHT_TAKES_PAWN_LEFT, "", "e7d5", 5);
    }

    @Test
    public void knightTest4() {
        assertMove(KNIGHT_TAKES_PAWN_RIGHT, "", "e7f5", 5);
    }

    @Test
    public void knightTestMinMax3() {
        assertMove("q7/ppp1N1k1/5pN1/N4PN1/N2N2N1/8/p2PPPPP/4K3 w", "", "d4e6", 3);
    }

    @Test
    public void knightTestMinMax4() {
        assertMove("q7/ppp1N1k1/5pN1/N4PN1/N2N2N1/8/p2PPPPP/4K3 w", "", "d4e6", 4);
    }


}

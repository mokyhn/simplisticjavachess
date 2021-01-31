package com.simplisticjavachess.integration;

/**
 * @author Morten Kühnrich
 */

import com.simplisticjavachess.End2EndTest;
import org.junit.Test;

import static com.simplisticjavachess.integration.TestSearch.assertMove;

@End2EndTest
public class MateTests {

    private static final String WHITE_READY_TO_MATE_WITH_QUEEN_COMPLEX = "r1b1k3/1p3R2/p2p1Q2/P1pPp1P1/3P4/1P6/4P1P1" +
            "/RN2KBN1 w Q - 0 0";

    private static final String MATE_WITH_PAWN_0 = "k7/P7/KP6/8/7p/8/8/8 w";
    private static final String MATE_WITH_PAWN_1 = "k7/P7/KP6/8/7p/8/1P6/8 w";
    private static final String SUFFUCATED_MATE_TEST_2 = "r3r2k/6pp/8/6N1/2Q5/1B6/1PPP4/2K5 w";

    @Test
    public void mate_with_pawn_1_test() {
        assertMove(MATE_WITH_PAWN_0, "", "b6b7", 3);
    }

    @Test
    public void mate_with_pawn_2_test() {
        assertMove(MATE_WITH_PAWN_0, "", "b6b7", 3);
    }

    @Test
    public void mate_with_pawn_3_test() {
        assertMove(MATE_WITH_PAWN_1, "", "b6b7", 3);
    }

    @Test
    public void mate_with_pawn_4_test() {
        assertMove(MATE_WITH_PAWN_1, "", "b6b7", 3);
    }

    @Test
    public void mate_with_queen_1_test() {
        assertMove(WHITE_READY_TO_MATE_WITH_QUEEN_COMPLEX, "", "f6e7", 3);
    }

    @Test
    public void suffucated_mate_3_test() {
        assertMove(SUFFUCATED_MATE_TEST_2, "", "c4g8", 4);
    }

    @Test
    public void suffucated_mate_4_test() {
        assertMove(SUFFUCATED_MATE_TEST_2, "", "c4g8", 4);
    }

}

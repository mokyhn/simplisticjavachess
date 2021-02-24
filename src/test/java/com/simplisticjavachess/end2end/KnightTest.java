/**
 * @author Morten Kühnrich
 */

package com.simplisticjavachess.end2end;

import static com.simplisticjavachess.end2end.TestSearch.assertMove;

import com.simplisticjavachess.End2EndTest;
import org.junit.Test;

@End2EndTest
public class KnightTest {

    @Test
    public void knightTestMinMax3() {
        assertMove("q7/ppp1N1k1/5pN1/N4PN1/N2N2N1/8/p2PPPPP/4K3 w", "", "d4e6", 3);
    }

    @Test
    public void knightTestMinMax4() {
        assertMove("q7/ppp1N1k1/5pN1/N4PN1/N2N2N1/8/p2PPPPP/4K3 w", "", "d4e6", 4);
    }


}

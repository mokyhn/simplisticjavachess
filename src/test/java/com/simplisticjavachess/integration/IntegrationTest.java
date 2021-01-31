package com.simplisticjavachess.integration;

/**
 * @author Morten Kühnrich
 */

import com.simplisticjavachess.position.PositionIO;
import com.simplisticjavachess.position.IllegalMoveException;
import com.simplisticjavachess.position.Mover;
import com.simplisticjavachess.position.Position;
import org.junit.Test;

import static com.simplisticjavachess.integration.TestSearch.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;

public class IntegrationTest
{

    @Test
    public void fiftyMoveRuleDraw() throws IllegalMoveException {
        /*
         _______________
         k . . . . . . .     8
         . . . . . . . .     7
         . . . . . . . .     6
         . . . . . . . .     5
         . . . . . . . .     4
         . . . . . . . .     3
         N B . . . . . .     2
         K . . . . . . .     1
         _______________
         a b c d e f g h
          White to move
         */
        Position position = PositionIO.FEN("k7/8/8/8/8/8/NB6/K7 w - - 49 49");
        assertFalse(position.isDrawBy50Move());
        Position drawPosition = Mover.doMove(position, "a1b1");
        assertTrue(drawPosition.isDrawBy50Move());
    }

    @Test
    public void staleMateTest()
    {
        /*
         _______________
         . . . . . . . .     8
         . . . . . . . p     7
         . . . . . . . P     6
         . . . . . . . P     5
         . . . . . . . P     4
         . . . . . . . P     3
         . . . . k . . P     2
         . . . . . . . K     1
         _______________
         a b c d e f g h
           Black to move
        */
        // Chose stalemate move
        assertMove("8/7p/7P/7P/7P/7P/4k2P/7K b - - 0 0", "", "e2f1 e2f2", 4);
    }

    
    @Test
    public void avoidingMateMinMax()
    {
        /*
         _______________
         k . . . . . . .     8
         P . p . . . . .     7
         K P . . . . . .     6
         . . . . . . . .     5
         . . . . . . . .     4
         . . . . . . . .     3
         . . p . . . . .     2
         . . . . . . . .     1
         _______________
         a b c d e f g h
          Black to move
                 */
        assertMove("k7/P1p5/KP6/8/8/8/2p5/8 b - - 0 1", "", "c7b6", 4);
    }

    @Ignore(value = "slow")
    @Test
    public void bratkoKopecTest()
    {
       assertMove("1k1r4/pp1b1R2/3q2pp/4p3/2B5/4Q3/PPP2B2/2K5 b", "", "d6d1", 7); //BK.01
    }
    
    @Ignore(value = "slow")
    @Test
    public void endGameTacticsTest()
    {
        assertMove("7k/ppp5/8/PPP5/8/8/8/7K w", "", "b5b6", 10);
    }
  
}

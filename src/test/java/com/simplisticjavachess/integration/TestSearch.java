/**
 *
 * @author Morten Kühnrich
 */

package com.simplisticjavachess.integration;

import com.simplisticjavachess.board.Board;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.move.MoveParser;
import com.simplisticjavachess.engine.Engine;
import com.simplisticjavachess.engine.MinMaxEngine;
import com.simplisticjavachess.engine.SearchResult;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class TestSearch
{


    public static void assertMove(String expectedMoves, String fen, String moveSequence, int plyDepth)
    {
        Board board = Board.createFromFEN(fen);
        System.out.println(board.asASCII());

        // Do initial set of moves
        try {
            performMoves(board, moveSequence);

            //TODO: Only min max engine tested here
            Engine engine = new MinMaxEngine();

            SearchResult searchResult = engine.search(board, plyDepth);

            Move foundMove = searchResult.getMoveSequence().getFirst();

            if (foundMove == null && expectedMoves.isEmpty())
            {
                assertTrue(true);
            }

            Collection<Move> expected = parseExpectedMoves(board, expectedMoves);
            if (expected.contains(foundMove))
            {
                assertTrue(true);
            }
            else
            {
                fail("Engine failed by playing: " + searchResult);
            }
        } catch (Exception e) {
            System.out.println("Failed in setup of pieces");
            fail();
        }
    }

    private static void performMoves(Board board, String moveSequence)
    {
        if (!moveSequence.isEmpty()) 
        {
            String[] moveStrings = moveSequence.split(" ");
            for (String str : moveStrings)
            {
                Move move = MoveParser.parseMove(board, str);        
                board.doMove(move);             
            }   
        }  
    }
    
    private static Collection<Move> parseExpectedMoves(Board board, String expectedMoves)
    {
        ArrayList<Move> result = new ArrayList<>();

        if (!expectedMoves.isEmpty())
        {
            String[] expectedMoveStrings = expectedMoves.split(" ");
            for (String str : expectedMoveStrings)
            {
                Move move = MoveParser.parseMove(board, str);
                result.add(move);
            }        
        }
        
        return result;
    }
}

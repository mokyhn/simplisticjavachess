/**
 *
 * @author Morten Kühnrich
 */

package com.simplisticjavachess.integration;

import com.simplisticjavachess.board.Board;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.move.MoveParser;
import com.simplisticjavachess.search.Search;
import com.simplisticjavachess.search.MinMaxSearch;
import com.simplisticjavachess.search.SearchResult;
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


            Search engine;

            engine = new MinMaxSearch();

            SearchResult searchResult = engine.search(board, plyDepth);

            if (searchResult.getMoveSequence().getFirst() == null && expectedMoves.isEmpty())
            {
                assertTrue(true);
            }

            Collection<Move> expected = parseExpectedMoves(board, expectedMoves);

            if (expected.contains(searchResult.getMoveSequence().getFirst()))
            {
                assertTrue(true);
            }
            else
            {
                fail();
            }
        } catch (Exception e) {
            System.out.println("Failed in setup of pieces");
            fail();
        }
    }

    private static void performMoves(Board board, String moveSequence) throws Exception
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
    
    private static Collection<Move> parseExpectedMoves(Board board, String expectedMoves) throws Exception
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

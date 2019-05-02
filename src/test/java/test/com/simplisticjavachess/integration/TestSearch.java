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

public class TestSearch
{
   
    public static boolean search(String fen, String moveSequence, int plyDepth, String expectedMoves) throws Exception
    {
        Board board = Board.createFromFEN(fen);
        
        // Do initial set of moves
        performMoves(board, moveSequence);

        Collection<Move> expected = parseExpectedMoves(board, expectedMoves);

        Search engine;

        engine = new MinMaxSearch();
        
        SearchResult searchResult = engine.search(board, plyDepth);

        if (searchResult.getMoveSequence().getFirst() == null && expectedMoves.isEmpty())
        {
            return true;
        }            

        for (Move move : expected)
        {
            if (move.equals(searchResult.getMoveSequence().getFirst()))
            {
                return true;
            }
        }
        
        if (expectedMoves.isEmpty())
        {
            System.out.println("Engine found: " + searchResult.getMoveSequence().toString() + " BUT no move was expected in position " + fen + "\n");
        } else
        {
            System.out.println("Engine found: " + searchResult.getMoveSequence().toString() + "BUT expected " + expectedMoves + " in position " + fen + "\n");
        }
        
        return false;        
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

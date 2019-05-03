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


    public static boolean search(String fen, String moveSequence, int plyDepth, String expectedMoves)
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
                return true;
            }

            Collection<Move> expected = parseExpectedMoves(board, expectedMoves);

            return expected.contains(searchResult.getMoveSequence().getFirst());

        } catch (Exception e) {
            System.out.println("Failed in setup of pieces");
            return false;
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

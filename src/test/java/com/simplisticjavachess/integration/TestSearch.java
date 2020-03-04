/**
 *
 * @author Morten Kühnrich
 */

package com.simplisticjavachess.integration;

import com.simplisticjavachess.board.BoardParser;
import com.simplisticjavachess.board.Mover;
import com.simplisticjavachess.board.Position;
import com.simplisticjavachess.evaluation.IntegerEvaluator;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.move.MoveParser;
import com.simplisticjavachess.engine.Engine;
import com.simplisticjavachess.engine.MinMaxEngine;
import com.simplisticjavachess.engine.SearchResult;
import com.simplisticjavachess.movegenerator.MainMoveGenerator;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class TestSearch
{


    public static void assertMove(String expectedMoves, String fen, String moveSequence, int plyDepth)
    {
        Position position = BoardParser.FEN(fen);
        System.out.println(position.toString());

        // Do initial set of moves
        try {
            position = performMoves(position, moveSequence);

            Engine engine = new MinMaxEngine();

            SearchResult searchResult = engine.search(position, new MainMoveGenerator(), new IntegerEvaluator(), plyDepth);

            Move foundMove = searchResult.getMoveSequence().getFirst();

            if (foundMove == null && expectedMoves.isEmpty())
            {
                assertTrue(true);
            }

            Collection<Move> expected = parseExpectedMoves(position, expectedMoves);
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

    private static Position performMoves(Position position, String moveSequence)
    {
        if (!moveSequence.isEmpty()) 
        {
            String[] moveStrings = moveSequence.split(" ");
            for (String str : moveStrings)
            {
                Move move = MoveParser.parse(position, str);
                position = Mover.doMove(position, move).getPosition();
            }   
        }
        return position;
    }

    private static Collection<Move> parseExpectedMoves(Position position, String expectedMoves)
    {
        ArrayList<Move> result = new ArrayList<>();

        if (!expectedMoves.isEmpty())
        {
            String[] expectedMoveStrings = expectedMoves.split(" ");
            for (String str : expectedMoveStrings)
            {
                Move move = MoveParser.parse(position, str);
                result.add(move);
            }        
        }
        
        return result;
    }
}

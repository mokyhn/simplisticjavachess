/**
 * @author Morten Kühnrich
 */

package com.simplisticjavachess.integration;

import com.simplisticjavachess.engine.MinMaxEngine;
import com.simplisticjavachess.evaluation.IntegerEvaluator;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.move.MoveParser;
import com.simplisticjavachess.engine.Engine;
import com.simplisticjavachess.engine.SearchResult;
import com.simplisticjavachess.movegenerator.MainMoveGenerator;
import com.simplisticjavachess.position.ChessMover;
import com.simplisticjavachess.position.IllegalMoveException;
import com.simplisticjavachess.position.Mover;
import com.simplisticjavachess.position.Position;
import com.simplisticjavachess.position.PositionIO;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class TestSearch {
    private final static Mover mover = new ChessMover();

    public static void assertMove(String fen, String moveSequence, String expectedMoves, int plyDepth) {
        Position position = PositionIO.FEN(fen);

        System.out.println(position);

        // Do initial set of moves
        try {
            position = mover.doMove(position, moveSequence);

            Engine engine = new MinMaxEngine();

            SearchResult searchResult = engine.search(position, mover, new MainMoveGenerator(),
                    new IntegerEvaluator(), plyDepth);

            Move foundMove = searchResult.getMoveSequence().getFirst();

            if (foundMove == null && expectedMoves.isEmpty()) {
                assertTrue(true);
            }

            Collection<Move> expected = parseExpectedMoves(position, expectedMoves);
            if (expected.contains(foundMove)) {
                assertTrue(true);
                System.out.println(searchResult);
            } else {
                fail("Engine failed by playing: " + searchResult);
            }
        } catch (IllegalMoveException e) {
            System.out.println("Error in move");
        } catch (Exception e) {
            System.out.println("Failure " + e.toString());
            fail();
        }
    }


    private static Collection<Move> parseExpectedMoves(Position position, String expectedMoves) {
        ArrayList<Move> result = new ArrayList<>();

        if (!expectedMoves.isEmpty()) {
            String[] expectedMoveStrings = expectedMoves.split(" ");
            for (String str : expectedMoveStrings) {
                Move move = MoveParser.parse(position, str);
                result.add(move);
            }
        }

        return result;
    }
}

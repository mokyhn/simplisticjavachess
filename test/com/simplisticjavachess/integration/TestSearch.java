/**
 *
 * @author Morten Kühnrich
 */

package com.simplisticjavachess.integration;

import com.simplisticjavachess.board.Board;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.move.MoveParser;
import com.simplisticjavachess.search.AbstractSearch;
import com.simplisticjavachess.search.AlphaBetaSearch;
import com.simplisticjavachess.search.MinMaxSearch;
import java.util.ArrayList;
import java.util.Collection;

public class TestSearch
{
    public final static int ALPHABETA = 1;
    public final static int MINMAX = 2;
    
    public static boolean search(String fen, String moveSequence, int method, int plyDepth, String expectedMoves) throws Exception
    {
        Board board = Board.createFromFEN(fen);
        
        // Do initial set of moves
        performMoves(board, moveSequence);

        Collection<Move> expected = parseExpectedMoves(board, expectedMoves);

        AbstractSearch engine;

        switch (method)
        {
            case ALPHABETA:
                engine = new AlphaBetaSearch();
                break;
            case MINMAX:
                engine = new MinMaxSearch();
                break;
            default:
                engine = new AlphaBetaSearch();
                break;
        }
        engine.setBoard(board);
        engine.search(plyDepth);
        Move strongestMove = engine.getStrongestMove();

        if (strongestMove == null && expectedMoves.isEmpty())
        {
            return true;
        }            

        for (Move move : expected)
        {
            if (move.equals(strongestMove))
            {
                return true;
            }
        }
        
        if (expectedMoves.isEmpty())
        {
            System.out.println("Engine found: " + strongestMove + " BUT no move was expected in position " + fen + "\n");
        } else
        {
            System.out.println("Engine found: " + strongestMove + "BUT expected " + expectedMoves + " in position " + fen + "\n");
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
        ArrayList<Move> result = new ArrayList<Move>();

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

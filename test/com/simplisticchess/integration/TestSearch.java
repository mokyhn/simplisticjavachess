/**
 *
 * @author Morten Kühnrich
 */

package com.simplisticchess.integration;

import com.simplisticchess.board.Board;
import com.simplisticchess.move.Move;
import com.simplisticchess.move.MoveParser;
import com.simplisticchess.search.AbstractSearch;
import com.simplisticchess.search.AlphaBetaSearch;
import com.simplisticchess.search.MinMaxSearch;
import java.util.ArrayList;
import java.util.Collection;

public class TestSearch
{
    public final static int ALPHABETA = 1;
    public final static int MINMAX = 2;
    
    public static boolean search(String fen, String moveSequence, int method, int plyDepth, String expectedMoves) throws Exception
    {
        Board board = new Board(fen);
        
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
        engine.setPlyDepth(plyDepth);
        engine.setBoard(board);
        engine.dosearch();
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
            System.out.println("Engine found: " + engine.getStatistics() + "BUT expected " + expectedMoves + " in position " + fen + "\n");
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

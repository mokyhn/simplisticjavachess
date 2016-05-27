/**
 *
 * @author Morten Kühnrich
 */

package com.simplisticchess.integration;

import com.simplisticchess.board.Board;
import com.simplisticchess.board.FENPositions;
import com.simplisticchess.move.Move;
import com.simplisticchess.movegenerator.MoveGenerator;
import com.simplisticchess.piece.Color;
import java.util.Iterator;
import org.junit.Ignore;
import org.junit.Test;

public class FullDepthSearchTest
{
    //@Ignore("This test pinpoints where we have problems with move generation/move/undo/search... (assuming that we do not clone boards in the move generator)")
    @Test
    public void testFullDepth() 
    {
        int i = 0;
        for (String fen : FENPositions.POSITIONS)
        {
            
            fen =  "K7/8/1p6/8/5p2/8/4P3/8 b";

            i++;
            //System.out.println("testing position " + i);
            Board board = new Board(fen);
            try 
            {
                
                System.out.println(board.getASCIIBoard());
                testFullDepthSearchHelper(board, 0);
            }
            catch (Exception e)
            {
             e.printStackTrace();
                System.out.println("Failed on position " + i + " " + fen);
                System.out.println(board.getASCIIBoard());
            }
            finally 
            {
                if (i > 1) 
                {
                    return;
                }
            }
           
        } 

    }
    
    public void testFullDepthSearchHelper(Board board, int depth) throws Exception
    {

            //System.out.println("Depth " + depth);
            if (depth == 4) 
            {
                return;
            }
                    
            MoveGenerator moveGenerator = new MoveGenerator();
            
            Iterator<Move> moves = moveGenerator.generateMoves(board);
            
            while (moves.hasNext())
            {
                Move move = moves.next();
                boolean valid = board.doMove(move);
                
                if (!valid)
                {
                    board.undo();
                    continue;
                }
                if (board.inMove() == Color.WHITE) 
                {
                    System.out.println(indent(depth, depth + "..." + move.toString()));

                } else 
                {
                    System.out.println(indent(depth, depth + "   " + move.toString()));
                }
                
                testFullDepthSearchHelper(board, depth+1);
                
                System.out.println(indent(depth, "undo"));
                board.undo();                
            }                           
        }
      
        private String indent(int n, String s)
        {
            String result = "";
            
            for (int i = 0; i < 2*n; i++) 
            {
                result += " ";   
            }
            return result += s;
        }
    
}

/**
 *
 * @author Morten Kühnrich
 */

package com.simplisticchess.integration;

import com.simplisticchess.board.Board;
import com.simplisticchess.board.FENPositions;
import com.simplisticchess.move.Move;
import com.simplisticchess.movegenerator.MoveGenerator;
import java.util.Iterator;
import org.junit.Ignore;
import org.junit.Test;

public class FullDepthSearch
{
    @Ignore("This test pinpoints where we have problems with move generation/move/undo/search... (assuming that we do not clone boards in the move generator)")
    @Test
    public void testFullDepth() 
    {
        int i = 0;
        for (String fen : FENPositions.POSITIONS)
        {
             i++;
            //System.out.println("testing position " + i);
            try 
            {
                testFullDepthSearchHelper(new Board(fen), 4);
            }
            catch (Exception e)
            {
                System.out.println("Failed on position " + i + " " + fen);
            }
           
        }

    }
    
    public void testFullDepthSearchHelper(Board board, int depth)
    {
            //System.out.println("Depth " + depth);
            if (depth == 0) 
            {
                return;
            }
                    
            MoveGenerator moveGenerator = new MoveGenerator();
            
            Iterator<Move> moves = moveGenerator.generateMoves(board);
            
            while (moves.hasNext())
            {
                boolean valid = board.doMove(moves.next());
                
                if (!valid)
                {
                    board.undo();
                    continue;
                }
                
                testFullDepthSearchHelper(board, depth-1);
                
                board.undo();                
            }   
            
            
    }
    
}

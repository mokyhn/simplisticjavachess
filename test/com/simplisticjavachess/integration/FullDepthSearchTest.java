/**
 *
 * @author Morten Kühnrich
 */

package com.simplisticjavachess.integration;

import com.simplisticjavachess.board.Board;
import com.simplisticjavachess.board.FENPositions;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.movegenerator.MoveGenerator;
import com.simplisticjavachess.piece.Color;
import java.util.Iterator;
import org.junit.Test;

public class FullDepthSearchTest
{
    //@Ignore("This test pinpoints where we have problems with move generation/move/undo/search... (assuming that we do not clone boards in the move generator)")
    @Test
    public void testFullDepth() throws Exception
    {
        int i = 0;
        for (String fen : FENPositions.POSITIONS)
        {
            

            i++;
            System.out.println("testing position " + i + " with fen " + fen);
            Board board = new Board(fen);
            testFullDepthSearchHelper(board, 0);
           
        } 

    }
    
    public void testFullDepthSearchHelper(Board board, int depth) throws Exception
    {

            //System.out.println("Depth " + depth);
            if (depth == 2) 
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
                    //System.out.println(indent(depth, depth + "..." + move.toString()));

                } else 
                {
                    //System.out.println(indent(depth, depth + "   " + move.toString()));
                }
                
                testFullDepthSearchHelper(board, depth+1);
                
                //System.out.println(indent(depth, "undo"));
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

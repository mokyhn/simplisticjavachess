/**
 *
 * @author Morten Kühnrich
 */

package com.simplisticchess.movegenerator;

import com.simplisticchess.board.Board;
import com.simplisticchess.move.Move;
import com.simplisticchess.piece.Piece;
import java.util.ArrayList;

public class RookMoveGenerator
{
    public ArrayList<Move> generateMoves(Board b, Piece p)
    {
        final ArrayList<Move> Moves = new ArrayList<Move>();
        int d; // Displacement

        Move newMove;

        //System.out.println("h");
        //System.exit(0);
        // Move up
        for (d = 1; (p.getyPos() + d <= 7); d++)
        {
            newMove = MoveGeneratorUtil.genMove(b, p, 0, d);
            if (newMove == null)
            {
                break; // The square was occupied by my own piece
            }
            Moves.add(newMove);
            if (newMove.aCapture())
            {
                break; // The square was occopied by an opponent square
            }
        }

        // Move down
        for (d = 1; (p.getyPos() - d >= 0); d++)
        {
            newMove = MoveGeneratorUtil.genMove(b, p, 0, -d);
            if (newMove == null)
            {
                break; // The square was occupied by my own piece
            }
            Moves.add(newMove);
            if (newMove.aCapture())
            {
                break; // The square was occopied by an opponent square
            }
        }

        // Move left
        for (d = 1; (p.getxPos() - d >= 0); d++)
        {
            newMove = MoveGeneratorUtil.genMove(b, p, -d, 0);
            if (newMove == null)
            {
                break; // The square was occupied by my own piece
            }
            Moves.add(newMove);
            if (newMove.aCapture())
            {
                break; // The square was occopied by an opponent square
            }
        }

        // Move right 
        for (d = 1; (p.getxPos() + d <= 7); d++)
        {
            newMove = MoveGeneratorUtil.genMove(b, p, d, 0);
            if (newMove == null)
            {
                break; // The square was occupied by my own piece
            }
            Moves.add(newMove);
            if (newMove.aCapture())
            {
                break; // The square was occopied by an opponent square
            }
        }

        return Moves;
    }
    
}

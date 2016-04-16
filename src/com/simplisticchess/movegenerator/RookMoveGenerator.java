/**
 *
 * @author Morten Kühnrich
 */

package com.simplisticchess.movegenerator;

import com.simplisticchess.board.Board;
import com.simplisticchess.move.Move;
import com.simplisticchess.piece.Piece;
import java.util.ArrayList;
import java.util.Iterator;

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

    // TODO: The following can be refined so that not all moves are generated at once
    public Iterator<Move> iterator(final Board b, final Piece p)
    {
        return new Iterator<Move>()
        {
            Iterator<Move> generated = null;
            
            public boolean hasNext()
            {
                if (generated == null) 
                {
                    generated = generateMoves(b, p).iterator();
                }
                return generated.hasNext();
            }

            public Move next()
            {
                if (hasNext()) 
                {
                    return generated.next();
                }
                else
                {
                    return null;
                }
            }

            public void remove()
            {                
            }
        };
    }

    
}

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

public class KnightMoveGenerator
{
    public ArrayList<Move> generateMoves(Board b, Piece p)
    {
        final ArrayList<Move> Moves = new ArrayList<Move>();
        Move newMove;
        newMove = MoveGeneratorUtil.genMove(b, p, -2, 1);
        if (newMove != null)
        {
            Moves.add(newMove);
        }
        newMove = MoveGeneratorUtil.genMove(b, p, -2, -1);
        if (newMove != null)
        {
            Moves.add(newMove);
        }
        newMove = MoveGeneratorUtil.genMove(b, p, -1, 2);
        if (newMove != null)
        {
            Moves.add(newMove);
        }
        newMove = MoveGeneratorUtil.genMove(b, p, 1, 2);
        if (newMove != null)
        {
            Moves.add(newMove);
        }
        newMove = MoveGeneratorUtil.genMove(b, p, -1, -2);
        if (newMove != null)
        {
            Moves.add(newMove);
        }
        newMove = MoveGeneratorUtil.genMove(b, p, 1, -2);
        if (newMove != null)
        {
            Moves.add(newMove);
        }
        newMove = MoveGeneratorUtil.genMove(b, p, 2, 1);
        if (newMove != null)
        {
            Moves.add(newMove);
        }
        newMove = MoveGeneratorUtil.genMove(b, p, 2, -1);
        if (newMove != null)
        {
            Moves.add(newMove);
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

/**
 *
 * @author Morten Kühnrich
 */


package com.simplisticjavachess.movegenerator;

import com.simplisticjavachess.board.Board;
import com.simplisticjavachess.board.Vector;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.piece.Piece;
import java.util.ArrayList;
import java.util.Iterator;

public class BishopMoveGenerator
{
    
    private static ArrayList<Move> generateMoves(Board b, Piece p)
    {
        final ArrayList<Move> Moves = new ArrayList<Move>();
       
        Moves.addAll(LineMoveGenerator.generateMoves(b, p, new Vector(1, 1)));
        Moves.addAll(LineMoveGenerator.generateMoves(b, p, new Vector(1, -1)));
        Moves.addAll(LineMoveGenerator.generateMoves(b, p, new Vector(-1, 1)));
        Moves.addAll(LineMoveGenerator.generateMoves(b, p, new Vector(-1, -1)));
       
        return Moves;
    }
    
    // TODO: The following can be refined so that not all moves are generated at once
    public static Iterator<Move> getIterator(final Board b, final Piece p)
    {
        return new Iterator<Move>()
        {
            Iterator<Move> generated = null;
            
            @Override
            public boolean hasNext()
            {
                if (generated == null) 
                {
                    generated = generateMoves(b, p).iterator();
                }
                return generated.hasNext();
            }

            @Override
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

            @Override
            public void remove()
            {                
            }
        };
    }
}

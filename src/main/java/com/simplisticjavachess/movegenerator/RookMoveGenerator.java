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

public class RookMoveGenerator
{
    private static ArrayList<Move> generateMoves(Board board, Piece piece)
    {
        final ArrayList<Move> Moves = new ArrayList<>();

        Moves.addAll(LineMoveGenerator.generateMoves(board, piece, Vector.UP));
        Moves.addAll(LineMoveGenerator.generateMoves(board, piece, Vector.DOWN));
        Moves.addAll(LineMoveGenerator.generateMoves(board, piece, Vector.RIGHT));
        Moves.addAll(LineMoveGenerator.generateMoves(board, piece, Vector.LEFT));

        return Moves;
    }

    // TODO: The following can be refined so that not all moves are generated at once
    public static Iterator<Move> iterator(Board board, Piece piece)
    {
        return new Iterator<Move>()
        {
            Iterator<Move> generated = null;
            
            @Override
            public boolean hasNext()
            {
                if (generated == null) 
                {
                    generated = generateMoves(board, piece).iterator();
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

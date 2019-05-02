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
import java.util.List;

public class KnightMoveGenerator
{
    private static ArrayList<Move> generateMoves(Board board, Piece piece)
    {
        final ArrayList<Move> Moves = new ArrayList<>();

        addIfNotNull(Moves, MoveGeneratorUtil.genMove(board, piece, new Vector(-2, 1)));
        addIfNotNull(Moves, MoveGeneratorUtil.genMove(board, piece, new Vector(-2, -1)));
        addIfNotNull(Moves, MoveGeneratorUtil.genMove(board, piece, new Vector(-1, 2)));
        addIfNotNull(Moves, MoveGeneratorUtil.genMove(board, piece, new Vector(1, 2)));
        addIfNotNull(Moves, MoveGeneratorUtil.genMove(board, piece, new Vector(-1, -2)));
        addIfNotNull(Moves, MoveGeneratorUtil.genMove(board, piece, new Vector(1, -2)));
        addIfNotNull(Moves, MoveGeneratorUtil.genMove(board, piece, new Vector(2, 1)));
        addIfNotNull(Moves, MoveGeneratorUtil.genMove(board, piece, new Vector(2, -1)));

        return Moves;
    }

    private static void addIfNotNull(List<Move> moves, Move move)
    {
        if (move != null)
        {
            moves.add(move);
        }
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

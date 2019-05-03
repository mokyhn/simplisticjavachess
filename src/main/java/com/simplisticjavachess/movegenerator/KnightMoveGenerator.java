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

public class KnightMoveGenerator implements IMoveGenerator
{
    // TODO: The following can be refined so that not all moves are generated at once
    private static ArrayList<Move> generateMovesHelper(Board board, Piece piece)
    {
        final ArrayList<Move> moves = new ArrayList<>();
        addIfNotNull(moves, MoveGeneratorUtil.genMove(board, piece, new Vector(-2, 1)));
        addIfNotNull(moves, MoveGeneratorUtil.genMove(board, piece, new Vector(-2, -1)));
        addIfNotNull(moves, MoveGeneratorUtil.genMove(board, piece, new Vector(-1, 2)));
        addIfNotNull(moves, MoveGeneratorUtil.genMove(board, piece, new Vector(1, 2)));
        addIfNotNull(moves, MoveGeneratorUtil.genMove(board, piece, new Vector(-1, -2)));
        addIfNotNull(moves, MoveGeneratorUtil.genMove(board, piece, new Vector(1, -2)));
        addIfNotNull(moves, MoveGeneratorUtil.genMove(board, piece, new Vector(2, 1)));
        addIfNotNull(moves, MoveGeneratorUtil.genMove(board, piece, new Vector(2, -1)));
        return moves;
    }

    private static void addIfNotNull(List<Move> moves, Move move)
    {
        if (move != null)
        {
            moves.add(move);
        }
    }

    @Override
    public Iterator<Move> generateMoves(Board b, Piece p)
    {
        return new Iterator<Move>()
        {
            Iterator<Move> generated = null;
            
            @Override
            public boolean hasNext()
            {
                if (generated == null) 
                {
                    generated = generateMovesHelper(b, p).iterator();
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

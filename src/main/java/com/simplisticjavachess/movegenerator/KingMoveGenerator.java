/**
 *
 * @author Morten Kühnrich
 */
package com.simplisticjavachess.movegenerator;

import com.simplisticjavachess.board.Board;
import com.simplisticjavachess.board.Vector;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.move.MoveType;
import com.simplisticjavachess.piece.Color;
import com.simplisticjavachess.piece.Piece;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class KingMoveGenerator
{   
    private static ArrayList<Move> generateMoves(Board board, Piece piece)
    {
        final ArrayList<Move> moves = new ArrayList<>();
        final Color color = board.inMove();
        final int fy = piece.getyPos();

        // Castling short
        if (board.canCastleShort() &&
            board.freeSquare(5, fy) &&
            board.freeSquare(6, fy) &&
            !board.isAttacked(5, fy) &&
            !board.isAttacked(6, fy) &&
            !board.isInCheck(color))
        {
            moves.add(new Move(
                    piece.getLocation(), 
                    Vector.RIGHT_RIGHT.translocate(piece.getLocation()),
                    MoveType.CASTLE_SHORT, null, color));
        }

        // Castling long
        if (board.canCastleLong() &&
            board.freeSquare(3, fy) &&
            board.freeSquare(2, fy) &&
            board.freeSquare(1, fy) &&
            !board.isAttacked(2, fy) &&
            !board.isAttacked(3, fy) &&
            !board.isInCheck(color))
        {
            moves.add(new Move(
                    piece.getLocation(),
                    Vector.LEFT_LEFT.translocate(piece.getLocation()),
                    MoveType.CASTLE_LONG, null, color));
        }
        

        addIfNotNull(moves, MoveGeneratorUtil.genKingMove(board, piece, Vector.LEFT));
        addIfNotNull(moves, MoveGeneratorUtil.genKingMove(board, piece, Vector.RIGHT));        
        addIfNotNull(moves, MoveGeneratorUtil.genKingMove(board, piece, Vector.UP));
        addIfNotNull(moves, MoveGeneratorUtil.genKingMove(board, piece, Vector.DOWN));
        
        addIfNotNull(moves, MoveGeneratorUtil.genKingMove(board, piece, Vector.UP_AND_LEFT));
        addIfNotNull(moves, MoveGeneratorUtil.genKingMove(board, piece, Vector.DOWN_AND_LEFT));
        addIfNotNull(moves, MoveGeneratorUtil.genKingMove(board, piece, Vector.UP_AND_RIGHT));
        addIfNotNull(moves, MoveGeneratorUtil.genKingMove(board, piece, Vector.DOWN_AND_RIGHT));
        
        return moves;
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

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
import com.simplisticjavachess.piece.PieceType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class KingMoveGenerator
{   
    private static ArrayList<Move> generateMoves(Board b, Piece p)
    {
        final ArrayList<Move> moves = new ArrayList<>();
        final Color color = b.inMove();
        final int fx = p.getxPos();
        final int fy = p.getyPos();


        // Castling short
        if (fx == 4
                && b.canCastleShort()
                && b.freeSquare(5, fy)
                && b.freeSquare(6, fy)
                && !b.isAttacked(5, fy)
                && !b.isAttacked(6, fy)
                && !b.isInCheck(color))
        {
            assert (b.getPiece(7, fy) != null);
            assert (b.getPiece(7, fy).getPieceType() == PieceType.ROOK) : "Expected rook, found wirdo piece: " + b.getPiece(7, fy).toString() + "fx, fy=" + fx + ", " + fy + "c = " + color;
            assert (b.getPiece(7, fy).getColor() == color);
            moves.add(new Move(fx, fy, fx + 2, fy, MoveType.CASTLE_SHORT, null, color));
        }

        // Castling long
        if (fx == 4
                && b.canCastleLong()
                && b.freeSquare(3, fy)
                && b.freeSquare(2, fy)
                && b.freeSquare(1, fy)
                && !b.isAttacked(2, fy)
                && !b.isAttacked(3, fy)
                && !b.isInCheck(color))
        {
            moves.add(new Move(fx, fy, fx - 2, fy, MoveType.CASTLE_LONG, null, color));
        }
        

        addIfNotNull(moves, MoveGeneratorUtil.genKingMove(b, p, Vector.LEFT));
        addIfNotNull(moves, MoveGeneratorUtil.genKingMove(b, p, Vector.RIGHT));        
        addIfNotNull(moves, MoveGeneratorUtil.genKingMove(b, p, Vector.UP));
        addIfNotNull(moves, MoveGeneratorUtil.genKingMove(b, p, Vector.DOWN));
        
        addIfNotNull(moves, MoveGeneratorUtil.genKingMove(b, p, Vector.UP_AND_LEFT));
        addIfNotNull(moves, MoveGeneratorUtil.genKingMove(b, p, Vector.DOWN_AND_LEFT));
        addIfNotNull(moves, MoveGeneratorUtil.genKingMove(b, p, Vector.UP_AND_RIGHT));
        addIfNotNull(moves, MoveGeneratorUtil.genKingMove(b, p, Vector.DOWN_AND_RIGHT));
        
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

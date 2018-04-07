/**
 *
 * @author Morten Kühnrich
 */

package com.simplisticjavachess.movegenerator;

import com.simplisticjavachess.board.Board;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.move.MoveType;
import com.simplisticjavachess.piece.Color;
import com.simplisticjavachess.piece.Piece;
import com.simplisticjavachess.piece.PieceType;
import com.simplisticjavachess.board.Location;
import java.util.ArrayList;
import java.util.Iterator;

public class PawnMoveGenerator
{
    private static ArrayList<Move> generateMoves(Board board, Piece piece)
    {

        final Color c = board.inMove();
        final int fx = piece.getxPos();
        final int fy = piece.getyPos();

        Location from = piece.getLocation();
        
        Piece leftPiece;
        Piece rightPiece;

        final ArrayList<Move> Moves = new ArrayList<Move>();

        // Normal one step forward pawn move
        if (((fy < 6) && (c == Color.WHITE))
                || (fy > 1) && (c == Color.BLACK))
        {
            if (board.freeSquare(fx, fy + c.getColor() * 1))
            {
                Moves.add(new Move(fx, fy, fx, fy + c.getColor() * 1, MoveType.NORMALMOVE, null, c));
            }
        }

        // Normal two step forward pawn move
        if (((fy == 1) && (c == Color.WHITE))
                || ((fy == 6) && (c == Color.BLACK)))
        {
            if (board.freeSquare(fx, fy + c.getColor() * 1) && board.freeSquare(fx, fy + c.getColor() * 2))
            {
                Moves.add(new Move(fx, fy, fx, (fy + c.getColor() * 2), MoveType.NORMALMOVE, null, c));
            }
        }

        // Non capturing PAWN promotion
        if (((fy == 6) && (c == Color.WHITE) && board.freeSquare(fx, 7))
                || ((fy == 1) && (c == Color.BLACK) && board.freeSquare(fx, 0)))
        {
            Moves.add(new Move(fx, fy, fx, fy + c.getColor(), MoveType.PROMOTE_TO_QUEEN, null, c));
            Moves.add(new Move(fx, fy, fx, fy + c.getColor(), MoveType.PROMOTE_TO_ROOK, null, c));
            Moves.add(new Move(fx, fy, fx, fy + c.getColor(), MoveType.PROMOTE_TO_KNIGHT, null, c));
            Moves.add(new Move(fx, fy, fx, fy + c.getColor(), MoveType.PROMOTE_TO_BISHOP, null, c));
        }

        // Normal diagonal capturing to the left
        if ((fx > 0) && (fy != (5 * c.getColor() + 7) / 2))
        {
            leftPiece = board.getPiece(from);
            if (leftPiece != null && leftPiece.getColor() != c)
            {
                Location to = new Location(fx - 1, fy + c.getColor());
                Moves.add(new Move(from, to, MoveType.CAPTURE, leftPiece, c));
            }
        }

        // Normal diagonal capturing to the right
        if ((fx < 7) && (fy != (5 * c.getColor() + 7) / 2))
        {
            Location to = new Location(fx + 1, fy + c.getColor());
            rightPiece = board.getPiece(to);
            if (rightPiece != null && rightPiece.getColor() != c)
            {
                Moves.add(new Move(from, to, MoveType.CAPTURE, rightPiece, c));
            }
        }

        // Promotion via diagonal capturing to the left
        if ((fx > 0) && (fy == (5 * c.getColor() + 7) / 2))
        {
            leftPiece = board.getPiece(fx - 1, fy + c.getColor());
            if (leftPiece != null && leftPiece.getColor() != c)
            {
                Moves.add(new Move(fx, fy, fx - 1, fy + c.getColor(), MoveType.CAPTURE_AND_PROMOTE_TO_BISHOP, leftPiece, c));
                Moves.add(new Move(fx, fy, fx - 1, fy + c.getColor(), MoveType.CAPTURE_AND_PROMOTE_TO_KNIGHT, leftPiece, c));
                Moves.add(new Move(fx, fy, fx - 1, fy + c.getColor(), MoveType.CAPTURE_AND_PROMOTE_TO_QUEEN, leftPiece, c));
                Moves.add(new Move(fx, fy, fx - 1, fy + c.getColor(), MoveType.CAPTURE_AND_PROMOTE_TO_ROOK, leftPiece, c));
            }
        }

        // Promotion via diagonal capturing to the right
        if ((fx < 7) && (fy == (5 * c.getColor() + 7) / 2))
        {
            rightPiece = board.getPiece(fx + 1, fy + c.getColor());
            if (rightPiece != null && rightPiece.getColor() != c)
            {
                Moves.add(new Move(fx, fy, fx + 1, fy + c.getColor(), MoveType.CAPTURE_AND_PROMOTE_TO_BISHOP, rightPiece, c));
                Moves.add(new Move(fx, fy, fx + 1, fy + c.getColor(), MoveType.CAPTURE_AND_PROMOTE_TO_KNIGHT, rightPiece, c));
                Moves.add(new Move(fx, fy, fx + 1, fy + c.getColor(), MoveType.CAPTURE_AND_PROMOTE_TO_QUEEN, rightPiece, c));
                Moves.add(new Move(fx, fy, fx + 1, fy + c.getColor(), MoveType.CAPTURE_AND_PROMOTE_TO_ROOK, rightPiece, c));
            }

        }

        // En passant capture
       
        Move lastMove = board.getLastMove();
        if (lastMove != null)
        {
            Piece lastMovePiece = board.getPiece(lastMove.getTo());

            if (lastMovePiece.getPieceType() == PieceType.PAWN && 
                (Math.abs(lastMove.getFrom().getY() - lastMove.getTo().getY()) == 2)) 
            {                                
                if (fx > 0)
                {
                    Location to = new Location(fx - 1, fy + c.getColor());
                    // The piece stands to the left
                    if ((lastMove.getTo().getX() == fx - 1) && (lastMove.getTo().getY() == fy))
                    {
                        Moves.add(new Move(from, to, MoveType.CAPTURE_ENPASSANT, lastMovePiece, c));
                    }
                }

                if (fx < 7)
                {
                    Location to = new Location(fx + 1, fy + c.getColor());
                    // The piece stands to the right
                    if ((lastMove.getTo().getX() == fx + 1) && (lastMove.getTo().getY() == fy))
                    {
                        Moves.add(new Move(from, to, MoveType.CAPTURE_ENPASSANT, lastMovePiece, c));

                    }
                }
            }
        }

         

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

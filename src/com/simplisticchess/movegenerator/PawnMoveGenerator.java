/**
 *
 * @author Morten Kühnrich
 */

package com.simplisticchess.movegenerator;

import com.simplisticchess.board.Board;
import com.simplisticchess.move.Move;
import com.simplisticchess.move.MoveType;
import com.simplisticchess.piece.Color;
import com.simplisticchess.piece.Piece;
import com.simplisticchess.piece.PieceType;
import java.util.ArrayList;
import java.util.Iterator;

public class PawnMoveGenerator
{
    public ArrayList<Move> generateMoves(Board b, Piece p)
    {

        final Color c = b.inMove();
        final int fx = p.getxPos();
        final int fy = p.getyPos();

        Piece leftPiece;
        Piece rightPiece;

        final ArrayList<Move> Moves = new ArrayList<Move>();

        // Normal one step forward pawn move
        if (((fy < 6) && (c == Color.WHITE))
                || (fy > 1) && (c == Color.BLACK))
        {
            if (b.freeSquare(fx, fy + c.getColor() * 1))
            {
                Moves.add(new Move(fx, fy, fx, fy + c.getColor() * 1, MoveType.NORMALMOVE, null, c));
            }
        }

        // Normal two step forward pawn move
        if (((fy == 1) && (c == Color.WHITE))
                || ((fy == 6) && (c == Color.BLACK)))
        {
            if (b.freeSquare(fx, fy + c.getColor() * 1) && b.freeSquare(fx, fy + c.getColor() * 2))
            {
                Moves.add(new Move(fx, fy, fx, (fy + c.getColor() * 2), MoveType.NORMALMOVE, null, c));
            }
        }

        // Non capturing PAWN promotion
        if (((fy == 6) && (c == Color.WHITE) && b.freeSquare(fx, 7))
                || ((fy == 1) && (c == Color.BLACK) && b.freeSquare(fx, 0)))
        {
            Moves.add(new Move(fx, fy, fx, fy + c.getColor(), MoveType.PROMOTE_TO_QUEEN, null, c));
            Moves.add(new Move(fx, fy, fx, fy + c.getColor(), MoveType.PROMOTE_TO_ROOK, null, c));
            Moves.add(new Move(fx, fy, fx, fy + c.getColor(), MoveType.PROMOTE_TO_KNIGHT, null, c));
            Moves.add(new Move(fx, fy, fx, fy + c.getColor(), MoveType.PROMOTE_TO_BISHOP, null, c));
        }

        // Normal diagonal capturing to the left
        if ((fx > 0) && (fy != (5 * c.getColor() + 7) / 2))
        {
            leftPiece = b.getPiece(fx - 1, fy + c.getColor());
            if (leftPiece != null && leftPiece.getColor() != c)
            {
                Moves.add(new Move(fx, fy, fx - 1, fy + c.getColor(), MoveType.CAPTURE, leftPiece, c));
            }
        }

        // Normal diagonal capturing to the right
        if ((fx < 7) && (fy != (5 * c.getColor() + 7) / 2))
        {
            rightPiece = b.getPiece(fx + 1, fy + c.getColor());
            if (rightPiece != null && rightPiece.getColor() != c)
            {
                Moves.add(new Move(fx, fy, fx + 1, fy + c.getColor(), MoveType.CAPTURE, rightPiece, c));
            }
        }

        // Promotion via diagonal capturing to the left
        if ((fx > 0) && (fy == (5 * c.getColor() + 7) / 2))
        {
            leftPiece = b.getPiece(fx - 1, fy + c.getColor());
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
            rightPiece = b.getPiece(fx + 1, fy + c.getColor());
            if (rightPiece != null && rightPiece.getColor() != c)
            {
                Moves.add(new Move(fx, fy, fx + 1, fy + c.getColor(), MoveType.CAPTURE_AND_PROMOTE_TO_BISHOP, rightPiece, c));
                Moves.add(new Move(fx, fy, fx + 1, fy + c.getColor(), MoveType.CAPTURE_AND_PROMOTE_TO_KNIGHT, rightPiece, c));
                Moves.add(new Move(fx, fy, fx + 1, fy + c.getColor(), MoveType.CAPTURE_AND_PROMOTE_TO_QUEEN, rightPiece, c));
                Moves.add(new Move(fx, fy, fx + 1, fy + c.getColor(), MoveType.CAPTURE_AND_PROMOTE_TO_ROOK, rightPiece, c));
            }

        }

        // En passant capture
        try
        {
            Move lastMove = b.getLastMove();
            if (lastMove != null)
            {
                Piece lastMovePiece = b.getPiece(lastMove.getTo());
                
                if (lastMovePiece.getPieceType() == PieceType.PAWN && 
                    (Math.abs(lastMove.getFrom().getY() - lastMove.getTo().getY()) == 2)) 
                {                
                    if (fx > 0)
                    {
                        // The piece stands to the left
                        if ((lastMove.getTo().getX() == fx - 1) && (lastMove.getTo().getY() == fy))
                        {
                            Moves.add(new Move(fx, fy, fx - 1, fy + c.getColor(), 
                                    MoveType.CAPTURE_ENPASSANT, lastMovePiece, c));
                        }
                    }

                    if (fx < 7)
                    {
                        // The piece stands to the right
                        if ((lastMove.getTo().getX() == fx + 1) && (lastMove.getTo().getY() == fy))
                        {
                            Moves.add(new Move(fx, fy, fx + 1, fy + c.getColor(), 
                                    MoveType.CAPTURE_ENPASSANT, lastMovePiece, c));

                        }
                    }
                }
            }

        } catch (java.util.EmptyStackException e)
        {
            // There were no last move. We must be a beginning of game.
            // Hence no en passant moves are possible
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

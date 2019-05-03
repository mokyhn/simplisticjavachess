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

public class PawnMoveGenerator implements IMoveGenerator
{
    // TODO: The following can be refined so that not all moves are generated at once
    // Split into different iterators for the blocks inside the method
    private ArrayList<Move> generateMovesHelper(Board board, Piece piece)
    {

        final Color c = board.inMove();
        final int fx = piece.getxPos();
        final int fy = piece.getyPos();

        Location from = piece.getLocation();
        
        Piece leftPiece;
        Piece rightPiece;

        final ArrayList<Move> Moves = new ArrayList<>();

        // Normal one step forward pawn move
        {
            Location to = board.getMoveDirection().translocate(piece.getLocation());
            if (to.isValid())
            {
                if (board.freeSquare(to))
                {
                    Moves.add(new Move(from, to, MoveType.NORMALMOVE, null, c));
                }
            }
        }

        // Normal two step forward pawn move
        if ((c == Color.WHITE && fy == 1) || (c == Color.BLACK && fy == 6))
        {
            Location oneAhead = new Location(fx, fy + c.getColor());
            Location to = new Location(fx, fy + c.getColor() * 2);
            if (board.freeSquare(oneAhead) && board.freeSquare(to))
            {
                Moves.add(new Move(from, to, MoveType.NORMALMOVE, null, c));
            }
        }

        // Non capturing PAWN promotion
        if ((c == Color.WHITE && fy == 6 && board.freeSquare(fx, 7)) ||
            (c == Color.BLACK && fy == 1 && board.freeSquare(fx, 0)))
        {
            Location to = new Location(fx, fy + c.getColor());
            Moves.add(new Move(from, to, MoveType.PROMOTE_TO_QUEEN,  null, c));
            Moves.add(new Move(from, to, MoveType.PROMOTE_TO_ROOK,   null, c));
            Moves.add(new Move(from, to, MoveType.PROMOTE_TO_KNIGHT, null, c));
            Moves.add(new Move(from, to, MoveType.PROMOTE_TO_BISHOP, null, c));
        }

        if (fy == (5 * c.getColor() + 7) / 2)
        {
            // Promotion via diagonal capturing to the left
            if (fx > 0)
            {
                Location to = new Location(fx - 1, fy + c.getColor());
                leftPiece = board.getPiece(to);
                if (leftPiece != null && leftPiece.getColor() != c)
                {
                    Moves.add(new Move(from, to, MoveType.CAPTURE_AND_PROMOTE_TO_BISHOP, leftPiece, c));
                    Moves.add(new Move(from, to, MoveType.CAPTURE_AND_PROMOTE_TO_KNIGHT, leftPiece, c));
                    Moves.add(new Move(from, to, MoveType.CAPTURE_AND_PROMOTE_TO_QUEEN,  leftPiece, c));
                    Moves.add(new Move(from, to, MoveType.CAPTURE_AND_PROMOTE_TO_ROOK,   leftPiece, c));
                }
            }

            // Promotion via diagonal capturing to the right
            if (fx < 7)
            {
                Location to = new Location(fx + 1, fy + c.getColor());
                rightPiece = board.getPiece(to);
                if (rightPiece != null && rightPiece.getColor() != c)
                {
                    Moves.add(new Move(from, to, MoveType.CAPTURE_AND_PROMOTE_TO_BISHOP, rightPiece, c));
                    Moves.add(new Move(from, to, MoveType.CAPTURE_AND_PROMOTE_TO_KNIGHT, rightPiece, c));
                    Moves.add(new Move(from, to, MoveType.CAPTURE_AND_PROMOTE_TO_QUEEN,  rightPiece, c));
                    Moves.add(new Move(from, to, MoveType.CAPTURE_AND_PROMOTE_TO_ROOK,   rightPiece, c));
                }
            }
        }
        else
        {
            // Normal diagonal capturing to the left
            if (fx > 0)
            {
                Location to = new Location(fx - 1, fy + c.getColor());
                leftPiece = board.getPiece(to);
                if (leftPiece != null && leftPiece.getColor() != c)
                {                
                    Moves.add(new Move(from, to, MoveType.CAPTURE, leftPiece, c));
                }
            }

            // Normal diagonal capturing to the right
            if (fx < 7)
            {
                Location to = new Location(fx + 1, fy + c.getColor());
                rightPiece = board.getPiece(to);
                if (rightPiece != null && rightPiece.getColor() != c)
                {
                    Moves.add(new Move(from, to, MoveType.CAPTURE, rightPiece, c));
                }
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

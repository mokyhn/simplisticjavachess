/**
 *
 * @author Morten Kühnrich
 */

package com.simplisticjavachess.movegenerator;

import com.simplisticjavachess.position.Position;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.move.MoveType;
import com.simplisticjavachess.piece.Color;
import com.simplisticjavachess.piece.Piece;
import com.simplisticjavachess.piece.PieceType;
import com.simplisticjavachess.position.Location;

import java.util.*;

public class PawnMoveGenerator implements PieceMoveGenerator
{

    @Override
    public PieceType getPieceType() {
        return PieceType.PAWN;
    }


    // TODO: The following can be refined so that not all moves are generated at once
    // Split into different iterators for the blocks inside the method
    // See move generation for King

    private ArrayList<Move> generateMovesHelper(Position position, Piece piece)
    {

        final Color c = position.inMove();
        final int fx = piece.getX();
        final int fy = piece.getY();

        Location from = piece.getLocation();
        
        Piece leftPiece;
        Piece rightPiece;

        final ArrayList<Move> Moves = new ArrayList<>();

        if (c != piece.getColor() || piece.getPieceType() != PieceType.PAWN)
        {
            throw new IllegalArgumentException("Trying to generate moves for opponent piece which is not in move.");
        }

        Location oneAhead = position.getMoveDirection().translocate(piece.getLocation());
        Location twoAhead = position.getMoveDirection().scale(2).translocate(piece.getLocation());

        // Normal one step forward pawn move
        if (!pawnAtPromotionRank(piece))
        {
            Location to = position.getMoveDirection().translocate(piece.getLocation());
            if (to.isValid())
            {
                if (position.freeSquare(to))
                {
                    Moves.add(new Move(from, to, MoveType.NORMALMOVE, null, c));
                }
            }
        }

        // Normal two step forward pawn move
        if (pawnAtHomeRank(piece))
        {
            if (position.freeSquare(oneAhead) && position.freeSquare(twoAhead))
            {
                Moves.add(new Move(from, twoAhead, MoveType.NORMALMOVE, null, c));
            }
        }

        // Non capturing PAWN promotion
        if ((c == Color.WHITE && fy == 6 && position.freeSquare(fx, 7)) ||
            (c == Color.BLACK && fy == 1 && position.freeSquare(fx, 0)))
        {
            Moves.add(new Move(from, oneAhead, MoveType.PROMOTE_TO_QUEEN,  null, c));
            Moves.add(new Move(from, oneAhead, MoveType.PROMOTE_TO_ROOK,   null, c));
            Moves.add(new Move(from, oneAhead, MoveType.PROMOTE_TO_KNIGHT, null, c));
            Moves.add(new Move(from, oneAhead, MoveType.PROMOTE_TO_BISHOP, null, c));
        }

        if (fy == (5 * c.getColor() + 7) / 2)
        {
            // Promotion via diagonal capturing to the left
            if (fx > 0)
            {
                Location to = new Location(fx - 1, fy + c.getColor());
                leftPiece = position.getPiece(to);
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
                rightPiece = position.getPiece(to);
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
                leftPiece = position.getPiece(to);
                if (leftPiece != null && leftPiece.getColor() != c)
                {                
                    Moves.add(new Move(from, to, MoveType.CAPTURE, leftPiece, c));
                }
            }

            // Normal diagonal capturing to the right
            if (fx < 7)
            {
                Location to = new Location(fx + 1, fy + c.getColor());
                rightPiece = position.getPiece(to);
                if (rightPiece != null && rightPiece.getColor() != c)
                {
                    Moves.add(new Move(from, to, MoveType.CAPTURE, rightPiece, c));
                }
            }
        }

        // En passant capture       
        Optional<Move> lastMoveOptional = position.getEnpassantMove();
        if (lastMoveOptional.isPresent())
        {
            Move lastMove = lastMoveOptional.get();
            Piece lastMovePiece = position.getPiece(lastMove.getTo());

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


    private boolean pawnAtHomeRank(Piece piece)
    {
        return ((piece.getColor() == Color.WHITE && piece.getY() == 1) ||
                (piece.getColor() == Color.BLACK && piece.getY() == 6));
    }

    private boolean pawnAtPromotionRank(Piece piece)
    {
        return ((piece.getColor() == Color.WHITE && piece.getY() == 6) ||
                (piece.getColor() == Color.BLACK && piece.getY() == 1));
    }

    @Override
    public Iterator<Move> generateMoves(Position position, Piece p)
    {
        return new Iterator<Move>()
        {
            Iterator<Move> generated = null;

            @Override
            public boolean hasNext()
            {
                if (generated == null)
                {
                    generated = generateMovesHelper(position, p).iterator();
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

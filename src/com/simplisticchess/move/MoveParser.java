package com.simplisticchess.move;

import com.simplisticchess.piece.Piece;
import com.simplisticchess.board.Board;
import com.simplisticchess.piece.Color;
import com.simplisticchess.piece.PieceType;
import com.simplisticchess.position.Location;

public final class MoveParser
{

    /**
     *
     * @param b - Current Board position
     * @param str - move string
     * @return a Move
     */
    public static Move parseMove(Board b, String str)
    {
        if (str == null)
        {
            return null;
        }

        if (str.equalsIgnoreCase("o-o") && b.inMove() == Color.WHITE)
        {
            str = "e1g1";
        }
        if (str.equalsIgnoreCase("o-o-o") && b.inMove() == Color.WHITE)
        {
            str = "e1c1";
        }
        if (str.equalsIgnoreCase("o-o") && b.inMove() == Color.BLACK)
        {
            str = "e8g8";
        }
        if (str.equalsIgnoreCase("o-o-o") && b.inMove() == Color.BLACK)
        {
            str = "e8c8";
        }

        if (str.length() < 4 || str.length() > 5)
        {
            return null;
        }

        try
        {
            Location from = new Location(str.substring(0, 2));
            Location to = new Location(str.substring(2, 4));

            Piece p;
            p = b.getPiece(from);
            if (p == null)
            {
                return null;
            }

            Color whoToMove = b.inMove();

            if (p.getColor() != whoToMove)
            {
                return null;
            }

            if (str.length() == 4)
            {
                // White or black does a short or a long castling
                if (p.getPieceType() == PieceType.KING && from.rankEquals(to) && (from.getY() == 0 || from.getY() == 7))
                {
                    if (from.getX() == 4 && to.getX() == 6)
                    {
                        return new Move(from, to, MoveType.CASTLE_SHORT, null, whoToMove);
                    } else if (from.getX() == 4 && to.getX() == 2)
                    {
                        return new Move(from, to, MoveType.CASTLE_LONG, null, whoToMove);
                    }
                }

                // ENPASSENT Move
                if (p.getPieceType() == PieceType.PAWN)
                {
                    if (from.fileDifferent(to) && (b.freeSquare(to)))
                    {
                        return new Move(from, to, MoveType.CAPTURE_ENPASSANT, PieceType.PAWN, whoToMove);
                    }
                }

                // Normal move
                if (b.freeSquare(to))
                {
                    return new Move(from, to, MoveType.NORMALMOVE, null, whoToMove);
                }

                // A capturing move
                Piece pto;
                pto = b.getPiece(to);
                if (pto != null && pto.getColor() == whoToMove.flip())
                {
                    return new Move(from, to, MoveType.CAPTURE, pto.getPieceType(), whoToMove);
                }
            }

            // Promotion moves
            if (str.length() == 5
                    && p.getPieceType() == PieceType.PAWN
                    && ((p.getColor() == Color.WHITE && from.getY() == 6)
                    || (p.getColor() == Color.BLACK && from.getY() == 1)))
            {

                MoveType moveType;

                // Simple promotions
                if (from.fileEquals(to) && b.freeSquare(to))
                {
                    switch (str.charAt(4))
                    {
                        case 'N':
                            moveType = MoveType.PROMOTE_TO_KNIGHT;
                            break;
                        case 'K':
                            moveType = MoveType.PROMOTE_TO_KNIGHT;
                            break;
                        case 'B':
                            moveType = MoveType.PROMOTE_TO_BISHOP;
                            break;
                        case 'Q':
                            moveType = MoveType.PROMOTE_TO_QUEEN;
                            break;
                        case 'R':
                            moveType = MoveType.PROMOTE_TO_ROOK;
                            break;
                        default:
                            return null;
                    }
                    return new Move(from, to, moveType, null, whoToMove);
                }

                // Capture and promote
                if (from.fileDifferent(to)
                        && !b.freeSquare(to)
                        && b.getPiece(to).getColor() == p.getColor().flip())
                {
                    switch (str.charAt(4))
                    {
                        case 'K':
                            moveType = MoveType.CAPTURE_AND_PROMOTE_TO_KNIGHT;
                            break;
                        case 'B':
                            moveType = MoveType.CAPTURE_AND_PROMOTE_TO_BISHOP;
                            break;
                        case 'Q':
                            moveType = MoveType.CAPTURE_AND_PROMOTE_TO_QUEEN;
                            break;
                        case 'R':
                            moveType = MoveType.CAPTURE_AND_PROMOTE_TO_ROOK;
                            break;
                        default:
                            return null;
                    }

                    return new Move(from, to, moveType, b.getPiece(to).getPieceType(), whoToMove);
                }
            }

        } catch (Exception e)
        {
            return null;
        }

        return null;
    }

}

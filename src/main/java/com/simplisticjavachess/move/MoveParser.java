package com.simplisticjavachess.move;

import com.simplisticjavachess.board.Position;
import com.simplisticjavachess.piece.Piece;
import com.simplisticjavachess.piece.Color;
import com.simplisticjavachess.piece.PieceType;
import com.simplisticjavachess.board.Location;

public final class MoveParser
{

    public static Move parse(Position position, String str)
    {
        str = str.trim().toLowerCase();
        str = preprocessorCastlingSyntax(str, position.inMove());

        Location from = Location.parse(str.substring(0, 2));
        Location to = Location.parse(str.substring(2, 4));

        if (str.length() < 4 || str.length() > 5)
        {
            return null;
        }


        Piece p;
        p = position.getPiece(from);
        if (p == null)
        {
            return null;
        }

        Color whoToMove = position.inMove();

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
                if (from.fileDifferent(to) && (position.freeSquare(to)))
                {
                    return new Move(from, to, MoveType.CAPTURE_ENPASSANT, 
                            position.getPiece(new Location(to.getX(), from.getY())), whoToMove);
                }
            }

            // Normal move
            if (position.freeSquare(to))
            {
                return new Move(from, to, MoveType.NORMALMOVE, null, whoToMove);
            }

            // A capturing move
            Piece pto = position.getPiece(to);
            if (pto != null && pto.getColor() == whoToMove.opponent())
            {
                return new Move(from, to, MoveType.CAPTURE, pto, whoToMove);
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
            if (from.fileEquals(to) && position.freeSquare(to))
            {
                switch (str.charAt(4))
                {
                    case 'n':
                    case 'k':
                        moveType = MoveType.PROMOTE_TO_KNIGHT;
                        break;
                    case 'b':
                        moveType = MoveType.PROMOTE_TO_BISHOP;
                        break;
                    case 'q':
                        moveType = MoveType.PROMOTE_TO_QUEEN;
                        break;
                    case 'r':
                        moveType = MoveType.PROMOTE_TO_ROOK;
                        break;
                    default:
                        return null;
                }
                return new Move(from, to, moveType, null, whoToMove);
            }

            //TODO: This code must be simplifiable

            // Capture and promote
            if (from.fileDifferent(to)
                    && !position.freeSquare(to)
                    && position.getPiece(to).getColor() == p.getColor().opponent())
            {
                switch (str.charAt(4))
                {
                    case 'n':
                    case 'k':
                        moveType = MoveType.CAPTURE_AND_PROMOTE_TO_KNIGHT;
                        break;
                    case 'b':
                        moveType = MoveType.CAPTURE_AND_PROMOTE_TO_BISHOP;
                        break;
                    case 'q':
                        moveType = MoveType.CAPTURE_AND_PROMOTE_TO_QUEEN;
                        break;
                    case 'r':
                        moveType = MoveType.CAPTURE_AND_PROMOTE_TO_ROOK;
                        break;
                    default:
                        return null;
                }

                return new Move(from, to, moveType, position.getPiece(to), whoToMove);
            }
        }

        return null;
    }

    private static String preprocessorCastlingSyntax(String str, Color color)
    {
        if (str.equals("o-o"))
        {
            return color == Color.WHITE ? "e1g1" : "e8g8";
        }
        else if (str.equals("o-o-o"))
        {
            return color == Color.WHITE ? "e1c1" : "e8c8";
        }
        return str;
    }

}

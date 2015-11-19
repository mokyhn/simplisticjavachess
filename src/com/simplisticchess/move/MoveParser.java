package com.simplisticchess.move;

import com.simplisticchess.piece.Piece;
import com.simplisticchess.board.Board;
import com.simplisticchess.piece.Color;
import com.simplisticchess.piece.PieceType;

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

        char[] s = str.toCharArray();
        int fromX, fromY, toX, toY;

        fromX = (int) (s[0] - 'a');
        fromY = (int) ((s[1] - '0') - 1);
        toX = (int) (s[2] - 'a');
        toY = (int) ((s[3] - '0') - 1);

        if (fromX < 0 || fromX > 7
                || toX < 0 || toX > 7
                || fromY < 0 || fromY > 7
                || toY < 0 || toY > 7)
        {
            return null;
        }

        Piece p;
        p = b.getPiece(fromX, fromY);
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
            if (p.getPieceType() == PieceType.KING && fromY == toY && (fromY == 0 || fromY == 7))
            {
                if (fromX == 4 && toX == 6)
                {
                    return new Move(fromX, fromY, toX, toY, MoveType.CASTLE_SHORT, null, whoToMove);
                } else if (fromX == 4 && toX == 2)
                {
                    return new Move(fromX, fromY, toX, toY, MoveType.CASTLE_LONG, null, whoToMove);
                }
            }

            // ENPASSENT Move
            if (p.getPieceType() == PieceType.PAWN)
            {
                if ((fromX != toX) && (b.freeSquare(toX, toY)))
                {
                    return new Move(fromX, fromY, toX, toY, MoveType.CAPTURE_ENPASSANT, PieceType.PAWN, whoToMove);
                }
            }

            // Normal move
            if (b.freeSquare(toX, toY))
            {
                return new Move(fromX, fromY, toX, toY, MoveType.NORMALMOVE, null, whoToMove);
            }

            // A capturing move
            Piece pto;
            pto = b.getPiece(toX, toY);
            if (pto != null && pto.getColor() == whoToMove.flip())
            {
                return new Move(fromX, fromY, toX, toY, MoveType.CAPTURE, pto.getPieceType(), whoToMove);
            }
        }

        // Promotion moves
        if (str.length() == 5
                && p.getPieceType() == PieceType.PAWN
                && ((p.getColor() == Color.WHITE && fromY == 6)
                || (p.getColor() == Color.BLACK && fromY == 1)))
        {

            MoveType moveType;

            // Simple promotions
            if (fromX == toX && b.freeSquare(toX, toY))
            {
                switch (s[4])
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
                return new Move(fromX, fromY, toX, toY, moveType, null, whoToMove);
            }

            // Capture and promote
            if (fromX != toX
                    && !b.freeSquare(toX, toY)
                    && b.getPiece(toX, toY).getColor() == p.getColor().flip())
            {
                switch (s[4])
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

                return new Move(fromX, fromY, toX, toY, moveType, b.getPiece(toX, toY).getPieceType(), whoToMove);
            }
        }

        return null;
    }

}

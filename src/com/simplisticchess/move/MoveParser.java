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

        if (p.color != whoToMove)
        {
            return null;
        }

        Move m = new Move();
        m.fromX = fromX;
        m.fromY = fromY;
        m.toX = toX;
        m.toY = toY;
        m.capturedPiece = null;
        m.whoMoves = whoToMove;

        if (str.length() == 4)
        {
            // White or black does a short or a long castling
            if (p.pieceType == PieceType.KING && fromY == toY && (fromY == 0 || fromY == 7))
            {
                if (fromX == 4 && toX == 6)
                {
                    m.moveType = MoveType.CASTLE_SHORT;
                    return m;
                } else if (fromX == 4 && toX == 2)
                {
                    m.moveType = MoveType.CASTLE_LONG;
                    return m;
                }
            }

            // ENPASSENT Move
            if (p.pieceType == PieceType.PAWN)
            {
                if ((fromX != toX) && (b.freeSquare(toX, toY)))
                {
                    m.moveType = MoveType.CAPTURE_ENPASSANT;
                    m.capturedPiece = PieceType.PAWN;
                    return m;
                }
            }

            // Normal move
            if (b.freeSquare(toX, toY))
            {
                m.moveType = MoveType.NORMALMOVE;
                return m;
            }

            // A capturing move
            Piece pto;
            pto = b.getPiece(toX, toY);
            if (pto != null && pto.color == whoToMove.flip())
            {
                m.moveType = MoveType.CAPTURE;
                m.capturedPiece = pto.pieceType;
                return m;
            }
        }

        // Promotion moves
        if (str.length() == 5
                && p.pieceType == PieceType.PAWN
                && ((p.color == Color.WHITE && fromY == 6)
                || (p.color == Color.BLACK && fromY == 1)))
        {

            // Simple promotions
            if (fromX == toX && b.freeSquare(toX, toY))
            {
                switch (s[4])
                {
                    case 'N':
                        m.moveType = MoveType.PROMOTE_TO_KNIGHT;
                        break;
                    case 'K':
                        m.moveType = MoveType.PROMOTE_TO_KNIGHT;
                        break;
                    case 'B':
                        m.moveType = MoveType.PROMOTE_TO_BISHOP;
                        break;
                    case 'Q':
                        m.moveType = MoveType.PROMOTE_TO_QUEEN;
                        break;
                    case 'R':
                        m.moveType = MoveType.PROMOTE_TO_ROOK;
                        break;
                }
                return m;
            }

            // Capture and promote
            if (fromX != toX
                    && !b.freeSquare(toX, toY)
                    && b.getPiece(toX, toY).color == p.color.flip())
            {
                switch (s[4])
                {
                    case 'K':
                        m.moveType = MoveType.CAPTURE_AND_PROMOTE_TO_KNIGHT;
                        break;
                    case 'B':
                        m.moveType = MoveType.CAPTURE_AND_PROMOTE_TO_BISHOP;
                        break;
                    case 'Q':
                        m.moveType = MoveType.CAPTURE_AND_PROMOTE_TO_QUEEN;
                        break;
                    case 'R':
                        m.moveType = MoveType.CAPTURE_AND_PROMOTE_TO_ROOK;
                        break;
                }

                m.capturedPiece = b.getPiece(toX, toY).pieceType;

                return m;
            }
        }

        return null;
    }

}

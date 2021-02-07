package com.simplisticjavachess.move;

import com.simplisticjavachess.position.Position;
import com.simplisticjavachess.piece.Piece;
import com.simplisticjavachess.piece.Color;
import com.simplisticjavachess.piece.PieceType;
import com.simplisticjavachess.position.Location;

public final class MoveParser {

    public static Move parse(Position position, String str) {
        str = str.trim().toLowerCase();
        str = preprocessorCastlingSyntax(str, position.inMove());

        Location from = Location.parse(str.substring(0, 2));
        Location to = Location.parse(str.substring(2, 4));

        if (str.length() < 4 || str.length() > 5) {
            throw new IllegalArgumentException("Move string must be of length 4 or 5");
        }


        Piece piece = position.getPiece(from);
        if (piece == null) {
            throw new IllegalArgumentException("Cannot not move a piece which is not there");
        }

        Color whoToMove = position.inMove();

        if (piece.getColor() != whoToMove) {
            throw new IllegalArgumentException("Tried to move an opponent piece");
        }

        if (str.length() == 4) {
            // White or black does a short or a long castling
            if (piece.getType() == PieceType.KING && from.rankEquals(to) && (from.getY() == 0 || from.getY() == 7)) {
                if (from.getX() == 4 && to.getX() == 6) {
                    return new Move(from, to, MoveType.CASTLE_SHORT, null, whoToMove);
                } else if (from.getX() == 4 && to.getX() == 2) {
                    return new Move(from, to, MoveType.CASTLE_LONG, null, whoToMove);
                }
            }

            // ENPASSENT Move
            if (piece.getType() == PieceType.PAWN) {
                if (from.fileDifferent(to) && (position.freeSquare(to))) {
                    return new Move(from, to, MoveType.CAPTURE_ENPASSANT,
                            position.getPiece(new Location(to.getX(), from.getY())), whoToMove);
                }
            }

            // Normal move
            if (position.freeSquare(to)) {
                return new Move(from, to, MoveType.NORMALMOVE, null, whoToMove);
            }

            // A capturing move
            Piece pto = position.getPiece(to);
            if (pto != null && pto.getColor() == whoToMove.opponent()) {
                return new Move(from, to, MoveType.CAPTURE, pto, whoToMove);
            }
        }

        // Promotion moves
        if (str.length() == 5
                && piece.getType() == PieceType.PAWN
                && ((piece.getColor() == Color.WHITE && from.getY() == 6)
                || (piece.getColor() == Color.BLACK && from.getY() == 1))) {

            MoveType moveType;

            // Simple promotions
            if (from.fileEquals(to) && position.freeSquare(to)) {
                switch (str.charAt(4)) {
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
                        throw new IllegalStateException("Unreachable switch branch.");
                }
                return new Move(from, to, moveType, null, whoToMove);
            }

            //TODO: This code must be simplifiable

            // Capture and promote
            if (from.fileDifferent(to)
                    && !position.freeSquare(to)
                    && position.getPiece(to).getColor() == piece.getColor().opponent()) {
                switch (str.charAt(4)) {
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
                        throw new IllegalStateException("Unreachable switch branch.");
                }

                return new Move(from, to, moveType, position.getPiece(to), whoToMove);
            }
        }

        throw new IllegalArgumentException("Illegal move.");
    }

    private static String preprocessorCastlingSyntax(String str, Color color) {
        if (str.equals("o-o")) {
            return color.isWhite() ? "e1g1" : "e8g8";
        } else if (str.equals("o-o-o")) {
            return color.isWhite() ? "e1c1" : "e8c8";
        }
        return str;
    }

}

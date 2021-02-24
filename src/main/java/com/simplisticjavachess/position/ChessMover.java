package com.simplisticjavachess.position;

import com.simplisticjavachess.move.MoveSequence;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.piece.Color;
import com.simplisticjavachess.piece.Piece;
import com.simplisticjavachess.piece.PieceType;

import java.util.Iterator;

public class ChessMover implements Mover {

    @Override
    public Position doMove(Position position, Move move) throws IllegalMoveException {
        Piece piece = position.getPiece(move.getFrom());

        Position newPosition = position.setMove(move).tickGameClock();

        if (move.getMoveType().isCapture() || piece.getType().isPawn()) {
            newPosition = newPosition.resetFiftyMoveDrawClock();
        } else {
            newPosition = newPosition.tickFiftyMoveDrawClock();
        }

        Color inMove = position.inMove();

        // Moving the king will disallow castling in the future
        if (PieceType.KING.equals(piece.getType())) {
            newPosition = newPosition.setCanCastleLong(false, move.getWhoMoves());
            newPosition = newPosition.setCanCastleShort(false, move.getWhoMoves());
        }

        // Moving a rook can disallow castling in the future
        if (PieceType.ROOK.equals(piece.getType())) {
            if (move.getFrom().getX() == 0) {
                newPosition = newPosition.setCanCastleLong(false, piece.getColor());
            } else if (move.getFrom().getX() == 7) {
                newPosition = newPosition.setCanCastleShort(false, piece.getColor());
            }
        }

        switch (move.getMoveType()) {
            case NORMALMOVE:
                newPosition = newPosition.move(piece, move.getTo());
                break;

            case CAPTURE:
                newPosition = newPosition.remove(move.getCapturedPiece());
                newPosition = newPosition.move(piece, move.getTo());
                if (PieceType.ROOK.equals(move.getCapturedPiece().getType())) {
                    if (move.getTo().getX() == 0) {
                        newPosition = newPosition.setCanCastleLong(false, move.getCapturedPiece().getColor());
                    } else if (move.getTo().getX() == 7) {
                        newPosition = newPosition.setCanCastleShort(false, move.getCapturedPiece().getColor());
                    }
                }
                break;

            case CASTLE_SHORT:
                if (!position.getCanCastleShort(inMove)) {
                    throw new IllegalMoveException();
                }
                newPosition = newPosition.move(position.getPiece(move.getFrom()), move.getTo());
                newPosition = newPosition.move(position.getPiece(new Location(7, move.getFrom().getY())),
                        new Location(5, move.getFrom().getY()));
                break;

            case CASTLE_LONG:
                if (!position.getCanCastleLong(inMove)) {
                    throw new IllegalMoveException();
                }
                newPosition = newPosition.move(position.getPiece(move.getFrom()), move.getTo());
                newPosition = newPosition.move(position.getPiece(new Location(0, move.getFrom().getY())),
                        new Location(3, move.getFrom().getY()));
                break;

            case CAPTURE_ENPASSANT:
                newPosition = newPosition.move(piece, move.getTo());
                newPosition = newPosition.remove(move.getCapturedPiece());
                break;

            case PROMOTE_TO_BISHOP:  /* Intended fallthrough */
            case PROMOTE_TO_KNIGHT:  /* Intended fallthrough */
            case PROMOTE_TO_ROOK:    /* Intended fallthrough */
            case PROMOTE_TO_QUEEN:   /* Intended fallthrough */
                newPosition = newPosition.insert(new Piece(move.getTo(), move.getWhoMoves(), move.promotionTo()));
                newPosition = newPosition.remove(piece);
                break;

            case CAPTURE_AND_PROMOTE_TO_BISHOP: /* Intended fallthrough */
            case CAPTURE_AND_PROMOTE_TO_KNIGHT: /* Intended fallthrough */
            case CAPTURE_AND_PROMOTE_TO_ROOK:   /* Intended fallthrough */
            case CAPTURE_AND_PROMOTE_TO_QUEEN:  /* Intended fallthrough */
                newPosition = newPosition.remove(position.getPiece(move.getTo()));
                newPosition = newPosition.remove(position.getPiece(move.getFrom()));
                newPosition = newPosition.insert(new Piece(move.getTo(), move.getWhoMoves(), move.promotionTo()));
        }

        // Swap the move color
        newPosition = newPosition.flipInMove();

        if (newPosition.isInCheck(position.inMove())) {
            throw new IllegalMoveException("Was in check");
        }

        return newPosition;
    }

    @Override
    public Position doMove(Position position, String moves) throws IllegalMoveException {
        MoveSequence moveSequence = MoveSequence.parse(position, moves);
        return doMove(position, moveSequence);
    }

    private Position doMove(Position position, MoveSequence moveSequence) throws IllegalMoveException {
        Iterator<Move> moves = moveSequence.iterator();

        while (moves.hasNext()) {
            position = doMove(position, moves.next());
        }

        return position;
    }


}

package com.simplisticjavachess.board;

import com.simplisticjavachess.engine.MoveSequence;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.piece.Color;
import com.simplisticjavachess.piece.Piece;
import com.simplisticjavachess.piece.PieceType;

import java.util.Iterator;

public class Mover
{

    public static MoveResult doMove(Position position, Move move)
    {
        Piece piece = position.getPiece(move.getFrom());

        Position newPosition = position.setMove(move);

        Color inMove = position.inMove();

        // Moving the king will disallow castling in the future
        if (PieceType.KING.equals(piece.getPieceType()))
        {
            newPosition = newPosition.setCanCastleLong(false, move.getWhoMoves());
            newPosition = newPosition.setCanCastleShort(false, move.getWhoMoves());
        }

        // Moving a rook can disallow castling in the future
        if (PieceType.ROOK.equals(piece.getPieceType()))
        {
            if (move.getFrom().getX() == 0)
            {
                newPosition = newPosition.setCanCastleLong(false, piece.getColor());
            }
            else if (move.getFrom().getX() == 7)
            {
                newPosition = newPosition.setCanCastleShort(false, piece.getColor());
            }
        }

        switch (move.getMoveType())
        {
            case NORMALMOVE:
                newPosition = newPosition.move(piece, move.getTo());
                break;

            case CAPTURE:
                newPosition = newPosition.remove(move.getCapturedPiece());
                newPosition = newPosition.move(piece, move.getTo());
                if (PieceType.ROOK.equals(move.getCapturedPiece().getPieceType()))
                {
                    if (move.getTo().getX() == 0)
                    {
                        newPosition = newPosition.setCanCastleLong(false, move.getCapturedPiece().getColor());
                    }
                    else if (move.getTo().getX() == 7)
                    {
                        newPosition = newPosition.setCanCastleShort(false, move.getCapturedPiece().getColor());
                    }
                }
                break;

            case CASTLE_SHORT:
                if (!position.getCanCastleShort(inMove))
                {
                    return new MoveResult(false, position);
                }
                // Move the king
                newPosition = newPosition.move(position.getPiece(move.getFrom()), move.getTo());
                newPosition = newPosition.move(position.getPiece(new Location(7, move.getFrom().getY())),
                        new Location(5, move.getFrom().getY()));
                break;

            case CASTLE_LONG:
                if (!position.getCanCastleLong(inMove))
                {
                    return new MoveResult(false, position);
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
        newPosition = newPosition.setInMove(position.inMove().opponent());

        boolean wasMoveLegal;

        // The player that did the move is in check
        // his or her move is hence not legal
        if (newPosition.isInCheck(position.inMove()))
        {
            wasMoveLegal = false;
        }
        else
        {
            //checkDrawBy50MoveRule();
            //checkDrawBy3RepetionsRule();
            wasMoveLegal = true;
        }

        return new MoveResult(wasMoveLegal, newPosition);
    }

    public static MoveResult doMove(Position position, String moves)
    {
        MoveSequence moveSequence = MoveSequence.parse(position, moves);
        return Mover.doMove(position, moveSequence);
    }

    private static MoveResult doMove(Position position, MoveSequence moveSequence)
    {
        MoveResult moveResult = null;
        Iterator<Move> moves = moveSequence.iterator();

        Position theboard = position;

        while (moves.hasNext())
        {
            moveResult = Mover.doMove(theboard, moves.next());
            theboard = moveResult.getPosition();
        }

        return moveResult;
    }



}

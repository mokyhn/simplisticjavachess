/**
 *
 * @author Morten Kühnrich
 */
package com.simplisticjavachess.movegenerator;

import com.simplisticjavachess.board.Board;
import com.simplisticjavachess.board.Location;
import com.simplisticjavachess.board.Vector;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.move.MoveType;
import com.simplisticjavachess.piece.Color;
import com.simplisticjavachess.piece.Piece;
import com.simplisticjavachess.piece.PieceType;

import java.util.Iterator;

import static com.simplisticjavachess.misc.IteratorUtils.compose;
import static com.simplisticjavachess.movegenerator.MoveGeneratorUtil.*;

public class KingMoveGenerator implements PieceMoveGenerator
{
    @Override
    public PieceType getPieceType() {
        return PieceType.KING;
    }

    @Override
    public Iterator<Move> generateMoves(Board b, Piece p)
    {
        return compose(
                castlingShort(b, p),
                castlingLong(b, p),
                oneMoveIterator(() -> genKingMove(b, p, Vector.LEFT)),
                oneMoveIterator(() -> genKingMove(b, p, Vector.RIGHT)),
                oneMoveIterator(() -> genKingMove(b, p, Vector.UP)),
                oneMoveIterator(() -> genKingMove(b, p, Vector.DOWN)),
                oneMoveIterator(() -> genKingMove(b, p, Vector.UP_AND_LEFT)),
                oneMoveIterator(() -> genKingMove(b, p, Vector.DOWN_AND_LEFT)),
                oneMoveIterator(() -> genKingMove(b, p, Vector.UP_AND_RIGHT)),
                oneMoveIterator(() -> genKingMove(b, p, Vector.DOWN_AND_RIGHT))
        );
    }


    private Iterator<Move> castlingShort(Board board, Piece piece) {
        return oneMoveIterator(() -> {
            final Color color = board.inMove();
            final int fy = piece.getY();

            Piece rook = board.getPiece(new Location(7, fy));

            // Ensure that there is a rook
            if (rook == null || rook.getPieceType() != PieceType.ROOK || rook.getColor() != color) {
                return null;
            }

            // Castling short
            if (board.canCastleShort() &&
                    board.freeSquare(5, fy) &&
                    board.freeSquare(6, fy) &&
                    !board.isAttacked(5, fy) &&
                    !board.isAttacked(6, fy) &&
                    !board.isInCheck(color))
            {
                return new Move(
                        piece.getLocation(),
                        Vector.RIGHT_RIGHT.translocate(piece.getLocation()),
                        MoveType.CASTLE_SHORT, null, color);
            }
            return null;
        });
    }

    private Iterator<Move> castlingLong(Board board, Piece piece)
    {
        return oneMoveIterator(() -> {
            final Color color = board.inMove();
            final int fy = piece.getY();

            Piece rook = board.getPiece(new Location(0, fy));

            // Ensure that there is a rook
            if (rook == null || rook.getPieceType() != PieceType.ROOK || rook.getColor() != color) {
                return null;
            }

            // Castling long
            if (board.canCastleLong() &&
                    board.freeSquare(3, fy) &&
                    board.freeSquare(2, fy) &&
                    board.freeSquare(1, fy) &&
                    !board.isAttacked(2, fy) &&
                    !board.isAttacked(3, fy) &&
                    !board.isInCheck(color))
            {
                return new Move(
                        piece.getLocation(),
                        Vector.LEFT_LEFT.translocate(piece.getLocation()),
                        MoveType.CASTLE_LONG, null, color);
            }
            return null;
        });
    }


}

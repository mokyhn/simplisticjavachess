/**
 *
 * @author Morten Kühnrich
 */
package com.simplisticjavachess.movegenerator;

import com.simplisticjavachess.position.Location;
import com.simplisticjavachess.position.Position;
import com.simplisticjavachess.position.Vector;
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
    public Iterator<Move> generateMoves(Position position, Piece p)
    {
        return compose(
                castlingShort(position, p),
                castlingLong(position, p),
                oneMoveIterator(() -> genKingMove(position, p, Vector.LEFT)),
                oneMoveIterator(() -> genKingMove(position, p, Vector.RIGHT)),
                oneMoveIterator(() -> genKingMove(position, p, Vector.UP)),
                oneMoveIterator(() -> genKingMove(position, p, Vector.DOWN)),
                oneMoveIterator(() -> genKingMove(position, p, Vector.UP_AND_LEFT)),
                oneMoveIterator(() -> genKingMove(position, p, Vector.DOWN_AND_LEFT)),
                oneMoveIterator(() -> genKingMove(position, p, Vector.UP_AND_RIGHT)),
                oneMoveIterator(() -> genKingMove(position, p, Vector.DOWN_AND_RIGHT))
        );
    }


    private Iterator<Move> castlingShort(Position position, Piece piece) {
        return oneMoveIterator(() -> {
            final Color color = position.inMove();
            final int fy = piece.getY();

            Piece rook = position.getPiece(new Location(7, fy));

            // Ensure that there is a rook
            if (rook == null || rook.getPieceType() != PieceType.ROOK || rook.getColor() != color) {
                return null;
            }

            // Castling short
            if (position.canCastleShort() &&
                    position.freeSquare(5, fy) &&
                    position.freeSquare(6, fy) &&
                    !position.isAttacked(5, fy) &&
                    !position.isAttacked(6, fy) &&
                    !position.isInCheck(color))
            {
                return new Move(
                        piece.getLocation(),
                        Vector.RIGHT_RIGHT.translocate(piece.getLocation()),
                        MoveType.CASTLE_SHORT, null, color);
            }
            return null;
        });
    }

    private Iterator<Move> castlingLong(Position position, Piece piece)
    {
        return oneMoveIterator(() -> {
            final Color color = position.inMove();
            final int fy = piece.getY();

            Piece rook = position.getPiece(new Location(0, fy));

            // Ensure that there is a rook
            if (rook == null || rook.getPieceType() != PieceType.ROOK || rook.getColor() != color) {
                return null;
            }

            // Castling long
            if (position.canCastleLong() &&
                    position.freeSquare(3, fy) &&
                    position.freeSquare(2, fy) &&
                    position.freeSquare(1, fy) &&
                    !position.isAttacked(2, fy) &&
                    !position.isAttacked(3, fy) &&
                    !position.isInCheck(color))
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

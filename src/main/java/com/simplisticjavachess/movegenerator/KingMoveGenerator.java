/**
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

public class KingMoveGenerator implements PieceMoveGenerator {
    @Override
    public PieceType getPieceType() {
        return PieceType.KING;
    }

    @Override
    public Iterator<Move> generateMoves(Position position, Piece p) {
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
            if (!position.canCastleShort()) {
                return null;
            }

            final Color toMove = position.inMove();

            if (!piece.getType().isKing() || piece.getX() != 4 || piece.getColor() != toMove) {
                return null;
            }

            final int fy = piece.getY();
            Piece rook = position.getPiece(new Location(7, fy));

            if (rook == null || rook.getType() != PieceType.ROOK || rook.getColor() != toMove) {
                return null;
            }

            Location fSquare = new Location(5, fy);
            Location gSquare = new Location(6, fy);
            if (position.freeSquare(fSquare) &&
               !position.isAttacked(fSquare) &&
                position.freeSquare(gSquare) &&
               !position.isAttacked(gSquare) &&
               !position.isInCheck()) {
                return new Move(
                        piece.getLocation(),
                        Vector.RIGHT_RIGHT.translate(piece.getLocation()),
                        MoveType.CASTLE_SHORT, null, toMove);
            }


            return null;

        });
    }

    private Iterator<Move> castlingLong(Position position, Piece piece) {
        return oneMoveIterator(() -> {
            if (!position.canCastleLong()) {
                return null;
            }

            final Color toMove = position.inMove();

            if (!piece.getType().isKing() || piece.getX() != 4 || piece.getColor() != toMove) {
                return null;
            }

            final int fy = piece.getY();

            Piece rook = position.getPiece(new Location(0, fy));

            if (rook == null || rook.getType() != PieceType.ROOK || rook.getColor() != toMove) {
                return null;
            }

            Location bSquare = new Location(1, fy);
            Location cSquare = new Location(2, fy);
            Location dSquare = new Location(3, fy);
            if (position.freeSquare(dSquare) &&
               !position.isAttacked(dSquare) &&
                position.freeSquare(cSquare) &&
               !position.isAttacked(cSquare) &&
                position.freeSquare(bSquare) &&
                !position.isInCheck()) {
                return new Move(
                        piece.getLocation(),
                        Vector.LEFT_LEFT.translate(piece.getLocation()),
                        MoveType.CASTLE_LONG, null, toMove);
            }
            return null;
        });
    }


}

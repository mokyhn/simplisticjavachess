package com.simplisticjavachess.position;

import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.piece.Color;
import com.simplisticjavachess.piece.Piece;

import java.util.Collection;
import java.util.Optional;

public interface Position {
    Color inMove();

    boolean isWhiteInMove();

    Vector getMoveDirection();

    Position setInMove(Color inMove);

    Position flipInMove();

    Piece getPiece(Location location);

    Optional<Piece> getKing(Color color);

    Collection<Piece> getPieces();

    Position insert(Piece piece);

    Position remove(Piece piece);

    Position move(Piece piece, Location to);

    Position setCanCastleShort(boolean flag, Color color);

    Position setCanCastleLong(boolean flag, Color color);

    boolean canCastleShort();

    boolean canCastleLong();

    boolean freeSquare(Location location);

    boolean freeSquares(Location from, Location to);

    boolean isAttacked(Location location);

    Boolean isInCheck(Color color);

    Boolean isInCheck();

    Position setMove(Move move);

    Optional<Move> getEnpassantMove();

    Position tickGameClock();

    Position tickFiftyMoveDrawClock();

    Position resetFiftyMoveDrawClock();

    boolean isDrawBy50Move();

    int getChessHashCode();
}

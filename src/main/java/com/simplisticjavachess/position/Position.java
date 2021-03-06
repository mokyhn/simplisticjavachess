package com.simplisticjavachess.position;


import com.simplisticjavachess.Immutable;
import com.simplisticjavachess.game.CastlingState;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.piece.Color;
import com.simplisticjavachess.piece.Piece;
import com.simplisticjavachess.piece.PieceMap;

import java.util.Collection;

import java.util.List;
import java.util.Optional;

@Immutable
public final class Position {
    private final Color inMove;
    private final CastlingState castlingState;
    private final PieceMap pieceMap;

    private final Optional<Move> enpassantMove;
    private final HalfMoveClock fiftyMoveDrawClock;
    private final HalfMoveClock gameClock;

    public Position(Color inMove, CastlingState castlingState, List<Piece> pieces,
                             Optional<Move> enpassantMove, HalfMoveClock fiftyMoveDraw, HalfMoveClock gameClock) {
        this.inMove = inMove;
        this.castlingState = castlingState;
        this.pieceMap = new PieceMap(pieces);
        this.enpassantMove = enpassantMove;
        this.fiftyMoveDrawClock = fiftyMoveDraw;
        this.gameClock = gameClock;
    }

    private Position(Color inMove, CastlingState castlingState, PieceMap pieceMap,
                              Optional<Move> enpassantMove, HalfMoveClock fiftyMoveDraw, HalfMoveClock gameClock) {
        this.inMove = inMove;
        this.castlingState = castlingState;
        this.pieceMap = pieceMap;
        this.enpassantMove = enpassantMove;
        this.fiftyMoveDrawClock = fiftyMoveDraw;
        this.gameClock = gameClock;
    }

    public Color inMove() {
        return inMove;
    }

    public Position setInMove(Color inMove) {
        Position newPosition = new Position(inMove, this.castlingState, this.pieceMap, this.enpassantMove,
                this.fiftyMoveDrawClock, this.gameClock);
        return newPosition;
    }

    public Position flipInMove() {
        Position newPosition = new Position(inMove.opponent(), this.castlingState, this.pieceMap,
                this.enpassantMove, this.fiftyMoveDrawClock, this.gameClock);
        return newPosition;
    }

    public Position setCanCastleShort(boolean flag, Color color) {
        CastlingState newCastlingState;

        newCastlingState = castlingState.setCanCastleShort(color, flag);
        return new Position(inMove, newCastlingState, this.pieceMap, this.enpassantMove,
                this.fiftyMoveDrawClock, this.gameClock);
    }

    public Position setCanCastleLong(boolean flag, Color color) {
        CastlingState newCastlingState;
        newCastlingState = castlingState.setCanCastleLong(color, flag);
        return new Position(inMove, newCastlingState, this.pieceMap, this.enpassantMove,
                this.fiftyMoveDrawClock, this.gameClock);
    }

    /**
     *
     * @param piece to insert
     * @return the resulting position
     */
    public Position insert(Piece piece) {
        return new Position(this.inMove, this.castlingState, pieceMap.insert(piece), this.enpassantMove,
                this.fiftyMoveDrawClock, this.gameClock);
    }

    public Piece getPiece(Location location) {
        return pieceMap.get(location);
    }

    public Collection<Piece> getPieces() {
        return pieceMap.values();
    }

    /**
     * Remove a piece
     * @param piece - to remove
     * @return the resulting position
     */
    public Position remove(Piece piece) {
        return new Position(this.inMove, this.castlingState, pieceMap.remove(piece), this.enpassantMove,
                this.fiftyMoveDrawClock, this.gameClock);
    }

    public Position move(Piece piece, Location to) {
        return new Position(this.inMove, this.castlingState, pieceMap.move(piece, to), this.enpassantMove,
                this.fiftyMoveDrawClock, this.gameClock);
    }

    public Optional<Piece> getKing(Color color) {
        return pieceMap.getKing(color);
    }

    public boolean freeSquare(Location location) {
        return pieceMap.freeSquare(location);
    }

    public boolean isAttacked(Location location) {
        return PositionInference.attacks(this, location, inMove().opponent()) != null;
    }

    /**
     * @param color
     * @return Is player with color color in check?
     */
    public Boolean isInCheck(Color color) {
        return PositionInference.isInCheck(this, color);
    }

    /**
     * @return Is player in move color color in check?
     */
    public Boolean isInCheck() {
        return PositionInference.isInCheck(this, this.inMove());
    }


    /**
     * Are all locations free between from and to?
     * @param from location to check from
     * @param to location to check towards
     * @return true if all locations in between are free and false otherwise
     */
     public boolean freeSquares(Location from, Location to) {
        for (Location location : from.iterateTO(to)) {
            if (!freeSquare(location)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return Ascii representation of the position
     */
    String getPositionString() {
        String result = pieceMap.getPositionString();
        result = result + (inMove == Color.WHITE ? "  White to move\n" : "  Black to move\n");
        result = result + castlingState.toString();
        result =
                result + "Half move: " + this.gameClock.toString() + "          fifty move rule draw clock: " + fiftyMoveDrawClock.toString();
        return result;
    }

    public String toString() {
        return this.getPositionString();
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object instanceof Position) {
            Position position = (Position) object;

            return this.getChessHashCode() == position.getChessHashCode() &&
                    this.inMove == position.inMove &&
                    this.castlingState.equals(position.castlingState) &&
                    this.pieceMap.equals(position.pieceMap) &&
                    this.isEnpassant() == position.isEnpassant() &&
                    implies(this.isEnpassant(), this.enpassantMove.equals(position.enpassantMove));
        } else {
            return false;
        }
    }

    //TODO: Add tests that show that hashing lives up to the requirements for chess positions also wrt. definitions
    //needed for threefold repetition checking
    public int hashCode() {
        return getChessHashCode();
    }

    public int getChessHashCode() {
        return inMove.getChessHashCode() ^ castlingState.getChessHashCode() ^ pieceMap.getChessHashCode() ^ (this.isEnpassant() ? this.getEnpassantMove().hashCode() : 0);
    }

    public Optional<Move> getEnpassantMove() {
        return enpassantMove;
    }

    public boolean canCastleShort() {
        return castlingState.canCastleShort(inMove);
    }

    public boolean canCastleLong() {
        return castlingState.canCastleLong(inMove);
    }

    public Vector getMoveDirection() {
        return Vector.getMoveDirection(inMove());
    }

    public boolean isWhiteInMove() {
        return Color.WHITE.equals(inMove);
    }

    public Position setMove(Move move) {
        return new Position(inMove, castlingState, pieceMap, Optional.of(move), fiftyMoveDrawClock, gameClock);
    }

    private boolean isEnpassant() {
        if (enpassantMove.isPresent()) {
            Move move = enpassantMove.get();
            Piece piece = getPiece(move.getTo());
            return piece.getType().isPawn() && move.distance() == 2;
        } else {
            return false;
        }

    }

    private boolean implies(boolean a, boolean b) {
        return !a || b;
    }

    public Position tickGameClock() {
        return new Position(inMove, castlingState, pieceMap, enpassantMove, fiftyMoveDrawClock,
                gameClock.tick());
    }

    public Position tickFiftyMoveDrawClock() {
        return new Position(inMove, castlingState, pieceMap, enpassantMove, fiftyMoveDrawClock.tick(),
                gameClock);
    }

    public Position resetFiftyMoveDrawClock() {
        return new Position(inMove, castlingState, pieceMap, enpassantMove, fiftyMoveDrawClock.reset(),
                gameClock);
    }

    public boolean isDrawBy50Move() {
        return fiftyMoveDrawClock.isFifty();
    }
}

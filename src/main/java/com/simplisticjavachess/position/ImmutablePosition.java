/**
 * @author Morten Kühnrich
 */

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
public final class ImmutablePosition implements Position {
    private final Color inMove;
    private final CastlingState castlingState;
    private final PieceMap pieceMap;

    private final Optional<Move> enpassantMove;
    private final HalfMoveClock fiftyMoveDrawClock;
    private final HalfMoveClock gameClock;

    public ImmutablePosition(Color inMove, CastlingState castlingState, List<Piece> pieces,
                             Optional<Move> enpassantMove, HalfMoveClock fiftyMoveDraw, HalfMoveClock gameClock) {
        this.inMove = inMove;
        this.castlingState = castlingState;
        this.pieceMap = new PieceMap(pieces);
        this.enpassantMove = enpassantMove;
        this.fiftyMoveDrawClock = fiftyMoveDraw;
        this.gameClock = gameClock;
    }

    private ImmutablePosition(Color inMove, CastlingState castlingState, PieceMap pieceMap,
                              Optional<Move> enpassantMove, HalfMoveClock fiftyMoveDraw, HalfMoveClock gameClock) {
        this.inMove = inMove;
        this.castlingState = castlingState;
        this.pieceMap = pieceMap;
        this.enpassantMove = enpassantMove;
        this.fiftyMoveDrawClock = fiftyMoveDraw;
        this.gameClock = gameClock;
    }

    @Override
    public Color inMove() {
        return inMove;
    }

    @Override
    public Position setInMove(Color inMove) {
        Position newPosition = new ImmutablePosition(inMove, this.castlingState, this.pieceMap, this.enpassantMove,
                this.fiftyMoveDrawClock, this.gameClock);
        return newPosition;
    }

    @Override
    public Position flipInMove() {
        Position newPosition = new ImmutablePosition(inMove.opponent(), this.castlingState, this.pieceMap,
                this.enpassantMove, this.fiftyMoveDrawClock, this.gameClock);
        return newPosition;
    }

    @Override
    public Position setCanCastleShort(boolean flag, Color color) {
        CastlingState newCastlingState;

        newCastlingState = castlingState.setCanCastleShort(color, flag);
        return new ImmutablePosition(inMove, newCastlingState, this.pieceMap, this.enpassantMove,
                this.fiftyMoveDrawClock, this.gameClock);
    }

    @Override
    public Position setCanCastleLong(boolean flag, Color color) {
        CastlingState newCastlingState;
        newCastlingState = castlingState.setCanCastleLong(color, flag);
        return new ImmutablePosition(inMove, newCastlingState, this.pieceMap, this.enpassantMove,
                this.fiftyMoveDrawClock, this.gameClock);
    }

    /**
     *
     * @param piece to insert
     * @return the resulting position
     */
    public Position insert(Piece piece) {
        return new ImmutablePosition(this.inMove, this.castlingState, pieceMap.insert(piece), this.enpassantMove,
                this.fiftyMoveDrawClock, this.gameClock);
    }

    @Override
    public Piece getPiece(Location location) {
        return pieceMap.get(location);
    }

    @Override
    public Collection<Piece> getPieces() {
        return pieceMap.values();
    }

    /**
     * Remove a piece
     * @param piece - to remove
     * @return the resulting position
     */
    public Position remove(Piece piece) {
        return new ImmutablePosition(this.inMove, this.castlingState, pieceMap.remove(piece), this.enpassantMove,
                this.fiftyMoveDrawClock, this.gameClock);
    }

    @Override
    public Position move(Piece piece, Location to) {
        return new ImmutablePosition(this.inMove, this.castlingState, pieceMap.move(piece, to), this.enpassantMove,
                this.fiftyMoveDrawClock, this.gameClock);
    }

    @Override
    public Optional<Piece> getKing(Color color) {
        return pieceMap.getKing(color);
    }

    @Override
    public boolean freeSquare(Location location) {
        return pieceMap.freeSquare(location);
    }

    @Override
    public boolean isAttacked(Location location) {
        return PositionInference.attacks(this, location, inMove().opponent()) != null;
    }

    /**
     * @param color
     * @return Is player with color color in check?
     */
    @Override
    public Boolean isInCheck(Color color) {
        return PositionInference.isInCheck(this, color);
    }

    /**
     * @return Is player in move color color in check?
     */
    @Override
    public Boolean isInCheck() {
        return PositionInference.isInCheck(this, this.inMove());
    }


    /**
     * Are all locations free between from and to?
     * @param from location to check from
     * @param to location to check towards
     * @return true if all locations in between are free and false otherwise
     */
    @Override
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

    @Override
    public String toString() {
        return this.getPositionString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object instanceof ImmutablePosition) {
            ImmutablePosition position = (ImmutablePosition) object;

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
    @Override
    public int hashCode() {
        return getChessHashCode();
    }

    @Override
    public int getChessHashCode() {
        return inMove.getChessHashCode() ^ castlingState.getChessHashCode() ^ pieceMap.getChessHashCode() ^ (this.isEnpassant() ? this.getEnpassantMove().hashCode() : 0);
    }

    @Override
    public Optional<Move> getEnpassantMove() {
        return enpassantMove;
    }

    @Override
    public boolean canCastleShort() {
        return castlingState.canCastleShort(inMove);
    }

    @Override
    public boolean canCastleLong() {
        return castlingState.canCastleLong(inMove);
    }

    @Override
    public Vector getMoveDirection() {
        return Vector.getMoveDirection(inMove());
    }

    @Override
    public boolean isWhiteInMove() {
        return Color.WHITE.equals(inMove);
    }

    @Override
    public Position setMove(Move move) {
        return new ImmutablePosition(inMove, castlingState, pieceMap, Optional.of(move), fiftyMoveDrawClock, gameClock);
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

    @Override
    public Position tickGameClock() {
        return new ImmutablePosition(inMove, castlingState, pieceMap, enpassantMove, fiftyMoveDrawClock,
                gameClock.tick());
    }

    @Override
    public Position tickFiftyMoveDrawClock() {
        return new ImmutablePosition(inMove, castlingState, pieceMap, enpassantMove, fiftyMoveDrawClock.tick(),
                gameClock);
    }

    @Override
    public Position resetFiftyMoveDrawClock() {
        return new ImmutablePosition(inMove, castlingState, pieceMap, enpassantMove, fiftyMoveDrawClock.reset(),
                gameClock);
    }

    @Override
    public boolean isDrawBy50Move() {
        return fiftyMoveDrawClock.isFifty();
    }
}

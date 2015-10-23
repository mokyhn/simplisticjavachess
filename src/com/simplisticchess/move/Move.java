package com.simplisticchess.move;

/**
 *
 * @author Morten Kühnrich
 */

import com.simplisticchess.board.Board;
import com.simplisticchess.Chessio;
import com.simplisticchess.piece.Piece;

public final class Move
{

    public int fromX,
            fromY,
            toX,
            toY,
            type, // The move type
            capturedPiece, // Used for storing pieces that are taken
            // by the piece which moves
            whoMoves;

    // The different move types
    public final static int NORMALMOVE = 0,
            // Normal capture
            CAPTURE = 1,
            // The enpassant capture move
            CAPTURE_ENPASSANT = 2,
            // Capture and promotion at the same time
            CAPTURE_AND_PROMOTE_TO_BISHOP = 3,
            CAPTURE_AND_PROMOTE_TO_KNIGHT = 4,
            CAPTURE_AND_PROMOTE_TO_ROOK = 5,
            CAPTURE_AND_PROMOTE_TO_QUEEN = 6,
            // Simple promotions
            PROMOTE_TO_BISHOP = 7,
            PROMOTE_TO_KNIGHT = 8,
            PROMOTE_TO_ROOK = 9,
            PROMOTE_TO_QUEEN = 10,
            // Casteling
            CASTLE_SHORT = 11,
            CASTLE_LONG = 12,
            // Speciel moves
            RESIGN = 13,
            CALL_FOR_DRAW = 14;

    public Move()
    {
    }

    public Move(int fromX, int fromY, int toX, int toY, int type, int capturedPiece, int whoMoves)
    {
        assert fromX >= 0 && fromX <= 7
                && fromY >= 0 && fromY <= 7
                && toX >= 0 && toX <= 7
                && toY >= 0 && toY <= 7
                && (!(fromX == toX && fromY == toY)) : "(fX, fY, tX, tY) = " + "(" + fromX + ", " + fromY + ", " + toX + "," + toY + ")";

        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
        this.type = type;
        this.capturedPiece = capturedPiece;
        this.whoMoves = whoMoves;
    }

    public Move(Move move)
    {
        fromX = move.fromX;
        fromY = move.fromY;
        toX = move.toX;
        toY = move.toY;
        type = move.type;
        whoMoves = move.whoMoves;
        capturedPiece = move.capturedPiece;
    }

    // Used for generation of knight, bishop, rook and queen moves
    public static Move genMove(Board b, Piece fp, int dX, int dY)
    {
        Piece tp;

        Move m = null;

        final int tX = fp.xPos + dX,
                tY = fp.yPos + dY;

        int takenPiece;
        int moveType;

        assert fp.color == b.inMove();

        if (fp.xPos < 0 || fp.xPos > 7
                || fp.yPos < 0 || fp.yPos > 7
                || tX < 0 || tX > 7
                || tY < 0 || tY > 7)
        {
            return null;
        }

        tp = b.getPiece(tX, tY);

        if (tp != null && tp.color == -b.inMove())
        {
            takenPiece = tp.type;
            moveType = Move.CAPTURE;
            m = new Move(fp.xPos, fp.yPos, tX, tY, moveType, takenPiece, b.inMove());
        } else if (b.freeSquare(tX, tY))
        {
            takenPiece = Piece.EMPTY;
            moveType = Move.NORMALMOVE;
            m = new Move(fp.xPos, fp.yPos, tX, tY, moveType, takenPiece, b.inMove());
        }

        return m;
    }

    public boolean aCapture()
    {
        return this.type >= 1 && this.type <= 6;
    }

    public boolean equal(Move m)
    {
        if (m == null)
        {
            return false;
        }

        return fromX == m.fromX
                && toX == m.toX
                && fromY == m.fromY
                && toY == m.toY
                && type == m.type
                && capturedPiece == m.capturedPiece
                && whoMoves == m.whoMoves;
    }

    public boolean aSimplePromotion()
    {
        return (type >= PROMOTE_TO_BISHOP) && (type <= PROMOTE_TO_QUEEN);
    }

    public boolean aCapturePromotion()
    {
        return (type >= CAPTURE_AND_PROMOTE_TO_BISHOP)
                && (type <= CAPTURE_AND_PROMOTE_TO_QUEEN);
    }

    public int promotionTo()
    {
        int r = Piece.EMPTY;

        assert (type == CAPTURE_AND_PROMOTE_TO_BISHOP
                || type == CAPTURE_AND_PROMOTE_TO_KNIGHT
                || type == CAPTURE_AND_PROMOTE_TO_ROOK
                || type == CAPTURE_AND_PROMOTE_TO_QUEEN
                || type == PROMOTE_TO_BISHOP
                || type == PROMOTE_TO_KNIGHT
                || type == PROMOTE_TO_ROOK
                || type == PROMOTE_TO_QUEEN) : "Wrong promotion code";

        switch (type)
        {
            case CAPTURE_AND_PROMOTE_TO_BISHOP:
                r = Piece.BISHOP;
                break;
            case CAPTURE_AND_PROMOTE_TO_KNIGHT:
                r = Piece.KNIGHT;
                break;
            case CAPTURE_AND_PROMOTE_TO_ROOK:
                r = Piece.ROOK;
                break;
            case CAPTURE_AND_PROMOTE_TO_QUEEN:
                r = Piece.QUEEN;
                break;
            case PROMOTE_TO_BISHOP:
                r = Piece.BISHOP;
                break;
            case PROMOTE_TO_KNIGHT:
                r = Piece.KNIGHT;
                break;
            case PROMOTE_TO_ROOK:
                r = Piece.ROOK;
                break;
            case PROMOTE_TO_QUEEN:
                r = Piece.QUEEN;
        }
        return r;
    }

    public static String posToString(int x, int y)
    {
        return Chessio.numToChar(x) + Chessio.numToNumChar(y);
    }

    public static String pieceNumberToChar(int num)
    {
        String letter = "";
        switch (num)
        {
            case Piece.BISHOP:
                letter = "B";
                break;
            case Piece.KNIGHT:
                letter = "K";
                break;
            case Piece.ROOK:
                letter = "R";
                break;
            case Piece.QUEEN:
                letter = "Q";
                break;
        }
        return letter;
    }

    @Override
    public String toString()
    {
        if (type == NORMALMOVE)
        {
            return posToString(fromX, fromY) + "-" + posToString(toX, toY);
        }

        // Normal capture moves
        if (type == CAPTURE_ENPASSANT || type == CAPTURE)
        {
            return posToString(fromX, fromY) + "x" + posToString(toX, toY);
        }

        // mate
        if (type == CAPTURE && capturedPiece == Piece.KING)
        {
            return "mate";
        }

        // Promotions
        if (aSimplePromotion())
        {
            return posToString(fromX, fromY) + "-" + posToString(toX, toY) + "=" + pieceNumberToChar(promotionTo());
        }

        if (aCapturePromotion())
        {
            return posToString(fromX, fromY) + "x" + posToString(toX, toY) + "=" + pieceNumberToChar(promotionTo()); // 
        }

        if (type == CASTLE_SHORT)
        {
            return "o-o";
        }
        if (type == CASTLE_LONG)
        {
            return "o-o-o";
        }

        return "ERR: getMoveStr";
    }

}
package com.simplisticchess.move;

/**
 *
 * @author Morten Kühnrich
 */

import com.simplisticchess.board.Board;
import com.simplisticchess.Chessio;
import com.simplisticchess.piece.Piece;
import com.simplisticchess.piece.Color;
import com.simplisticchess.piece.PieceType;

public final class Move
{

    public int fromX;
    public int fromY;
    public int toX;
    public int toY;
    public MoveType type; 
    public PieceType capturedPiece; // Used for storing pieces that are taken
                              // by the piece which moves

    public Color whoMoves;

    public Move()
    {
    }

    public Move(int fromX, int fromY, int toX, int toY, MoveType type, PieceType capturedPiece, Color whoMoves)
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

        MoveType moveType;

        assert fp.color == b.inMove();

        if (fp.xPos < 0 || fp.xPos > 7
                || fp.yPos < 0 || fp.yPos > 7
                || tX < 0 || tX > 7
                || tY < 0 || tY > 7)
        {
            return null;
        }

        tp = b.getPiece(tX, tY);
        
        PieceType takenPiece;

        if (tp != null && tp.color == b.inMove().flip())
        {
            takenPiece = tp.pieceType;
            moveType = MoveType.CAPTURE;
            m = new Move(fp.xPos, fp.yPos, tX, tY, moveType, takenPiece, b.inMove());
        } else if (b.freeSquare(tX, tY))
        {
            takenPiece = null;
            moveType = MoveType.NORMALMOVE;
            m = new Move(fp.xPos, fp.yPos, tX, tY, moveType, takenPiece, b.inMove());
        }

        return m;
    }

    public boolean aCapture()
    {
        return type.isCapture();
    }

  
    public boolean aSimplePromotion()
    {
        return type.isSimplePromotion();
    }

    public boolean aCapturePromotion()
    {
        return type.isCapturePromotion();
    }

    public PieceType promotionTo()
    {
        return type.getPromotionPiece();
    }

    public static String posToString(int x, int y)
    {
        return Chessio.numToChar(x) + Chessio.numToNumChar(y);
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

    @Override
    public String toString()
    {
        if (type == MoveType.NORMALMOVE)
        {
            return posToString(fromX, fromY) + "-" + posToString(toX, toY);
        }

        // Normal capture moves
        if (type == MoveType.CAPTURE_ENPASSANT || type == MoveType.CAPTURE)
        {
            return posToString(fromX, fromY) + "x" + posToString(toX, toY);
        }

        // mate
        if (type == MoveType.CAPTURE && capturedPiece == PieceType.KING)
        {
            return "mate";
        }

        // Promotions
        if (aSimplePromotion())
        {
            return posToString(fromX, fromY) + "-" + posToString(toX, toY) + "=" + promotionTo().getPieceLetter();
        }

        if (aCapturePromotion())
        {
            return posToString(fromX, fromY) + "x" + posToString(toX, toY) + "=" + promotionTo().getPieceLetter(); 
        }

        if (type == MoveType.CASTLE_SHORT)
        {
            return "o-o";
        }
        if (type == MoveType.CASTLE_LONG)
        {
            return "o-o-o";
        }

        return "ERR: getMoveStr";
    }

}

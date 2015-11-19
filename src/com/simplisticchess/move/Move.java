package com.simplisticchess.move;

/**
 *
 * @author Morten Kühnrich
 */

import com.simplisticchess.board.Board;
import com.simplisticchess.piece.Piece;
import com.simplisticchess.piece.Color;
import com.simplisticchess.piece.PieceType;

public final class Move
{

    public int fromX;
    public int fromY;
    public int toX;
    public int toY;
    public MoveType moveType; 
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
        this.moveType = type;
        this.capturedPiece = capturedPiece;
        this.whoMoves = whoMoves;
    }

    public Move(Move move)
    {
        fromX = move.fromX;
        fromY = move.fromY;
        toX = move.toX;
        toY = move.toY;
        moveType = move.moveType;
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
        return moveType.isCapture();
    }

  
    public boolean aSimplePromotion()
    {
        return moveType.isSimplePromotion();
    }

    public boolean aCapturePromotion()
    {
        return moveType.isCapturePromotion();
    }

    public PieceType promotionTo()
    {
        return moveType.getPromotionPiece();
    }

    private String posToString(int x, int y)
    {
        return numToChar(x) + numToNumChar(y);
    }
  
    private static String numToChar(int pos) {
            switch (pos) {
            case 0: return "a";
            case 1:	return "b";
            case 2:	return "c";
            case 3:	return "d";
            case 4:	return "e";
            case 5:	return "f";
            case 6:	return "g";
            case 7:	return "h";
            }
            System.out.println( "ERR: numToChar: input was " + pos);
            System.exit(1);
            return "";
    }

    private String numToNumChar(int pos) {
            switch (pos) {
            case 0: return "1";
            case 1:	return "2";
            case 2:	return "3";
            case 3:	return "4";
            case 4:	return "5";
            case 5:	return "6";
            case 6:	return "7";
            case 7:	return "8";
            }
            System.out.println( "ERR: numToNumChar: input was " + pos);
            System.exit(1);
            return "";
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
                && moveType == m.moveType
                && capturedPiece == m.capturedPiece
                && whoMoves == m.whoMoves;
    }

    @Override
    public String toString()
    {
        if (moveType == MoveType.NORMALMOVE)
        {
            return posToString(fromX, fromY) + "-" + posToString(toX, toY);
        }

        // Normal capture moves
        if (moveType == MoveType.CAPTURE_ENPASSANT || moveType == MoveType.CAPTURE)
        {
            return posToString(fromX, fromY) + "x" + posToString(toX, toY);
        }

        // mate
        if (moveType == MoveType.CAPTURE && capturedPiece == PieceType.KING)
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

        if (moveType == MoveType.CASTLE_SHORT)
        {
            return "o-o";
        }
        if (moveType == MoveType.CASTLE_LONG)
        {
            return "o-o-o";
        }

        return "ERR: getMoveStr";
    }

}

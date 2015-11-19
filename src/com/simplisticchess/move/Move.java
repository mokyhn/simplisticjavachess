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

    private int fromX;
    private int fromY;
    private int toX;
    private int toY;
    private MoveType moveType; 
    private PieceType capturedPiece; // Used for storing pieces that are taken
                              // by the piece which moves

    private Color whoMoves;

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

        final int tX = fp.getxPos() + dX,
                tY = fp.getyPos() + dY;

        MoveType moveType;

        assert fp.getColor() == b.inMove();

        if (fp.getxPos() < 0 || fp.getxPos() > 7
                || fp.getyPos() < 0 || fp.getyPos() > 7
                || tX < 0 || tX > 7
                || tY < 0 || tY > 7)
        {
            return null;
        }

        tp = b.getPiece(tX, tY);
        
        PieceType takenPiece;

        if (tp != null && tp.getColor() == b.inMove().flip())
        {
            takenPiece = tp.getPieceType();
            moveType = MoveType.CAPTURE;
            m = new Move(fp.getxPos(), fp.getyPos(), tX, tY, moveType, takenPiece, b.inMove());
        } else if (b.freeSquare(tX, tY))
        {
            takenPiece = null;
            moveType = MoveType.NORMALMOVE;
            m = new Move(fp.getxPos(), fp.getyPos(), tX, tY, moveType, takenPiece, b.inMove());
        }

        return m;
    }

    public boolean aCapture()
    {
        return getMoveType().isCapture();
    }

  
    public boolean aSimplePromotion()
    {
        return getMoveType().isSimplePromotion();
    }

    public boolean aCapturePromotion()
    {
        return getMoveType().isCapturePromotion();
    }

    public PieceType promotionTo()
    {
        return getMoveType().getPromotionPiece();
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

        return getFromX() == m.getFromX()
                && getToX() == m.getToX()
                && getFromY() == m.getFromY()
                && getToY() == m.getToY()
                && getMoveType() == m.getMoveType()
                && getCapturedPiece() == m.getCapturedPiece()
                && getWhoMoves() == m.getWhoMoves();
    }

    @Override
    public String toString()
    {
        if (getMoveType() == MoveType.NORMALMOVE)
        {
            return posToString(getFromX(), getFromY()) + "-" + posToString(getToX(), getToY());
        }

        // Normal capture moves
        if (getMoveType() == MoveType.CAPTURE_ENPASSANT || getMoveType() == MoveType.CAPTURE)
        {
            return posToString(getFromX(), getFromY()) + "x" + posToString(getToX(), getToY());
        }

        // mate
        if (getMoveType() == MoveType.CAPTURE && getCapturedPiece() == PieceType.KING)
        {
            return "mate";
        }

        // Promotions
        if (aSimplePromotion())
        {
            return posToString(getFromX(), getFromY()) + "-" + posToString(getToX(), getToY()) + "=" + promotionTo().getPieceLetter();
        }

        if (aCapturePromotion())
        {
            return posToString(getFromX(), getFromY()) + "x" + posToString(getToX(), getToY()) + "=" + promotionTo().getPieceLetter(); 
        }

        if (getMoveType() == MoveType.CASTLE_SHORT)
        {
            return "o-o";
        }
        if (getMoveType() == MoveType.CASTLE_LONG)
        {
            return "o-o-o";
        }

        return "ERR: getMoveStr";
    }

    /**
     * @return the fromX
     */
    public int getFromX()
    {
        return fromX;
    }

 
    /**
     * @return the fromY
     */
    public int getFromY()
    {
        return fromY;
    }

     /**
     * @return the toX
     */
    public int getToX()
    {
        return toX;
    }


    /**
     * @return the toY
     */
    public int getToY()
    {
        return toY;
    }

    /**
     * @return the moveType
     */
    public MoveType getMoveType()
    {
        return moveType;
    }


    /**
     * @return the capturedPiece
     */
    public PieceType getCapturedPiece()
    {
        return capturedPiece;
    }


    /**
     * @return the whoMoves
     */
    public Color getWhoMoves()
    {
        return whoMoves;
    }


}

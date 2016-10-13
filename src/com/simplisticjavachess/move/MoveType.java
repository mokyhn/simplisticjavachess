package com.simplisticjavachess.move;

import com.simplisticjavachess.piece.PieceType;

/**
 *
 * @author Morten Kühnrich
 */
public enum MoveType
{
    
    NORMALMOVE(0),    
    CAPTURE(1),
    CAPTURE_ENPASSANT(2),

    CAPTURE_AND_PROMOTE_TO_BISHOP(3),
    CAPTURE_AND_PROMOTE_TO_KNIGHT(4),
    CAPTURE_AND_PROMOTE_TO_ROOK(5),
    CAPTURE_AND_PROMOTE_TO_QUEEN(6),

    PROMOTE_TO_BISHOP(7),
    PROMOTE_TO_KNIGHT(8),
    PROMOTE_TO_ROOK(9),
    PROMOTE_TO_QUEEN(10),

    CASTLE_SHORT(11),
    CASTLE_LONG(12),

    RESIGN(13),
    CALL_FOR_DRAW(14);

    final int moveType;

    MoveType(int moveType)
    {
        this.moveType = moveType;
    }
    
    public int getVal() 
    {
        return moveType;
    }
    
    public boolean isCapture()
    {
        return moveType >= 1 && moveType <= 6;
    }
    
    public boolean isCapturePromotion()
    {
        return moveType >= 3 && moveType <= 6;
    }
    
    public boolean isSimplePromotion()
    {
        return moveType >= 7 && moveType <= 10;
    }

  

    public PieceType getPromotionPiece()
    {
        PieceType pieceType;

        switch (this)
        {
            case CAPTURE_AND_PROMOTE_TO_BISHOP:
                pieceType = PieceType.BISHOP;
                break;
            case CAPTURE_AND_PROMOTE_TO_KNIGHT:
                pieceType = PieceType.KNIGHT;
                break;
            case CAPTURE_AND_PROMOTE_TO_ROOK:
                pieceType = PieceType.ROOK;
                break;
            case CAPTURE_AND_PROMOTE_TO_QUEEN:
                pieceType = PieceType.QUEEN;
                break;
            case PROMOTE_TO_BISHOP:
                pieceType = PieceType.BISHOP;
                break;
            case PROMOTE_TO_KNIGHT:
                pieceType = PieceType.KNIGHT;
                break;
            case PROMOTE_TO_ROOK:
                pieceType = PieceType.ROOK;
                break;
            case PROMOTE_TO_QUEEN:
                pieceType = PieceType.QUEEN;
                break;
            default:
                pieceType = null;
        }
        return pieceType;
    }

}

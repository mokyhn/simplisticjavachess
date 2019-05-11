package com.simplisticjavachess.piece;

/**
 *
 * @author Morten Kühnrich
 */
public enum PieceType
{
    PAWN,
    BISHOP,
    KNIGHT,
    ROOK,
    QUEEN,
    KING;

    public String getPieceLetter() 
    {
        switch (this)
        {
            case PAWN:
                return "P";
            case BISHOP:
                return "B";
            case KNIGHT:
                return "N";
            case ROOK:
                return "R";
            case QUEEN:
                return "Q";
            case KING:
                return "K";
            default:
                throw new IllegalStateException(); // Not reachable
        }    
    }
    
    public String getPieceLetter(Color color) 
    {
        String r = getPieceLetter();
        return color == Color.BLACK ? r.toLowerCase() : r.toUpperCase();
    }
    
}

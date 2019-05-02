package com.simplisticjavachess.piece;

/**
 *
 * @author Morten Kühnrich
 */
public enum PieceType
{
    PAWN(0),
    BISHOP(1),
    KNIGHT(2),
    ROOK(3),
    QUEEN(4),
    KING(5);
 
    private final int type;
    
    PieceType(int type)
    {
        this.type = type;
    }
    
    public int getType()
    {
        return type;
    }
    
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
                return null; // Not reachable
        }    
    }
    
    public String getPieceLetter(Color color) 
    {
        String r = getPieceLetter();
        String result = color == Color.BLACK ? r.toLowerCase() : r.toUpperCase();
        return result;  
    }
    
}

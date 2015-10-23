package com.simplisticchess.piece;

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
    
}

package com.simplisticjavachess.piece;

/**
 *
 * @author Morten Kühnrich
 */
public enum PieceType
{
    PAWN(-1331833639),
    BISHOP(-935112331),
    KNIGHT(1161123721),
    ROOK(27575945),
    QUEEN(-905638995),
    KING(843184298);

    private final int chessHashCode;

    PieceType(int chessHashCode)
    {
        this.chessHashCode = chessHashCode;
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
                throw new IllegalStateException(); // Not reachable
        }    
    }
    
    public String getPieceLetter(Color color) 
    {
        String r = getPieceLetter();
        return color == Color.BLACK ? r.toLowerCase() : r.toUpperCase();
    }

    public int getChessHashCode() {
        return chessHashCode;
    }

    public boolean isPawn()
    {
        return PAWN.equals(this);
    }

}

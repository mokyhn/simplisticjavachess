package com.simplisticjavachess.piece;

/**
 *
 * @author Morten Kühnrich
 */
public enum Color
{
    BLACK(-1, -1339633704),
    WHITE(1, 1760017000);

    private final int color;
    private final int chessHashCode;


    Color(int color, int chessHashCode)
    {
        this.color = color;
        this.chessHashCode = chessHashCode;
    }

    public int getColor()
    {
        return color;
    }

    public Color opponent()
    {
        return this == BLACK ? WHITE : BLACK;
    }

    public String getColorString()
    {
        if (this == BLACK) 
        {
            return "b";
        }
        else
        {
            return "w";
        }
    }

    @Override
    public String toString()
    {
        return getColorString();
    }

    public int getChessHashCode()
    {
        return chessHashCode;
    }

    public boolean isWhite()
    {
        return WHITE.equals(this);
    }

    public boolean isBlack()
    {
        return BLACK.equals(this);
    }
}

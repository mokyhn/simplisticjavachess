package com.simplisticchess.piece;

/**
 *
 * @author Morten Kühnrich
 */
public enum Color
{
    BLACK(-1), WHITE(1);
    private final int color;

    Color(int color)
    {
        this.color = color;
    }

    public int getColor()
    {
        return color;
    }

    public Color flip()
    {
        return this == BLACK ? WHITE : BLACK;
    }
    
}

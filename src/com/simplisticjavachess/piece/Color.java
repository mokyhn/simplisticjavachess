package com.simplisticjavachess.piece;

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
    
}

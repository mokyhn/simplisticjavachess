package com.simplisticjavachess.board;

import com.simplisticjavachess.piece.Color;

/**
 *
 * @author Morten Kühnrich
 */
public class Vector
{
    private final int dx;
    private final int dy;
    
    public Vector(int dx, int dy)
    {
        this.dx = dx;
        this.dy = dy;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }
    
    public Vector add(Vector v)
    {
        return new Vector(this.dx + v.getDx(), this.dy + v.getDy());
    }
    
    public Vector scale(int scalar)
    {
        return new Vector(scalar * this.dx, scalar * this.dy);
    }
    
    public Vector inverse()
    {
        return this.scale(-1);
    }
    
    public static Vector getMoveDirection(Color color)
    {
        switch (color)
        {
            case BLACK:
                return new Vector(0,-1);
            case WHITE:
                return new Vector(0,1);
            default:
                throw new IllegalStateException();
        }
    }

    public Location translocate(Location location)
    {
        return new Location(location.x + this.dx, location.y + this.dy);
    }
}

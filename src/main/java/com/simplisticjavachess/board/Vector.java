package com.simplisticjavachess.board;

import com.simplisticjavachess.piece.Color;
import java.util.Objects;

/**
 *
 * @author Morten Kühnrich
 */
public class Vector
{
    public final static Vector UP   = new Vector(0, 1);
    public final static Vector DOWN = new Vector(0, -1);
    public final static Vector LEFT = new Vector(-1, 0);
    public final static Vector RIGHT = new Vector(1, 0);
    
    public final static Vector LEFT_LEFT = new Vector(-2, 0);
    public final static Vector RIGHT_RIGHT = new Vector(2, 0);
    
    public final static Vector UP_AND_RIGHT = UP.add(RIGHT);
    public final static Vector UP_AND_LEFT  = UP.add(LEFT);
    public final static Vector DOWN_AND_RIGHT = DOWN.add(RIGHT);
    public final static Vector DOWN_AND_LEFT = DOWN.add(LEFT);

    
    private final int dx;
    private final int dy;
    private final int hashCode;
    
    public Vector(int dx, int dy)
    {
        this.dx = dx;
        this.dy = dy;
        this.hashCode = Objects.hash(dx, dy);
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

    @Override
    public boolean equals(Object other)
    {
        if (other instanceof Vector)
        {
            Vector vector = (Vector) other;
            return this.dx == vector.dx && this.dy == vector.dy;
        }
        else
        {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return hashCode;
    }
    
}
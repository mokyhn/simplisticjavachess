package com.simplisticchess.position;

public final class Point 
{
    final byte x;
    final byte y;
    
    private final static String letters[] = {"a", "b", "c", "d", "e", "f", "g", "h"};
    
    public Point(byte x, byte y) 
    {
        this.x = x;
        this.y = y;
    }
    
    public Point(int x, int y)
    {
        this((byte) x, (byte) y);
    }
    
    public Point(Point p)
    {
        this(p.getX(), p.getY());
    }
    
    public byte getX() 
    {
        return x;
    }
    
    public byte getY()
    {
        return y;
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (o instanceof Point)
        {
            Point p = (Point) o;
            return x == p.getX() && y == p.getY();
        }
        else
        {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 10 * this.x + this.y;
        return hash;
    }
    
    
    @Override
    public String toString()
    {
        return letters[x] + y;
    }
    
    
    
}

package com.simplisticchess.position;

public final class Location 
{
    final byte x;
    final byte y;
    
    private final static String letters[] = {"a", "b", "c", "d", "e", "f", "g", "h"};
    
    public Location(byte x, byte y) 
    {
        assert x >= 0 && x <= 7 && y >= 0 && y <= 7 : "Piece range error in x or y ";
        this.x = x;
        this.y = y;
    }
    
    public Location(int x, int y)
    {
        this((byte) x, (byte) y);
    }
    
    public Location(Location p)
    {
        this(p.getX(), p.getY());
    }
    
    public Location(String position)
    {
        position = position.toLowerCase();
        x = (byte) (position.charAt(0) - 'a');
        y = (byte) (-1 + position.charAt(1) - '0');
        assert x >= 0 && x <= 7 && y >= 0 && y <= 7 : "Piece range error in x or y ";
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
        if (o instanceof Location)
        {
            Location p = (Location) o;
            return x == p.getX() && y == p.getY();
        }
        else
        {
            return false;
        }
    }

    @Override
    public int hashCode() 
    {
        int hash = 10 * this.x + this.y;
        return hash;
    }
    
    
    @Override
    public String toString()
    {
        return letters[x] + (y + 1);
    }
    
}

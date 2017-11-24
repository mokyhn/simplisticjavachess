package com.simplisticjavachess.position;

public final class Location 
{
    final byte x;
    final byte y;
    
    private final static String letters[] = {"a", "b", "c", "d", "e", "f", "g", "h"};
    
    public Location(byte x, byte y) 
    {
        assert x >= 0 && x <= 7 && y >= 0 && y <= 7 : "Piece range error: x="+x+" and y="+y;
        this.x = x;
        this.y = y;
    }
    
    public Location(int x, int y)
    {
        this((byte) x, (byte) y);
    }
    
    public Location(String position) throws InvalidLocationException
    {
        position = position.toLowerCase();
        x = (byte) (position.charAt(0) - 'a');
        y = (byte) (-1 + position.charAt(1) - '0');
        if (x < 0 || x > 7 || y < 0 || y > 7)
        {
            throw new InvalidLocationException();
        }
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
    public boolean equals(Object object)
    {
        if (object instanceof Location)
        {
            Location location = (Location) object;
            return this.x == location.getX() && this.y == location.getY();
        }
        else
        {
            return false;
        }
    }

    @Override
    public int hashCode() 
    {
        int hash = this.x + this.y * 8;
        return hash;
    }
    
    
    @Override
    public String toString()
    {
        return letters[x] + (y + 1);
    }
    
    /**
     * 
     * @param location
     * @return true if x equals, i.e. the locations specify the same column
     */
    public boolean fileEquals(Location location) 
    {
        return x == location.x;
    }

     /**
     * 
     * @param location
     * @return true if x not equals, i.e. the locations do not specify the same column
     */
    public boolean fileDifferent(Location location) 
    {
        return x != location.x;
    }
   
    
    /**
     * 
     * @param location
     * @return true if y equals, i.e. the locations specify the same row
     */
    public boolean rankEquals(Location location)
    {
        return y == location.y;
    }

    /**
     * 
     * @param location
     * @return true if y not equals, i.e. the locations do not specify the same row
     */
    public boolean rankDifferent(Location location)
    {
        return y != location.y;
    }
    
    
}
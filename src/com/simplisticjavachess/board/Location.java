package com.simplisticjavachess.board;

public final class Location 
{
    final int x;
    final int y;
    final int hashCode;
    
    private final static String[] LETTERS = {"a", "b", "c", "d", "e", "f", "g", "h"};
    
    public Location(int x, int y) 
    {
        assert x >= 0 && x <= 7 && y >= 0 && y <= 7 : "Piece range error: x="+x+" and y="+y;
        this.x = x;
        this.y = y;
        this.hashCode = calculateHash(x, y);
    }
    
    public Location(String position) throws InvalidLocationException
    {
        position = position.toLowerCase();
        x = (int) (position.charAt(0) - 'a');
        y = (int) (-1 + position.charAt(1) - '0');
        this.hashCode = calculateHash(x, y);
        if (x < 0 || x > 7 || y < 0 || y > 7)
        {
            throw new InvalidLocationException();
        }
    }
    
    public static Location fromString(String position)
    {
        return new Location(position);
    }
    
    public int getX() 
    {
        return x;
    }
    
    public int getY()
    {
        return y;
    }
    
    public int horizontalDistance(Location other)
    {
        return Math.abs(this.x - other.x);
    }
    
    public int verticalDistance(Location other)
    {
        return Math.abs(this.y - other.y);
    }
    
    public int distanceTo(Location other)
    {
        return (Math.max
                        (
                            horizontalDistance(other)
                                ,
                            verticalDistance(other)
                        )
                );
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

    private int calculateHash(int x, int y)
    {
        return this.x + this.y * 8;
    }
    
    @Override
    public int hashCode() 
    {        
        return hashCode;
    }
    
    
    @Override
    public String toString()
    {
        return LETTERS[x] + (y + 1);
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

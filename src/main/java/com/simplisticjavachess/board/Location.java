package com.simplisticjavachess.board;

import java.util.*;

public final class Location
{
    final int x;
    final int y;
    final int hashCode;
    
    private final static String[] LETTERS = {"a", "b", "c", "d", "e", "f", "g", "h"};
    
    public Location(int x, int y) 
    {
        this.x = x;
        this.y = y;
        this.hashCode = Objects.hash(x, y);
    }
    
    public Location(String position)
    {
        position = position.toLowerCase();
        x = (position.charAt(0) - 'a');
        y = (-1 + position.charAt(1) - '0');
        this.hashCode = Objects.hash(x, y);
        if (x < 0 || x > 7 || y < 0 || y > 7)
        {
            throw new IllegalArgumentException("Given a bad position: " + position);
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
        return (Math.max(horizontalDistance(other),verticalDistance(other)));
    }
    
    @Override
    public boolean equals(Object object)
    {
        if (this == object) {
            return true;
        }

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

    public boolean isValid()
    {
        return this.x >= 0 && this.x <= 7 && this.y >= 0 && this.y <= 7;
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
    public boolean onSameFile(Location location)
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


    /**
     * Add a vector to this location
     * @param vector to add
     * @return new location
     */
    public Location add(Vector vector)
    {
        return vector.translocate(this);
    }

    /**
     * Iterate locations from this location to another
     * @param to another location
     * @return iteration of locations in between
     */
    public Iterator<Location> iterateTO(Location to)
    {
        return new LocationIterator(this, to);
    }

    private class LocationIterator implements Iterator<Location>
    {
        private final Vector direction;
        private final Location from;
        private final Location to;
        private Location current;

        public LocationIterator(Location from, Location to)
        {
            this.from = from;
            this.to = to;
            this.direction = Vector.from(to, from).normalize();
            this.current = null;
            assert(direction.norm() > 0);
        }

        @Override
        public boolean hasNext() {
            return (current == null || (!current.equals(to) && !current.add(direction).equals(to)));
        }

        @Override
        public Location next()
        {
            generate();
            return current;
        }

        private void generate() {
            if (current == null)
            {
                current = direction.translocate(from);
            }
            else
            {
                current = current.add(direction);
            }
        }
    }

}

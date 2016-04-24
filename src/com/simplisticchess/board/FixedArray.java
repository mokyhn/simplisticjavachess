/**
 *
 * @author Morten Kühnrich
 */

package com.simplisticchess.board;

public class FixedArray<T> 
{
    private int numberOfElements = 0;
    private final int capacity;
    private final Object[] elements;
    
    public FixedArray(int capacity)
    {
        this.capacity = capacity;
        this.elements = new Object[capacity];
    }
    
    public T get(int i)
    {
        return (T) elements[i];        
    }
    
    public void set(int i, T value)
    {
        elements[i] = value;
    }
    
    public void insert(T value) 
    {
        elements[numberOfElements] = value;
        numberOfElements++;
    }
    
    public void remove(int i)
    {
        numberOfElements--;
        
        if (numberOfElements > 0)
        {
            elements[i] = elements[numberOfElements];
        }
        else
        {
            elements[i] = null;
        }
        
        
    }
    
    public int length()
    {
        return numberOfElements;
    }
        
}

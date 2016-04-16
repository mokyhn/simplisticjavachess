/**
 *
 * @author Morten Kühnrich
 */
package com.simplisticchess.movegenerator;

import java.util.Collection;
import java.util.Iterator;

public class IteratorUtils
{
    public static <T> Iterator<T> compose(final Iterator<T> it1, final Iterator<T> it2) {
        return new Iterator<T>() {

            public boolean hasNext()
            {
                return it1.hasNext() || it2.hasNext();
            }

            public T next()
            {
                return it1.hasNext() ? it1.next() : (it2.hasNext() ? it2.next() : null);                
            }

            public void remove()
            {
                throw new UnsupportedOperationException();
            }
        };
    }
    
    public static <T> Iterator<T> compose(final Collection<Iterator<T>> iterators)
    {
        if (iterators.isEmpty()) 
        {
            return new Iterator<T>()
            {

                public boolean hasNext() { return false; }
                public T next() { return null; }
                public void remove() {}
            };
        }
        else
        {
            return new Iterator<T>()
            {
                Iterator<Iterator<T>> iteratorIterator = iterators.iterator();
                Iterator<T> currentIterator = iteratorIterator.next();

                public boolean hasNext()
                {
                    if (currentIterator.hasNext()) 
                    {
                        return true;
                    }
                    else 
                    {
                        if (iteratorIterator.hasNext())
                        {
                            currentIterator = iteratorIterator.next();
                            return hasNext();
                        }
                        else
                        {
                            return false;
                        }
                    }

                }

                public T next()
                {
                    if (hasNext())
                    {
                        return currentIterator.next();
                    }
                    else
                    {
                        return null;
                    }
                }

                public void remove()
                {                    
                }
            };
        }
    }
    
    public static <T> Iterator<T> buildEmptyIterator()
    {
        return new Iterator<T>() 
        {
            public boolean hasNext()
            {
                return false;
            }

            public T next()
            {
                return null;
            }

            public void remove()
            {            
            }
        };
    }
}

package com.simplisticchess.movegenerator;

import java.util.Iterator;

/**
 *
 * @author Morten Kühnrich
 */
public class IteratorUtils
{
    public static <T> Iterator<T> compose(final Iterator<T> it1, final Iterator<T> it2) {
        return new Iterator<T> () {

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
    
}

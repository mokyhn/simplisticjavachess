/**
 *
 * @author Morten Kühnrich
 */
package com.simplisticjavachess.misc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class IteratorUtils
{
    public static <T> Iterator<T> compose(final Iterator<T> it1, final Iterator<T> it2) {
        return new Iterator<T>() {

            @Override
            public boolean hasNext()
            {
                return it1.hasNext() || it2.hasNext();
            }

            @Override
            public T next()
            {
                return it1.hasNext() ? it1.next() : (it2.hasNext() ? it2.next() : null);                
            }

            @Override
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
            return Collections.emptyIterator();
        }
        else
        {
            return new Iterator<T>()
            {
                final Iterator<Iterator<T>> iteratorIterator = iterators.iterator();
                Iterator<T> currentIterator = iteratorIterator.next();

                @Override
                public boolean hasNext()
                {
                    while (true) {
                        if (currentIterator.hasNext())
                        {
                            return true;
                        }
                        else
                        {
                            if (iteratorIterator.hasNext())
                            {
                                currentIterator = iteratorIterator.next();
                            }
                            else
                            {
                                return false;
                            }
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

                @Override
                public void remove()
                {                    
                }
            };
        }
    }


    public static <T> Iterator<T> compose(Iterator<T>... iterators)
    {
        return compose(Arrays.asList(iterators));
    }

    public static <T> List<T> toList(Iterator<T> elements)
    {
        List<T> result = new ArrayList<>();
        
        while (elements.hasNext())
        {
            result.add(elements.next());
        }
        
        return result;
    }
    
    public static <T> String toString(Iterator<T> elements)
    {
        List<T> list = toList(elements);
        return Arrays.toString(list.toArray());
    }

    public static <T> Stream<T> asStream(Iterator<T> sourceIterator) {
        return asStream(sourceIterator, false);
    }

    public static <T> Stream<T> asStream(Iterator<T> sourceIterator, boolean parallel) {
        Iterable<T> iterable = () -> sourceIterator;
        return StreamSupport.stream(iterable.spliterator(), parallel);
    }
}

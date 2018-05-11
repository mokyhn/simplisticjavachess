package com.simplisticjavachess.misc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
/**
 *
 * @author Morten Kühnrich
 */
public class ImmutableList<T> implements Iterable<T>
{
   ArrayList<T> list;

   public ImmutableList(T... elements)
   {
      list = new ArrayList<>(Arrays.asList(elements));
   }

   public ImmutableList<T> add(T element)
   {
      ImmutableList<T> result = new ImmutableList();
      result.list.add(element);
      result.list.addAll(this.list);
      return result;
   }

   @Override
   public Iterator<T> iterator()
   {
      return list.iterator();
   }
   
   @Override
   public String toString()
   {
       return Arrays.toString(list.toArray());
   }

}

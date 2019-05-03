package com.simplisticjavachess.engine;

import com.simplisticjavachess.move.Move;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
/**
 *
 * @author Morten Kühnrich
 */
public class MoveSequence implements Iterable<Move>
{
   private ArrayList<Move> list;

   public MoveSequence(Move... moves)
   {
      list = new ArrayList<>(Arrays.asList(moves));
   }

   public MoveSequence add(Move move)
   {
      MoveSequence result = new MoveSequence(move);
      result.list.addAll(this.list);
      return result;
   }

   public Move getFirst()
   {
       return list.get(0);
   }
   
   @Override
   public Iterator<Move> iterator()
   {
      return list.iterator();
   }
   
   @Override
   public String toString()
   {
       return Arrays.toString(list.toArray());
   }

}

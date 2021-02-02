package com.simplisticjavachess.move;

import com.simplisticjavachess.position.ChessMover;
import com.simplisticjavachess.position.Mover;
import com.simplisticjavachess.position.IllegalMoveException;
import com.simplisticjavachess.position.Position;
import com.simplisticjavachess.misc.Strings;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author Morten Kühnrich
 */
public class MoveSequence implements Iterable<Move>
{
   private LinkedList<Move> list;

   public MoveSequence(Move... moves)
   {
      list = new LinkedList<>(Arrays.asList(moves));
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

   public static MoveSequence parse(Position position, String moveString)
   {
      Mover mover = new ChessMover();

      if (moveString.isEmpty())
      {
         return new MoveSequence();
      }

      moveString = Strings.trimWhiteSpace(moveString);
      String[] moveStrings = moveString.split(" ");
      MoveSequence result = new MoveSequence();

      for (String s : moveStrings)
      {
         Move move = MoveParser.parse(position, s);
         try
         {
            position = mover.doMove(position, move);
         }
         catch (IllegalMoveException e)
         {
            throw new IllegalArgumentException("Could not perform the illegal move: " + move +
                    " in the sequence of moves: " + Arrays.toString(moveStrings));
         }

         result.list.addLast(move);
      }

      return result;

   }
}

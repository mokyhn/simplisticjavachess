/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplisticjavachess.misc;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Familien Kühnrich
 */
public class ImmutableListTest {
   
   @Test
   public void testAdd()
   {
      ImmutableList<Integer> l1 = new ImmutableList<>(1,2,3);
      ImmutableList<Integer> l2 = l1.add((Integer) 0);

      int j = 0;
      for (Integer i : l2)
      {
         if (i != j) {
            fail();
         }
         j++;
      }
      assertTrue(true);
   }
    
   @Test
   public void testToString()
   {
       assertEquals("[1, 2, 3]", new ImmutableList<>(1,2,3).toString());
   }
}

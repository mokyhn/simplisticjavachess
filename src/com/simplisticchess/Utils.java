package com.simplisticchess;

/**
 *
 * @author Morten Kühnrich
 */

public final class Utils {
 
    /**
     * Replaces sequences of consecutive white spaces with single white spaces
     * @param s Input string
     * @return A trimmed string
     */
    public static String trimWhiteSpace(final String s) {
      String t     = "";
      char c;
      boolean flag = false;
  
      for (int i = 0; i < s.length(); i++) {
       c = s.charAt(i);

       if (c == ' ' && !flag) {
           flag = true;
           t = t + ' ';
       }

       if (c != ' ') {
           flag = false;
           t = t + c;
       }
      }

      return t;
  }
        
}

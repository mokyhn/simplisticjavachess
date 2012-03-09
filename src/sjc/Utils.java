/**
 *
 * @author mku
 * Various simple help functions used in various parts of the chess program
 */
public class Utils {
 
    /**
     * Replaces occurences of sequences of consequtive white spaces with single white spaces
     * @param s Input string
     * @return A trimmed string
     */
    public static String trimWhiteSpace(String s) {
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

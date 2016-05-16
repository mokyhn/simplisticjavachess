/**
 *
 * @author Morten Kühnrich
 */

package com.simplisticjavachess.misc;

public class Strings
{
    public static String trimWhiteSpace(String s)
    {
        String result = "";
        boolean trimming = false;

        for (int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);

            if (c == ' ' && !trimming)
            {
                trimming = true;
                if (i != 0) 
                {
                    result = result + ' ';
                }
            }

            if (c != ' ')
            {
                trimming = false;
                result = result + c;
            }
        }

        return result;
    }    
}

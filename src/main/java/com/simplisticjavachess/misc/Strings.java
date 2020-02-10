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

        //Remove leading and trailing spaces
        s = s.trim();

        //Remove double spaces inside string
        for (char c : s.toCharArray())
        {
            if (c == ' ' && !trimming)
            {
                trimming = true;
                result = result + ' ';
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

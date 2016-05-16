
package com.simplisticchess.move;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PGNParser
{
    
    
    
    public static void parsePGN(String file) throws IOException 
    {
        String contents = readFile(file, StandardCharsets.UTF_8);
        contents = removeBracketedCharacters(contents, '{', '}');
        contents = removeBracketedCharacters(contents, '(', ')');   
        contents = contents.replaceAll("\\$[\\d*]", "");
        
        contents = contents.replaceAll("[\\d+]\\.\\.\\.", "");
        
        System.out.println(contents);
    }
        
    static String readFile(String path, Charset encoding) throws IOException 
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
    
   
    public static String removeBracketedCharacters(String string, char start, char stop)
    {
        String result = "";
        
        int removing = 0;
        
        for (int i = 0; i < string.length(); i++)
        {
            char c = string.charAt(i);
            
            if (c == start)
            {
                removing++;
            }
            else
            if (c == stop)
            {
                removing--;
            } 
            else
            if (removing == 0) 
            {
                result += c;
            } 
        }
        
        return result;
        
    }
    
}

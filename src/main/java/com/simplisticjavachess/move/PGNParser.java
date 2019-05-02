
package com.simplisticjavachess.move;

import com.simplisticjavachess.misc.Strings;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PGNParser
{
    private enum states {
        NOT_PARSING, 
        PARSING_PAWNMOVE,
        PARSING_PAWN_CAPTURES_MOVE}
    
    public static void main(String[] args) throws IOException
    {
        parsePGN("your.pgn");
    }
    
    
    public static void parsePGN(String file) throws IOException 
    {
        String contents = readFile(file, StandardCharsets.UTF_8);
        
        String input = preparePGN(contents);
        
        System.out.println(input);
                
    }
    
    public static String preparePGN(String input) 
    {
        input = removeBracketedCharacters(input, '{', '}');
        input = removeBracketedCharacters(input, '(', ')');   
        input = removeBracketedCharacters(input, '[', ']');
        input = input.replaceAll("\\$[\\d*]", "");  
        input = input.replaceAll("[\\d*]+\\.\\.\\.", ""); // remove continuations for black
        input = input.replaceAll("[\\d*]+\\.", "");    // remove move numbering
        input = input.replaceAll("\\+", "");          // remove checks
        input = input.replaceAll("[\t\n\r]", " ");    // remove tabulation and new line
        input = Strings.trimWhiteSpace(input);
        return input;
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

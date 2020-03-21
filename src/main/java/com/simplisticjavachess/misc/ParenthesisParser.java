package com.simplisticjavachess.misc;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ParenthesisParser
{


    class Token
    {
        int linenumber;
        int characternumber;

        public Token(int linenumber, int characternumber)
        {
            this.linenumber = linenumber;
            this.characternumber = characternumber;
        }

        public int getLinenumber()
        {
            return linenumber;
        }

        public int getCharacternumber()
        {
            return characternumber;
        }

        public String toString()
        {
            return "line " + linenumber + " and position " + characternumber;
        }

    }


    class Result
    {
        int index;
        List<Object> list;

        public Result(int index, List<Object> list)
        {
            this.index = index;
            this.list = list;
        }

        public int getIndex()
        {
            return index;
        }

        public List<Object> getList()
        {
            return list;
        }



    }

    public void checkSyntax(String s)
    {
        Stack<Token> parseStack = new Stack<>();

        int characternumber = 0;
        int linenumber = 1;
        for (char c : s.toCharArray())
        {
            if (c == '\n')
            {
                linenumber++;
                characternumber = 0;
            } else
                {
                    characternumber++;
                }

            if (c == '(')
            {
                parseStack.push(new Token(linenumber, characternumber));
            }

            if (c == ')')
            {
                if (parseStack.empty())
                {
                    throw new IllegalArgumentException("No opening parenthesis found for closing parentheis at line " + linenumber + " and position " + characternumber);
                }
                else
                {
                    Token token = parseStack.pop();
                    System.out.println("Opening at " + token.toString() + " was closed at line: "  + linenumber + " and position " + characternumber);
                }
            }
        }

        if (!parseStack.empty())
        {
            System.out.println(parseStack);
            throw new IllegalArgumentException("Opening parenthesis at: " + parseStack.pop().toString() + " were not closed...");
        }
    }

    /**
     *     a 1 2 (b) (c d) (e f) (g (h i) (j k))
     *
     *
     * @param s
     */
    public Result parse(String s) {

        List<Object> result = new ArrayList<>();

        String collecting = "";

        for (int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);

            if (c == ' ' || c == '(' || c == ')')
            {
                if (!collecting.isEmpty())
                {
                    result.add(collecting);
                    collecting = "";
                }

            }

            if (c == ' ')
            {
                continue;
            }

            if (c == '(')
            {
                Result r = parse(s.substring(i+1));
                result.add(r.list);
                i = 1 + i + r.index;
                continue;
            }

            if (c == ')')
            {
                return new Result(i, result);
            }

            collecting += c;
        }

        return new Result(s.length(), result);
    }

    public static List<Object> traverse(List<Object> l)
    {
        List<Object> prefix   = new ArrayList<>();

        List<Object> result = new ArrayList<>();

        boolean foundVariant = false;
        for (Object o : l)
        {
            if (o instanceof ArrayList)
            {
                List<Object> suffixes = traverse((List<Object>) o);
                for (Object suffix : suffixes)
                {
                    List<Object> newTrack = new ArrayList<>();
                    newTrack.addAll(prefix);
                    newTrack.addAll((List<Object>) suffix);
                    result.add(newTrack);
                    foundVariant = true;
                }
            }
            else
            {
                prefix.add(o);
            }
        }

        if (!foundVariant)
        {
            result.add(prefix);
        }

        return result;
    }

    public List<Object> get(String parenthesis)
    {
        return traverse(parse(parenthesis).getList());
    }

}
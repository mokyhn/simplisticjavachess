package com.simplisticjavachess.misc;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ParenthesisParserTest
{
    @Test
    public void checkBalance1()
    {
        String s = "a 1 2 (b) (c d) (e f) (g (h i) (j k))";
        new ParenthesisParser().checkSyntax(s);
    }

    @Test
    public void checkBalance2()
    {
        new ParenthesisParser().checkSyntax("(( ) () ((())))");
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkBalance3()
    {
        new ParenthesisParser().checkSyntax("( (  )");
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkBalance4()
    {
        new ParenthesisParser().checkSyntax("( ))");
    }

    @Test
    public void parse()
    {
        String s = "a 1 2 (b) (c d) (e f) (g (h i) (j k))";
        ParenthesisParser.Result r = new ParenthesisParser().parse(s);
        assertEquals(7, r.getList().size());
    }

    @Test
    public void traverse()
    {
        String s = "e4 d5 c5 (b) (c d) (e f) (e2e4 (e2e4Q d7d5) (j k)))";
        ParenthesisParser.Result l = new ParenthesisParser().parse(s);
        List<Object> r = new ParenthesisParser().traverse(l.getList());
        assertEquals(5, r.size());
    }
}
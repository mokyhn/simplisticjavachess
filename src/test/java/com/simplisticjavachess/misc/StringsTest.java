package com.simplisticjavachess.misc;

import com.simplisticjavachess.UnitTest;
import org.junit.Assert;
import org.junit.Test;

@UnitTest
public class StringsTest {

    @Test
    public void trimWhiteSpace()
    {
        Assert.assertEquals("", Strings.trimWhiteSpace(""));
        Assert.assertEquals("a b", Strings.trimWhiteSpace(" a b"));
        Assert.assertEquals("a b", Strings.trimWhiteSpace(" a b "));
        Assert.assertEquals("a b", Strings.trimWhiteSpace("a      b"));
        Assert.assertEquals("a b c d e", Strings.trimWhiteSpace("a b  c d      e"));
    }
}
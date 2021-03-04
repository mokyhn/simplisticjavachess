package com.simplisticjavachess.engine;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CacheTest {

    @Test
    public void putAndGetTest() {
        Cache<Integer, String> cache = new Cache<>(3);
        cache.put(1, "alo1");
        cache.put(2, "alo2");
        cache.put(3, "alo3");
        cache.put(4, "alo4");
        assertEquals("alo2", cache.get(2));
        assertEquals("alo3", cache.get(3));
        assertEquals("alo4", cache.get(4));
        assertNull(cache.get(1));
    }
}
package com.simplisticjavachess;

import org.reflections.Reflections;

import java.util.Set;

public class TestHelper {
    public static Set<Class<?>> getTests(Class type) {
        Reflections reflections = new Reflections("com.simplisticjavachess");

        Set<Class<? extends Object>> tmp =
                reflections.getTypesAnnotatedWith(type);

        return tmp;
    }

}

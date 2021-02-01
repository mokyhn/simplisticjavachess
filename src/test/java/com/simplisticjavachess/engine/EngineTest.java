package com.simplisticjavachess.engine;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class EngineTest {

    private final static Collection engines =
            Arrays.asList(new MinMaxEngine(), new AlphaBetaEngine(), new RandomEngine());


    private Engine engine;

    public EngineTest(Engine engine) {
        this.engine = engine;
    }



    @Parameterized.Parameters
    public static Collection get() {
        return engines;
    }

    @Test
    public void testEngine() {
        System.out.println("Now testing " + engine.getClass().toString());
        fail();
    }

    // Engines under test




}
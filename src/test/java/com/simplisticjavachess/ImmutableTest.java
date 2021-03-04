package com.simplisticjavachess;

import org.junit.Test;


import static org.mutabilitydetector.unittesting.MutabilityAssert.assertImmutable;

public class ImmutableTest {

    @Test
    public void checkMyClassIsImmutable() {
        for (Class<?> c : TestHelper.getTests(Immutable.class)) {
            System.out.println("Checking " + c.toString());
            assertImmutable(c);
        }

    }

}

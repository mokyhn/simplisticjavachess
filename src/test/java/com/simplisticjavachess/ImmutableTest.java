package com.simplisticjavachess;

import com.simplisticjavachess.movegenerator.PieceMoveGenerator;
import org.junit.Test;
import org.mutabilitydetector.ConfigurationBuilder;
import org.mutabilitydetector.unittesting.MutabilityAsserter;


import static org.mutabilitydetector.unittesting.MutabilityAssert.assertImmutable;

public class ImmutableTest {

    MutabilityAsserter MUTABILITY = MutabilityAsserter.configured(new ConfigurationBuilder() {
        @Override public void configure() {
            hardcodeAsDefinitelyImmutable(PieceMoveGenerator.class);
        }
    });

    // in a test case



    @Test
    public void checkMyClassIsImmutable() {
        for (Class<?> c : TestHelper.getTests(Immutable.class)) {
            System.out.println("Checking " + c.toString());
            assertImmutable(c);
            //MUTABILITY.assertImmutable(c); // this now passes
        }

    }

}

package com.simplisticjavachess;

import junit.framework.JUnit4TestAdapter;
import junit.framework.TestSuite;

public class IntegrationTestSuite {
    public static TestSuite suite() {
        TestSuite suite = new TestSuite();

        for (Class<?> c : TestHelper.getTests(IntegrationTest.class)) {
            suite.addTest(new JUnit4TestAdapter(c));
        }

        return suite;
    }
}

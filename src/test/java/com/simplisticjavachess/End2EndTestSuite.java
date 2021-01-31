package com.simplisticjavachess;

import junit.framework.JUnit4TestAdapter;
import junit.framework.TestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.AllTests;

@RunWith(AllTests.class)
public class End2EndTestSuite {
    public static TestSuite suite() {
        TestSuite suite = new TestSuite();

        for (Class<?> c : TestHelper.getTests(End2EndTest.class)) {
            suite.addTest(new JUnit4TestAdapter(c));
        }

        return suite;
    }
}

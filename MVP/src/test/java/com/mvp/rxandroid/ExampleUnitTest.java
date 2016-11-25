package com.mvp.rxandroid;

import android.test.InstrumentationTestCase;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {

    @Before
    public void appStart() throws Exception {

    }

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void con() throws Exception {
        assertFalse("result->" + (4 > 2),4 > 2);
    }
}
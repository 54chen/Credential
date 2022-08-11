package com.john.utils;

import junit.framework.TestCase;

import java.util.List;

import com.john.biz.SwearChecker;

import junit.framework.Test;
import junit.framework.TestSuite;


public class SwearCheckerTest extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public SwearCheckerTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( SwearCheckerTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testSwearChecker()
    {
        SwearChecker sc = new SwearChecker();
        List<String> ret = sc.getErrorMessages("fuck", "");
        assertTrue(ret!=null);

        ret = sc.getErrorMessages("asshole", "");
        assertTrue(ret!=null);

        ret = sc.getErrorMessages("assh0le", "");
        assertTrue(ret!=null);

        ret = sc.getErrorMessages("assh01e", "");
        assertTrue(ret!=null);

        ret = sc.getErrorMessages("assho1e", "");
        assertTrue(ret!=null);

        ret = sc.getErrorMessages("asshole", "");
        assertTrue(ret!=null);

        ret = sc.getErrorMessages("f4ck", "");
        assertTrue(ret!=null);

        ret = sc.getErrorMessages("b1tch", "");
        assertTrue(ret!=null);
       
    }
}

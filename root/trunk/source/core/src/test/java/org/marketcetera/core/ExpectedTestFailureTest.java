package org.marketcetera.core;

import junit.framework.Test;
import junit.framework.TestCase;

/**
 * @author Toli Kuznets
 * @version $Id$
 */
@ClassVersion("$Id$")
public class ExpectedTestFailureTest extends TestCase
{
    public ExpectedTestFailureTest(String inName)
    {
        super(inName);
    }

    public static Test suite()
    {
        // run the test repeatedly since we can have a race condition
        return new MarketceteraTestSuite(ExpectedTestFailureTest.class);
    }

    public void testClassSpecified()
    {
        final RuntimeException ex = new RuntimeException();
        assertEquals(ex, (new ExpectedTestFailure(RuntimeException.class) {
            protected void execute() throws Throwable
            {
                throw ex;
            }
        }).run());
    }

    public void testMatchSpecified()
    {
        final RuntimeException rex = new RuntimeException("toli was here");
        assertEquals(rex, (new ExpectedTestFailure(RuntimeException.class, "toli") {
                protected void execute() throws Throwable
                {
                    throw rex;
                }
            }).run());
        final IllegalArgumentException ex = new IllegalArgumentException("toli was here");
        assertEquals(ex, (new ExpectedTestFailure(IllegalArgumentException.class, "was") {
                protected void execute() throws Throwable
                {
                    throw ex;
                }
            }).run());


    }

}

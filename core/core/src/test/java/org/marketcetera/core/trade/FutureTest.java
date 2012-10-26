package org.marketcetera.core.trade;

import java.util.List;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.marketcetera.core.ExpectedFailure;
import org.marketcetera.core.trade.impl.FutureImpl;

import quickfix.field.MaturityMonthYear;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/* $License$ */

/**
 * Tests {@link org.marketcetera.core.trade.impl.FutureImpl}.
 * 
 * @version $Id: FutureTest.java 16063 2012-01-31 18:21:55Z colin $
 * @since 2.1.0
 */
public class FutureTest
        extends InstrumentTestBase<FutureImpl>
{
    @Override
    protected FutureImpl createFixture() {
        return new FutureImpl("ABC", FutureExpirationMonth.JULY, 18);
    }

    @Override
    protected FutureImpl createEqualFixture() {
        return new FutureImpl("ABC", FutureExpirationMonth.JULY, 18);
    }

    @Override
    protected List<FutureImpl> createDifferentFixtures() {
        return ImmutableList.of(
        new FutureImpl("ABC", FutureExpirationMonth.JULY, 19),
        new FutureImpl("ABC", FutureExpirationMonth.JUNE, 18),
        new FutureImpl("METC", FutureExpirationMonth.JULY, 18));
    }

    @Override
    protected SecurityType getSecurityType() {
        return SecurityType.Future;
    }

    @Test
    public void testNullSymbol() throws Exception {
        new ExpectedFailure<IllegalArgumentException>(Messages.NULL_SYMBOL.getText()) {
            @Override
            protected void run() throws Exception {
                new FutureImpl(null, FutureExpirationMonth.JULY, 18);
            }
        };
        new ExpectedFailure<IllegalArgumentException>(Messages.NULL_SYMBOL.getText()) {
            @Override
            protected void run() throws Exception {
                new FutureImpl(null, "201010");
            }
        };
    }
    /**
     * Tests the ability to parse symbols with white space.
     *
     * @throws Exception if an unexpected error occurs
     */
    @Test
    public void testWhitespaceSymbol()
            throws Exception
    {
        new ExpectedFailure<IllegalArgumentException>(Messages.NULL_SYMBOL.getText()) {
            @Override
            protected void run() throws Exception {
                new FutureImpl("",FutureExpirationMonth.JULY, 18);
            }
        };
        new ExpectedFailure<IllegalArgumentException>(Messages.NULL_SYMBOL.getText()) {
            @Override
            protected void run() throws Exception {
                new FutureImpl("   ",FutureExpirationMonth.JULY, 18);
            }
        };
        new ExpectedFailure<IllegalArgumentException>(Messages.NULL_SYMBOL.getText()) {
            @Override
            protected void run() throws Exception {
                new FutureImpl("   ", "201012");
            }
        };
    }
    /**
     * Tests null expiration month values. 
     *
     * @throws Exception if an unexpected error occurs
     */
    @Test
    public void testNullExpirationMonth()
            throws Exception
    {
        new ExpectedFailure<IllegalArgumentException>(Messages.NULL_MONTH.getText()) {
            @Override
            protected void run() throws Exception {
                new FutureImpl("ABC", null, 18);
            }
        };
    }
    /**
     * Tests invalid expiration years. 
     *
     * @throws Exception if an unexpected error occurs
     */
    @Test
    public void testInvalidExpirationYear()
            throws Exception
    {
        new ExpectedFailure<IllegalArgumentException>(Messages.INVALID_YEAR.getText(-1)) {
            @Override
            protected void run() throws Exception {
                new FutureImpl("ABC",
                           FutureExpirationMonth.JULY,
                           -1);
            }
        };
        new ExpectedFailure<IllegalArgumentException>(Messages.INVALID_YEAR.getText(0)) {
            @Override
            protected void run() throws Exception {
                new FutureImpl("ABC",
                           FutureExpirationMonth.JULY,
                           0);
            }
        };
    }
    /**
     * Tests invalid expiration month values.
     *
     * @throws Exception if an unexpected error occurs
     */
    @Test
    public void testInvalidExpirationMonth()
            throws Exception
    {
        new ExpectedFailure<IllegalArgumentException>(Messages.INVALID_EXPIRY.getText("201000")) {
            @Override
            protected void run()
                    throws Exception
            {
                new FutureImpl("ABC",
                           "201000");
            }
        };
        new ExpectedFailure<IllegalArgumentException>(Messages.INVALID_EXPIRY.getText("201013")) {
            @Override
            protected void run()
                    throws Exception
            {
                new FutureImpl("ABC",
                           "201013");
            }
        };
    }
    /**
     * Tests invalid expiration day values.
     *
     * @throws Exception if an unexpected error occurs
     */
    @Test
    public void testInvalidExpirationDay()
            throws Exception
    {
        new ExpectedFailure<IllegalArgumentException>(Messages.INVALID_EXPIRY.getText("20100100")) {
            @Override
            protected void run()
                    throws Exception
            {
                new FutureImpl("ABC",
                           "20100100");
            }
        };
        new ExpectedFailure<IllegalArgumentException>(Messages.INVALID_EXPIRY.getText("20101232")) {
            @Override
            protected void run()
                    throws Exception
            {
                new FutureImpl("ABC",
                           "20101232");
            }
        };
    }
    @Test
    public void testInvalidExpiry() throws Exception {
        new ExpectedFailure<IllegalArgumentException>(Messages.NULL_EXPIRY.getText()) {
            @Override
            protected void run() throws Exception {
                new FutureImpl("ABC",
                           null);
            }
        };
        new ExpectedFailure<IllegalArgumentException>(Messages.NULL_EXPIRY.getText()) {
            @Override
            protected void run() throws Exception {
                new FutureImpl("ABC",
                           "    ");
            }
        };
        new ExpectedFailure<IllegalArgumentException>(Messages.INVALID_EXPIRY.getText("YYYYMM")) {
            @Override
            protected void run() throws Exception {
                new FutureImpl("ABC",
                           "YYYYMM");
            }
        };
        new ExpectedFailure<IllegalArgumentException>(Messages.INVALID_EXPIRY.getText("000000")) {
            @Override
            protected void run() throws Exception {
                new FutureImpl("ABC",
                           "000000");
            }
        };
    }
    /**
     * Tests the ability to parse expiries in {@link FutureImpl#Future(String, String)}.
     *
     * @throws Exception if an unexpected error occurs
     */
    @Test
    public void testExpiryParsing()
            throws Exception
    {
        verifyFuture(new FutureImpl("ABC",
                                "000101"),
                     "ABC",
                     FutureExpirationMonth.JANUARY,
                     2001,
                     -1);
        verifyFuture(new FutureImpl("ABC",
                                "010012"),
                     "ABC",
                     FutureExpirationMonth.DECEMBER,
                     100,
                     -1);
        verifyFuture(new FutureImpl("ABC",
                                "009912"),
                     "ABC",
                     FutureExpirationMonth.DECEMBER,
                     2099,
                     -1);
        for(FutureExpirationMonth month : FutureExpirationMonth.values()) {
            verifyFuture(new FutureImpl("ABC",
                                    "2010" + month.getMonthOfYear()),
                         "ABC",
                         month,
                         2010,
                         -1);
        }
        verifyFuture(new FutureImpl("ABC",
                                "20100107"),
                     "ABC",
                     FutureExpirationMonth.JANUARY,
                     2010,
                     7);
    }
    /**
     * Tests ability to parse future instruments from strings.
     *
     * @throws Exception if an unexpected error occurs
     */
    @Test
    public void testFromString()
            throws Exception
    {
        new ExpectedFailure<IllegalArgumentException>(Messages.NULL_SYMBOL.getText()) {
            @Override
            protected void run()
                    throws Exception
            {
                FutureImpl.fromString(null);
            }
        };
        new ExpectedFailure<IllegalArgumentException>(Messages.NULL_SYMBOL.getText()) {
            @Override
            protected void run()
                    throws Exception
            {
                FutureImpl.fromString("");
            }
        };
        new ExpectedFailure<IllegalArgumentException>(Messages.NULL_SYMBOL.getText()) {
            @Override
            protected void run()
                    throws Exception
            {
                FutureImpl.fromString("    ");
            }
        };
        new ExpectedFailure<IllegalArgumentException>(Messages.INVALID_SYMBOL.getText("x")) {
            @Override
            protected void run()
                    throws Exception
            {
                FutureImpl.fromString("x");
            }
        };
        new ExpectedFailure<IllegalArgumentException>(Messages.INVALID_EXPIRY.getText("201113")) {
            @Override
            protected void run()
                    throws Exception
            {
                FutureImpl.fromString("x-201113");
            }
        };
        verifyFuture(FutureImpl.fromString("SYMBOL-WITH-MULTIPLE_PARTS-IN-IT-201010"),
                     "SYMBOL-WITH-MULTIPLE_PARTS-IN-IT",
                     FutureExpirationMonth.OCTOBER,
                     2010,
                     -1);
        verifyFuture(FutureImpl.fromString("SYMBOL-20150829"),
                     "SYMBOL",
                     FutureExpirationMonth.AUGUST,
                     2015,
                     29);
    }
    @Test
    public void testToString() throws Exception {
        assertThat(
                createFixture().toString(),
                is("Future ABC [JULY(N) 2018]"));
        assertThat(
                new FutureImpl("ABC", FutureExpirationMonth.JULY, 18).toString(),
                is("Future ABC [JULY(N) 2018]"));
        assertThat(
                new FutureImpl("ABC",
                           "201010").toString(),
                is("Future ABC [OCTOBER(V) 2010]"));
        assertThat(new FutureImpl("ABC",
                              "20101014").toString(),
                   is("Future ABC [14 OCTOBER(V) 2010]"));
    }
    /**
     * Verifies that the given actual <code>Future</code> contains the given expected attributes.
     *
     * @param inActualInstrument a <code>Future</code> value
     * @param inExpectedSymbol a <code>String</code> value
     * @param inExpectedMonth a <code>FutureExpirationMonth</code> value
     * @param inExpectedYear an <code>int</code> value
     * @param inExpectedDay an <code>int</code> value
     * @throws Exception if an unexpected error occurs
     */
    private void verifyFuture(FutureImpl inActualInstrument,
                              String inExpectedSymbol,
                              FutureExpirationMonth inExpectedMonth,
                              int inExpectedYear,
                              int inExpectedDay)
            throws Exception
    {
        assertNotNull(inActualInstrument.toString());
        String expectedExpiry;
        if(inActualInstrument.getExpirationDay() == -1) {
            expectedExpiry = String.format("%1$4d%2$s",
                                           inExpectedYear,
                                           inExpectedMonth.getMonthOfYear());
        } else {
            expectedExpiry = String.format("%1$4d%2$s%3$2d",
                                           inExpectedYear,
                                           inExpectedMonth.getMonthOfYear(),
                                           inExpectedDay);
        }
        assertEquals(inExpectedDay,
                     inActualInstrument.getExpirationDay());
        assertEquals(inExpectedMonth,
                     inActualInstrument.getExpirationMonth());
        assertEquals(inExpectedYear,
                     inActualInstrument.getExpirationYear());
        MaturityMonthYear expectedMaturityMonthYear = new MaturityMonthYear(String.format("%1$4d%2$s",
                                                                                          inExpectedYear,
                                                                                          inExpectedMonth.getMonthOfYear()));
        assertEquals(expectedMaturityMonthYear,
                     inActualInstrument.getExpiryAsMaturityMonthYear());
        assertEquals(expectedExpiry,
                     inActualInstrument.getExpiryAsString());
        assertEquals(String.format("%s-%s",
                                   inExpectedSymbol,
                                   expectedExpiry),
                     inActualInstrument.getFullSymbol());
        assertEquals(SecurityType.Future,
                     inActualInstrument.getSecurityType());
        assertEquals(inExpectedSymbol,
                     inActualInstrument.getSymbol());
    }
}
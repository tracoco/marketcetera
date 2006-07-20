package org.marketcetera.core;


import junit.framework.TestCase;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * @author Graham Miller
 */
public class BigDecimalUtilsTest extends TestCase {

    /*
      * Test method for 'org.marketcetera.core.BigDecimalUtils.add(double, double)'
      */
    public void testAddDoubleDouble() {
        assertEquals((double)123.0+87.5, BigDecimalUtils.add((double)123.0, (double)87.5).doubleValue(), .000001);
        assertEquals((double)-123.0+87.5, BigDecimalUtils.add((double)-123.0, (double)87.5).doubleValue(), .000001);
        assertEquals((double)8888.0+.0001, BigDecimalUtils.add((double)8888.0, (double).0001).doubleValue(), .000001);
        assertEquals((double)92384.5+0, BigDecimalUtils.add((double)92384.5, (double)0).doubleValue(), .000001);
    }

    /*
      * Test method for 'org.marketcetera.core.BigDecimalUtils.add(BigDecimal, double)'
      */
    public void testAddBigDecimalDouble() {
        assertEquals((double)123.0+87.5, BigDecimalUtils.add(new BigDecimal(123.0), (double)87.5).doubleValue(), .000001);
        assertEquals((double)-123.0+87.5, BigDecimalUtils.add(new BigDecimal(-123.0), (double)87.5).doubleValue(), .000001);
        assertEquals((double)8888.0+.0001, BigDecimalUtils.add(new BigDecimal(8888.0), (double).0001).doubleValue(), .000001);
        assertEquals((double)92384.5+0, BigDecimalUtils.add(new BigDecimal(92384.5), (double)0).doubleValue(), .000001);
    }

    /*
      * Test method for 'org.marketcetera.core.BigDecimalUtils.add(BigDecimal, BigDecimal)'
      */
    public void testAddBigDecimalBigDecimal() {
        assertEquals((double)123.0+87.5, BigDecimalUtils.add(new BigDecimal(123.0), new BigDecimal(87.5)).doubleValue(), .000001);
        assertEquals((double)-123.0+87.5, BigDecimalUtils.add(new BigDecimal(-123.0), new BigDecimal(87.5)).doubleValue(), .000001);
        assertEquals((double)8888.0+.0001, BigDecimalUtils.add(new BigDecimal(8888.0), new BigDecimal(.0001)).doubleValue(), .000001);
        assertEquals((double)92384.5+0, BigDecimalUtils.add(new BigDecimal(92384.5), new BigDecimal(0)).doubleValue(), .000001);
    }

    /*
      * Test method for 'org.marketcetera.core.BigDecimalUtils.multiply(double, double)'
      */
    public void testMultiplyDoubleDouble() {
        assertEquals((double)123.0*87.5, BigDecimalUtils.multiply((double)123.0, (double)87.5).doubleValue(), .000001);
        assertEquals((double)-123.0*87.5, BigDecimalUtils.multiply((double)-123.0, (double)87.5).doubleValue(), .000001);
        assertEquals((double)8888.0*.0001, BigDecimalUtils.multiply((double)8888.0, (double).0001).doubleValue(), .000001);
        assertEquals((double)92384.5*0, BigDecimalUtils.multiply((double)92384.5, (double)0).doubleValue(), .000001);
    }

    /*
      * Test method for 'org.marketcetera.core.BigDecimalUtils.multiply(BigDecimal, double)'
      */
    public void testMultiplyBigDecimalDouble() {
        assertEquals((double)123.0*87.5, BigDecimalUtils.multiply(new BigDecimal(123.0), (double)87.5).doubleValue(), .000001);
        assertEquals((double)-123.0*87.5, BigDecimalUtils.multiply(new BigDecimal(-123.0), (double)87.5).doubleValue(), .000001);
        assertEquals((double)8888.0*.0001, BigDecimalUtils.multiply(new BigDecimal(8888.0), (double).0001).doubleValue(), .000001);
        assertEquals((double)92384.5*0, BigDecimalUtils.multiply(new BigDecimal(92384.5), (double)0).doubleValue(), .000001);
    }

    /*
      * Test method for 'org.marketcetera.core.BigDecimalUtils.multiply(BigDecimal, BigDecimal)'
      */
    public void testMultiplyBigDecimalBigDecimal() {
        assertEquals((double)123.0*87.5, BigDecimalUtils.multiply(new BigDecimal(123.0), new BigDecimal(87.5)).doubleValue(), .000001);
        assertEquals((double)-123.0*87.5, BigDecimalUtils.multiply(new BigDecimal(-123.0), new BigDecimal(87.5)).doubleValue(), .000001);
        assertEquals((double)8888.0*.0001, BigDecimalUtils.multiply(new BigDecimal(8888.0), new BigDecimal(.0001)).doubleValue(), .000001);
        assertEquals((double)92384.5*0, BigDecimalUtils.multiply(new BigDecimal(92384.5), new BigDecimal(0)).doubleValue(), .000001);
    }

    /*
      * Test method for 'org.marketcetera.core.BigDecimalUtils.divide(double, double)'
      */
    public void testDivideDoubleDouble() {
        assertEquals((double)123.0/87.5, BigDecimalUtils.divide((double)123.0, (double)87.5).doubleValue(), .000001);
        assertEquals((double)-123.0/87.5, BigDecimalUtils.divide((double)-123.0, (double)87.5).doubleValue(), .000001);
        assertEquals((double)8888.0/.0001, BigDecimalUtils.divide((double)8888.0, (double)0.0001).doubleValue(), .000001);
        try {
            BigDecimalUtils.divide((double)92384.5, (double)0).doubleValue();
            assertTrue("ArithmeticException expected", false);
        } catch (ArithmeticException e) {
        }
    }

    /*
      * Test method for 'org.marketcetera.core.BigDecimalUtils.multiply(BigDecimal, double)'
      */
    public void testDivideBigDecimalDouble() {
        assertEquals((double)123.0/87.5, BigDecimalUtils.divide(new BigDecimal(123.0), (double)87.5).doubleValue(), .000001);
        assertEquals((double)-123.0/87.5, BigDecimalUtils.divide(new BigDecimal(-123.0), (double)87.5).doubleValue(), .000001);
        assertEquals((double)8888.0/.0001, BigDecimalUtils.divide(new BigDecimal(8888.0), (double)0.0001).doubleValue(), .000001);
        try {
            BigDecimalUtils.divide(new BigDecimal(92384.5), (double)0).doubleValue();
            assertTrue("ArithmeticException expected", false);
        } catch (ArithmeticException e) {
        }
    }

    /*
      * Test method for 'org.marketcetera.core.BigDecimalUtils.multiply(BigDecimal, BigDecimal)'
      */
    public void testDivideBigDecimalBigDecimal() {
        assertEquals((double)123.0/87.5, BigDecimalUtils.divide(new BigDecimal(123.0), new BigDecimal(87.5)).doubleValue(), .000001);
        assertEquals((double)-123.0/87.5, BigDecimalUtils.divide(new BigDecimal(-123.0), new BigDecimal(87.5)).doubleValue(), .000001);
        assertEquals((double)8888.0/.0001, BigDecimalUtils.divide(new BigDecimal(8888.0), new BigDecimal(0.0001)).doubleValue(), .000001);
        try {
            BigDecimalUtils.divide(new BigDecimal(92384.5), new BigDecimal(0)).doubleValue();
            assertTrue("ArithmeticException expected", false);
        } catch (ArithmeticException e) {
        }
    }

    public void testBigDecimalAssumptions() throws Exception {
        // This is what round and scale do:

        // Round is used to set the total number of significant digits.  So even though we
        // use RoundingMode.HALF_UP on 10.5, its rounding to 1 significant digit, so it
        // rounds to 10.
        assertEquals(new BigDecimal("10").toPlainString(), new BigDecimal("10.5").round(new MathContext(1, RoundingMode.HALF_UP)).toPlainString());

        // Set scale sets the scale (the number of digits to the right of the
        // decimal place (this is often what we mean by "round").
        assertEquals(new BigDecimal("11").toPlainString(), new BigDecimal("10.5").setScale(0, RoundingMode.HALF_UP).toPlainString());
        assertEquals(new BigDecimal("10").toPlainString(), new BigDecimal("10.5").setScale(0, RoundingMode.HALF_DOWN).toPlainString());
    }
}

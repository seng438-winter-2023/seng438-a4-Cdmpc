package org.jfree.data;

import static org.junit.Assert.*;
import org.jmock.*;
import org.junit.*;
import java.util.*;

public class DataUtilitiesTest {
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    /* Mocking the interfaces */
    static Values2D values;
    static KeyedValues kv;

    /* Mocking object */
    static Mockery mockO;

    @Before
    public void setUp() throws Exception {
        mockO = new Mockery();
        values = mockO.mock(Values2D.class);
        kv = mockO.mock(KeyedValues.class);
    }

    /*--------------------------------------------------------------------------------------------------------------------------*/
    @Test
    public void testColumnSumValid() {
        // setup
        mockO.checking(new Expectations() {
            {
                one(values).getRowCount();
                will(returnValue(2));
                one(values).getValue(0, 0);
                will(returnValue(7.5));
                one(values).getValue(1, 0);
                will(returnValue(2.5));
            }
        });
        double result = DataUtilities.calculateColumnTotal(values, 0);
        // verify
        assertEquals(result, 10.0, .000000001d);
        // tear-down: NONE in this test method
    }

    @Test
    public void testColumnCount() {
        // setup
        mockO.checking(new Expectations() {
            {
                oneOf(values).getRowCount();
                will(returnValue(2));
                oneOf(values).getValue(0, 0);
                will(returnValue(7.5));
                oneOf(values).getValue(1, 0);
                will(returnValue(2.5));
            } 
        });
        double result = DataUtilities.calculateColumnTotal(values, 0);
        // verify
        assertNotSame("The sum was correct ", 12.0, result);
        // tear-down: NONE in this test method
    }

    /**
     * ---------------- TESTING ROW CALCULATION
     * ------------------------------------------------------
     **/
    @Test
    public void testRowSum() /** DEFECTIVE **/
    {
        mockO.checking(new Expectations() {

            {
                oneOf(values).getColumnCount();
                will(returnValue(3));
                oneOf(values).getValue(0, 0);
                will(returnValue(4.5));
                oneOf(values).getValue(0, 1);
                will(returnValue(7.8));
                oneOf(values).getValue(0, 2);
                will(returnValue(10.123));
            }
        });
        double rowSum = DataUtilities.calculateRowTotal(values, 0);
        assertEquals("Sum is not correct ", 22.423, rowSum, .00000001d);
    }

    /**
     * ------------------ TESTING createNumberArray(double[] data)
     * -------------------------------------
     **/
    @Test
    public void testCreateNumArrayExists() {
        double[] data = { 1.0, 4.5, 12.3456, 7.890 };
        Number[] numsData = DataUtilities.createNumberArray(data);
        List<Number> numsArr = Arrays.asList(numsData);
        assertEquals("Value was not found ", true, numsArr.contains(12.3456));
    }

    @Test
    public void testCreateNumArrayNotExists() {
        double[] data = { 1.0, 4.5, 12.3456, 7.890 };
        Number[] numsData = DataUtilities.createNumberArray(data);
        List<Number> numsArr = Arrays.asList(numsData);
        assertEquals("Value was found ", false, numsArr.contains(1));
    }

    /**
     * ------------------ TESTING createNumberArray2D(double[][] data)
     * -------------------------------------
     **/
    @Test 
    public void testCreateNumArray2DExists() /** DEFECTIVE **/
    {
        double[][] data = { { 1.0, 2.01 }, { 5.60, 7.1234 }, { 1.11, 8.90 } };
        Number[][] numsData = DataUtilities.createNumberArray2D(data);
        assertEquals("Value was not found ", 2.01, numsData[0][1]);
    }

    @Test
    public void testCreateNumArray2DNotExists() {
        double[][] data = { { 1.0, 2.01 }, { 5.60, 7.1234 }, { 1.11, 8.90 } };
        Number[][] numsData = DataUtilities.createNumberArray2D(data);
        assertEquals("Value was not found ", false, numsData[0][0].equals(3.0));
    }

    /**
     * ------------------ TESTING getCulmulativePercentages
     * -------------------------------------
     **/
    @Test
    public void testCheckItemCountEmpty() {
        mockO.checking(new Expectations() {
            {
                oneOf(kv).getItemCount();
            }
        });
        assertEquals("Something exists that shouldn't", 0, kv.getItemCount());
    }

    @Test
    public void testPercentage() /** DEFECTIVE **/
    {
        mockO.checking(new Expectations() {
            {
                /* BUILDS THE TABLE */
                allowing(kv).getItemCount();
                will(returnValue(4));

                allowing(kv).getKey(0);
                will(returnValue(0));
                allowing(kv).getKey(1);
                will(returnValue(1));
                allowing(kv).getKey(2);
                will(returnValue(2));
                allowing(kv).getKey(3);
                will(returnValue(3));

                allowing(kv).getValue(0);
                will(returnValue(5));
                allowing(kv).getValue(1);
                will(returnValue(10));
                allowing(kv).getValue(2);
                will(returnValue(15));
                allowing(kv).getValue(3);
                will(returnValue(20));

            }
        });

        KeyedValues result = DataUtilities.getCumulativePercentages(kv);
        assertEquals(0.6, result.getValue(2));
    }

    @Test
    public void testPercentageZero() {
        mockO.checking(new Expectations() {
            {
                /* BUILDS THE TABLE */
                allowing(kv).getItemCount();
                will(returnValue(4));

                allowing(kv).getKey(0);
                will(returnValue(0));
                allowing(kv).getKey(1);
                will(returnValue(1));
                allowing(kv).getKey(2);
                will(returnValue(2));
                allowing(kv).getKey(3);
                will(returnValue(3));

                allowing(kv).getValue(0);
                will(returnValue(0));
                allowing(kv).getValue(1);
                will(returnValue(0));
                allowing(kv).getValue(2);
                will(returnValue(15));
                allowing(kv).getValue(3);
                will(returnValue(20));

            }
        });

        KeyedValues result = DataUtilities.getCumulativePercentages(kv);
        assertEquals(0.0, result.getValue(1));
    }
    @Test
    public void testNotEqual()
    {
    	double[][] a = {{1, 2, 3},{1, 2 , 3},{1, 2, 3}};
    	double[][] b = {{1.3, 2, 3},{1, 2 , 3},{1, 2, 3}};
        assertEquals("Arrays are not equal ", false, DataUtilities.equal(a, b));

    	
    } 
    @Test
    public void testNotEqualDimension()
    {
    	double[][] a = {{1, 2, 3},{1, 2 , 3},{1, 2, 3}};
    	double[][] b = {{1, 2 , 3},{1, 2, 3}};
        assertEquals("Arrays are not equal ", false, DataUtilities.equal(a, b));

    	
    } 
    @Test
    public void testNotEqualBNull()
    {
    	double[][] a = {{1, 2, 3},{1, 2 , 3},{1, 2, 3}};
    	double[][] b = null;
        assertEquals("Arrays are not equal ", false, DataUtilities.equal(a, b));

    	
    } 
    @Test
    public void testNotEqualANull()
    {
    	double[][] a = null;
    	double[][] b = {{1.3, 2, 3},{1, 2 , 3},{1, 2, 3}};
        assertEquals("Arrays are not equal ", false, DataUtilities.equal(a, b));

    	
    } 
    @Test
    public void testEqual()
    {
    	double[][] a = {{1.3, 2, 3},{1, 2 , 3},{1, 2, 3}};
    	double[][] b = {{1.3, 2, 3},{1, 2 , 3},{1, 2, 3}};
        assertEquals("Arrays are equal ", true, DataUtilities.equal(a, b));

    	
    }
    @Test 
    public void TestCalculateRowTotal()
    {
        mockO.checking(new Expectations() {
            {
                oneOf(values).getColumnCount();
                will(returnValue(2));
                oneOf(values).getValue(0, 0);
                will(returnValue(7.5));
                oneOf(values).getValue(1, 0);
                will(returnValue(2.5));
                oneOf(values).getValue(2, 0);
                will(returnValue(3.0));
                oneOf(values).getValue(0, 1);
                will(returnValue(1));
                oneOf(values).getValue(1, 1);
                will(returnValue(1)); 
                oneOf(values).getValue(2, 1);
                will(returnValue(1));
            }
        });
        int[] array = {0, 1, 2};
        double result = DataUtilities.calculateRowTotal(values, 2, array);
        assertNotSame("The sum was correct ", 4.0, result);

    } 
    @Test
    public void testClone()
    {
    	double[][] array = {{1, 3, 4 ,5}, {1, 6, 4 ,9}, {1, 3, 4 ,5}};
    	
    	double[][] copyarray = DataUtilities.clone(array);
        assertEquals("Clone is equal ", true, DataUtilities.equal(array, copyarray));

    }
    @Test
    public void testCalculateColumnTotal()
    {
        mockO.checking(new Expectations() {
            {
                oneOf(values).getRowCount();
                will(returnValue(2));
                oneOf(values).getValue(0, 0);
                will(returnValue(7.5));
                oneOf(values).getValue(1, 0);
                will(returnValue(2.5));
                oneOf(values).getValue(2, 0);
                will(returnValue(3.0));
                oneOf(values).getValue(0, 1);
                will(returnValue(1));
                oneOf(values).getValue(1, 1);
                will(returnValue(1)); 
                oneOf(values).getValue(2, 1);
                will(returnValue(1));
            }
        });
        int[] array = {0, 1, 2};
        double result = DataUtilities.calculateColumnTotal(values, 1, array);
        assertNotSame("The sum was correct ", 3, result); 

    }
    
    /*--------------------------------------------------------------------------------------------------------------------------*/
    /* After, destroys the object, Java has automatic garbage collection. */
    @After
    public void tearDown() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }
}

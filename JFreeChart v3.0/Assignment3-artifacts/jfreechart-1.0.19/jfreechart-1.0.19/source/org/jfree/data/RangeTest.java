package org.jfree.data;

import static org.junit.Assert.*; import org.junit.*;

public class RangeTest 
{
	private Range exampleRange1;
    private Range exampleRange2;
    private Range exampleRange3;
    @BeforeClass public static void setUpBeforeClass() throws Exception {
    }

    /*Before, creates the object with some example values.*/
    @Before
    public void setUp() throws Exception { exampleRange1 = new Range(-100, 100); 
    	exampleRange2 = new Range(-450, 25);
    }
    
    /** TEST CASE FOR ILLEGAL ARGUEMENT ON THE RANGE CONSTRUCTOR **/
    @Test(expected = IllegalArgumentException.class)
    public void testRangeConstructorWithInvalidArgs() 
    {
    	exampleRange3 = new Range(400, 100);
    }

    /*Test, tests methods for the object, with one assert statement per stub.*/
    @Test
    public void centralValueShouldBeZero() {
        assertEquals("The central value of -100 and 100 should be 0", 0, exampleRange1.getCentralValue(), .000000001d);
    }
/*----------------------------------------------------------------------------------------------------------------------*/
    /*contains() method testing.*/
    @Test
    public void testContainLower()
    {
        assertEquals("Lower bound of -100 should be in range", true, exampleRange1.contains(-100));
    }
    @Test 
    public void testContainUpper() 
    {
    	assertEquals("Upper bound 100 should be in range", true, exampleRange1.contains(100));
    }
    @Test
    public void testContainBLB() 
    {
    	int BLB = -500;
    	assertEquals("Value of " + BLB + " should not be in the range", false, exampleRange1.contains(BLB));
    }
    @Test
    public void testContainBUB() 
    {
    	int AUB = 1000;
    	assertEquals("Value of " + AUB + " should not be in the range", false, exampleRange1.contains(AUB));
    }
    @Test
    public void testContainNom() 
    {
    	assertEquals("Value of 56 should be present in the range", true, exampleRange1.contains(56));
    }
    /** ----------------------- TEST GET LOWER BOUND -------------------------- **/
    @Test
    public void testLowerBoundValid() 
    {
    	/** lower <= upper **/
    	exampleRange3 = new Range(100, 500);
    	assertEquals("Lower bound value incorrect ", 100, exampleRange3.getLowerBound(), .000000001d);
    }
    
    /*----------------------- TESTS FOR COMBINE(Range r1, Range r2) ---------------------------------*/
    @Test
    public void testCombineValidMin() 
    {
    	exampleRange3 = Range.combine(exampleRange1, exampleRange2);
    	assertEquals("Min value is incorrect ", -450, exampleRange3.getLowerBound(), .000000001d);
    }
    @Test /*FAILED*/
    public void testCombineValidMax() 
    {
    	exampleRange3 = Range.combine(exampleRange1, exampleRange2);
    	assertEquals("Max value is incorrect ", 100, exampleRange3.getUpperBound(), .000000001d);
    }
    @Test/*FAILED*/
    public void testCombineNom() 
    {
    	exampleRange3 = Range.combine(exampleRange1, exampleRange2);
    	assertEquals("Value should exist in range ", true, exampleRange3.contains(25));
    }
    @Test
    public void testCombineOutofBounds() 
    {
    	exampleRange3 = Range.combine(exampleRange1, exampleRange2);
    	assertEquals("Value should not exist in range ", false, exampleRange3.contains(-500));
    }
    @Test //Should return the existing range, when one is null
    public void testCombineOneNull() 
    {
    	exampleRange1 = null;
    	exampleRange3 = Range.combine(exampleRange1, exampleRange2);
    	assertEquals("New Range returned wrong value ", exampleRange2, exampleRange3);
    }
    @Test
    public void testCombineBothNull() 
    {
    	exampleRange1 = null;
    	exampleRange2 = null;
    	exampleRange3 = Range.combine(exampleRange1, exampleRange2);
    	assertEquals("New Range returned a value when it should not have ", null, exampleRange3);
    }
    @Test
    public void testRange2Null() 
    {
    	exampleRange2 = null;
    	exampleRange3 = Range.combine(exampleRange1, exampleRange2);
    	assertEquals("Range1 was not returned ", exampleRange1, exampleRange3);
    }
    /*----------------------- TESTS FOR expandToInclude(Range r1, double value) ---------------------------------*/
    @Test public void testExpandNom() 
    {
    	exampleRange3 = Range.expandToInclude(exampleRange2, 45);
    	assertEquals("Value was not found ", true, exampleRange3.contains(-100));
    }
    @Test public void testExpandUpper() /*FAILED*/
    {
    	exampleRange3 = Range.expandToInclude(exampleRange2, 55);
    	assertEquals("Value was not the correct upper bound ", 55, exampleRange3.getUpperBound(), .000000001d);
    }
    @Test public void testExpandLower() /*FAILED: Defective method*/
    {
    	exampleRange3 = Range.expandToInclude(exampleRange2, -500);
    	assertEquals("Value was not the correct upper bound ", -500, exampleRange3.getLowerBound(), .000000001d);
    }
    @Test public void testExpandNull() 
    {
    	exampleRange3 = Range.expandToInclude(null, -500);
    	assertEquals("Range created is not correct ", -500, exampleRange3.getLowerBound(), .000000001d);
    }
    @Test public void testExpandValid() 
    {
    	exampleRange3 = Range.expandToInclude(exampleRange1, 45);
    	assertEquals("Value was not found ", true, exampleRange3.contains(42));
    }
    /*------------------------------ TESTS FOR INTERSECTS(double lower, double upper) ---------------------*/
    @Test
    public void testIntersectsB0Lower() 
    {
    	exampleRange3 = new Range(-200, 450);
    	assertEquals("Intersect returned wrong value", true, exampleRange3.intersects(-300, 50));
    }
    @Test
    public void testIntersectsB0Greater() 
    {
    	exampleRange3 = new Range(-200, 450);
    	assertEquals("Intersect returned wrong value", true, exampleRange3.intersects(-100, 50));
    }
    
    /*------------------------------ TESTS FOR INTERSECTS(Range r) ---------------------*/
    @Test
    public void testIntersectsRangeB0Lower() 
    {
    	exampleRange3 = new Range(-200, 450);
    	assertEquals("Intersect returned wrong value", true, exampleRange3.intersects(exampleRange1));
    }
    @Test
    public void testIntersectsRangeB0Greater() 
    {
    	exampleRange3 = new Range(-200, 450);
    	Range exampleRange4 = new Range(500, 1000);
    	assertEquals("Intersect returned wrong value", false, exampleRange3.intersects(exampleRange4));
    }
    
    /*--------------------------------- TESTS FOR SHIFT(Range r1, double delta, boolean allowZeroCrossing) ---------------------------------*/
    @Test
    public void testShiftZeroCrossing() 
    {
    	/*Using exampleRange1: [-100, 100]*/
    	exampleRange3 = Range.shift(exampleRange1, 100, true);
    	assertEquals("Upper bound is not correct ", 200, exampleRange3.getUpperBound(), .000000001d);
    }
    /*--------------------------------- TESTS FOR SHIFT(Range r1, double delta) ---------------------------------*/
    @Test public void testShiftUpperPos() //DEFECTIVE
    {
    	/*Using exampleRange1: [-100, 100]*/
    	exampleRange3 = Range.shift(exampleRange1, 100);
    	assertEquals("Upper bound is not correct ", 200, exampleRange3.getUpperBound(), .000000001d);
    }
    @Test public void testShiftLowerPos() 
    {
    	exampleRange3 = Range.shift(exampleRange1, 100);
    	assertEquals("Lower bound is not correct ", 0, exampleRange3.getLowerBound(), .000000001d);
    }
    @Test public void testShiftNom() //DEFECTIVE
    {
    	exampleRange3 = Range.shift(exampleRange1, 230);
    	assertEquals("Value should exist in range ", true, exampleRange3.contains(189));
    }
    @Test public void testShiftUpperNeg() //DEFECTIVE
    {
    	/*Using exampleRange1: [-100, 100]*/
    	exampleRange3 = Range.shift(exampleRange1, -100);
    	assertEquals("Upper bound is not correct ", 0, exampleRange3.getUpperBound(), .000000001d);
    }
    @Test public void testShiftLowerNeg() 
    {
    	exampleRange3 = Range.shift(exampleRange1, -100);
    	assertEquals("Lower bound is not correct ", -200, exampleRange3.getLowerBound(), .000000001d);
    }
    
    /**--------------------------------- TESTING EQUALS(OBJECT O) ------------------------------------------------**/
    @Test
    public void testEqualsNotRangeInstance() 
    {
    	Double d = 50.0;
    	assertEquals("Returned Range objet ", false, exampleRange1.equals(d));
    }
    @Test
    public void testEqualsNotUpper() 
    {
    	exampleRange3 = new Range(-100, 101);
    	assertEquals("Upper bounds of the two range match ", false, exampleRange3.equals(exampleRange1));
    }
    @Test
    public void testEqualsNotLower() 
    {
    	exampleRange3 = new Range(-101, 100);
    	assertEquals("Upper bounds of the two range match ", false, exampleRange3.equals(exampleRange1));
    }
    
    /**-------------------------------------------- TESTING SCALE(Range, double) -----------------------------------------**/
    @Test(expected = IllegalArgumentException.class)
    public void testScaleFactorUnderZero() 
    {
    	exampleRange3 = Range.scale(exampleRange1, -5);
    }
    @Test
    public void testScaleFactorOverZeroLowerBound() 
    {
    	exampleRange3 = Range.scale(exampleRange1, 5);
    	assertEquals("Factor scale up was wrong", -500, exampleRange3.getLowerBound(), .000000001d);
    }
    @Test
    public void testScaleFactorOverZeroUpperBound() 
    {
    	exampleRange3 = Range.scale(exampleRange1, 5);
    	assertEquals("Factor scale up was wrong", 500, exampleRange3.getUpperBound(), .000000001d);
    }
    
    /**-------------------------------------------- TESTING EXPAND(Range, double, double) -----------------------------------------**/
    @Test
    public void testExpandLowerMoreThanUpper() 
    {
    	exampleRange1 = new Range(250, 300);
    	exampleRange3 = Range.expand(exampleRange1, 1, -3);
    	assertEquals("New Range is not correct ", 175, exampleRange3.getUpperBound(), .000000001d);
    }
    @Test
    public void testExpandUpperGreaterLowerBound() 
    {
    	exampleRange3 = Range.expand(exampleRange1, 1, 5);
    	assertEquals("New Range is not correct bound-wise ", -300, exampleRange3.getLowerBound(), .00000001d);
    }
    
    /**-------------------------------------------- TESTING COMBINEIGNORINGNAN(Range, Range) -----------------------------------------**/
    @Test
    public void testNaNValidRanges() 
    {
    	exampleRange3 = Range.combineIgnoringNaN(exampleRange1, exampleRange2);
    	assertEquals("Bound is incorrect ", -450, exampleRange3.getLowerBound(), .000000001d);
    }
    @Test
    public void testNaNNullRange1() 
    {
    	exampleRange3 = Range.combineIgnoringNaN(null, exampleRange2);
    	assertEquals("Bound is incorrect ", -450, exampleRange3.getLowerBound(), .000000001d);
    }
    @Test
    public void testNaNNullRange2() 
    {
    	exampleRange3 = Range.combineIgnoringNaN(exampleRange1, null);
    	assertEquals("Bound is incorrect ", -100, exampleRange3.getLowerBound(), .000000001d);
    }
    @Test
    public void testNaNRange2IsNaN() 
    {
    	exampleRange2 = new Range(Double.NaN, Double.NaN);
    	exampleRange3 = Range.combineIgnoringNaN(null, exampleRange2);
    	assertEquals("Did not return null ", null, exampleRange3);
    }
    @Test
    public void testNaNRange1IsNaN() 
    {
    	exampleRange1 = new Range(Double.NaN, Double.NaN);
    	exampleRange3 = Range.combineIgnoringNaN(exampleRange1, null);
    	assertEquals("Did not return null ", null, exampleRange3);
    }
    @Test
    public void testNaNMinMaxNaN() 
    {
    	exampleRange1 = new Range(Double.NaN, Double.NaN);
    	exampleRange2 = exampleRange1;
    	exampleRange3 = Range.combineIgnoringNaN(exampleRange1, exampleRange2);
    	assertEquals("Did not return null ", null, exampleRange3);
    }
    /** ----------------------------- TESTING CONSTRAIN(double value) ------------------- **/
    @Test
    public void testConstrainInRange() 
    {
    	assertEquals("Incorrect value returned ", 50, exampleRange1.constrain(50), .00000001d);
    }
    @Test
    public void testConstrainAboveUpperBound() 
    {
    	assertEquals("Incorrect value returned ", 100, exampleRange1.constrain(600), .00000001d);
    }
    @Test
    public void testConstrainBelowLowerBound() 
    {
    	assertEquals("Incorrect value returned ", -100, exampleRange1.constrain(-300), .00000001d);
    }
    
/*-----------------------------------------------------------------------------------------------------------------------*/
    /*After, destroys the object, Java has automatic garbage collection.*/
    @After
    public void tearDown() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }
}


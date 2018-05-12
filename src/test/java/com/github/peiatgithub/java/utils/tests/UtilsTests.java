package com.github.peiatgithub.java.utils.tests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static com.github.peiatgithub.java.utils.Utils.*;
import static com.github.peiatgithub.java.utils.Constants.*;
import com.github.peiatgithub.java.utils.NanoStopWatch;
import com.github.peiatgithub.java.utils.RunFlag;


import org.junit.Test;

/**
 * tests
 * 
 * @author pei
 *
 */
public class UtilsTests {
	
	
	@Test
	public void testNumberToReadableString() throws Exception {
		
		assertThat(numberToReadableString(1), is("1.0"));
		assertThat(numberToReadableString(999), is("999.0"));
		assertThat(numberToReadableString(1000), is("1 K"));
		assertThat(numberToReadableString(5666), is("5.7 K"));
		assertThat(numberToReadableString(9533), is("9.5 K"));
		assertThat(numberToReadableString(10000), is("10 K"));
		assertThat(numberToReadableString(123456), is("123.5 K"));
		assertThat(numberToReadableString(999567), is("999.6 K"));
		assertThat(numberToReadableString(999954), is("1 Mn"));
		assertThat(numberToReadableString(1000000), is("1 Mn"));
		assertThat(numberToReadableString(1043210), is("1 Mn"));
		assertThat(numberToReadableString(1234578), is("1.2 Mn"));
		assertThat(numberToReadableString(999543210), is("999.5 Mn"));
		assertThat(numberToReadableString(999999999), is("1 Bn"));
		assertThat(numberToReadableString(1000000000), is("1 Bn"));
		assertThat(numberToReadableString(800000000000L), is("800 Bn"));
		assertThat(numberToReadableString(LIGHT_YEAR_IN_KM), is("9460.8 Bn")); // 9460800000000
		assertThat(numberToReadableString(Long.MAX_VALUE), is("9223372036.9 Bn")); //9223372036854775807
		
	}

	@Test
	public void testGetSubString() throws Exception {
		
        final String theStr = ",111,aaa,bbb,,ccc,";
        
        assertThat(getSubString(theStr, COMMA, 0, 1), is("111"));
        assertThat(getSubString(theStr, COMMA, 1, 2), is("aaa"));
        assertThat(getSubString(theStr, COMMA, 1, 4), is("aaa,bbb,"));
        assertThat(getSubString(theStr, COMMA, 2, 3), is("bbb"));
        assertThat(getSubString(theStr, COMMA, 3, 4), is(EMPTY));
        assertThat(getSubString(theStr, COMMA, 0, 2), is("111,aaa"));
        assertThat(getSubString(theStr, COMMA, 0, 100), is(EMPTY));
        assertThat(getSubString(theStr, COMMA, -1, 0), is(EMPTY));
        assertThat(getSubString(theStr, COMMA, -1, 1), is(",111"));
        assertThat(getSubString(theStr, COMMA, -2, 1), is(",111"));
        assertThat(getSubString(theStr, COMMA, 4, 5), is("ccc"));

        // unusual edge cases
        assertThat(getSubString(theStr, COMMA, 0, 0), is(EMPTY));
        assertThat(getSubString(theStr, COMMA, 1, 1), is(EMPTY));
        assertThat(getSubString(theStr, COMMA, 5, 5), is(EMPTY));
        assertThat(getSubString(theStr, COMMA, 5, 6), is(EMPTY));
        assertThat(getSubString(theStr, COMMA, 100, 100), is(EMPTY));
        assertThat(getSubString(theStr, COMMA, 1, 0), is(EMPTY));
        assertThat(getSubString(theStr, COMMA, 2, -1), is(EMPTY));
        assertThat(getSubString(theStr, COMMA, -2, -1), is(EMPTY));
        assertThat(getSubString(theStr, COMMA, -2, -2), is(EMPTY));
        assertThat(getSubString(theStr, COMMA, -1, -2), is(EMPTY));
        assertThat(getSubString(theStr, COMMA, -2, 100), is(EMPTY));
		
	}
	
	@Test
	public void testArraySum() throws Exception {
		
        assertThat(intsSum(), is(0));
        assertThat(intsSum(1), is(1));
        assertThat(intsSum(1,2), is(3));
        assertThat(intsSum(1,2,3), is(6));
        assertThat(intsSum(new int[] {1,2,3}), is(6));
        
	}
	
	@Test
	public void testNanoStopWatch() throws Exception {
		
		assertThat(NanoStopWatch.stopAndGetMillis(), is(0L));
		assertThat(NanoStopWatch.getMillis(), is(0L));

		NanoStopWatch.begin();
		threadSleep(1);
		assertThat(NanoStopWatch.getMillis(), greaterThanOrEqualTo(1L));
		threadSleep(1);
		assertThat(NanoStopWatch.stopAndGetMillis(), greaterThanOrEqualTo(2L));

		NanoStopWatch.begin();
		threadSleep(1);
		assertThat(NanoStopWatch.getNanos(), greaterThanOrEqualTo(1000000L));
		threadSleep(1);
		assertThat(NanoStopWatch.stopAndGetNanos(), greaterThanOrEqualTo(2000000L));

		threadSleep(1);
		assertThat(NanoStopWatch.getNanos(), is(0L));
		assertThat(NanoStopWatch.stopAndGetNanos(), is(0L));
		
	}
	
	@Test
	public void testRunFlag() throws Exception {
	
		RunFlag.reset();
		assertThat(RunFlag.hasRun(), is(false));
		assertThat(RunFlag.runTimes(), is(0));
		
		repeatRun(3, ()->RunFlag.run());
		assertThat(RunFlag.hasRun(), is(true));
		assertThat(RunFlag.runTimes(), is(3));
		
	}
	
	@Test
	public void testIfNotNull() throws Exception {
		
		RunFlag.reset();
		ifNotNull(NULL_TEXT, ()->RunFlag.run());
		assertThat(RunFlag.hasRun(), is(true));
		assertThat(RunFlag.runTimes(), is(1));
		
		RunFlag.reset();
		ifNotNull(null, ()-> RunFlag.run());
		assertThat(RunFlag.hasRun(), is(false));
		assertThat(RunFlag.runTimes(), is(0));
		
	}
	
	@Test
	public void testThreadUtils() throws Exception {
		
		Thread t = createAndStartThread(()->threadSleep(1000));
		assertThat(t.isAlive(), is(true));
		threadJoin(t);
		assertThat(t.isAlive(), is(false));
		
	}

}

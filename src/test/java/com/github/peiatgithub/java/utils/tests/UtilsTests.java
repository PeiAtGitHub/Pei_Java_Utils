package com.github.peiatgithub.java.utils.tests;

import static org.junit.Assert.*;

import java.util.Collections;
import static org.hamcrest.CoreMatchers.*;
import static org.assertj.core.api.Assertions.*;

import static com.github.peiatgithub.java.utils.Utils.*;
import static com.github.peiatgithub.java.utils.Constants.*;
import com.github.peiatgithub.java.utils.NanoStopWatch;
import com.github.peiatgithub.java.utils.Encloser;
import com.github.peiatgithub.java.utils.RunFlag;

import org.junit.Test;

/**
 * Utils tests
 * 
 * @author pei
 *
 */
public class UtilsTests {

    @Test
    public void testStrAndStrln() throws Exception {

        String theFormat = "Hello, {}, I have {} gift(s) for you!";
        String output = "Hello, PEI, I have 100 gift(s) for you!";

        assertThat(str(theFormat, "PEI", 100), is(output));
        assertThat(strln(theFormat, "PEI", 100), is(output + System.lineSeparator()));

        theFormat = "I can lend you my {}, since your {} is in workshop";

        assertThat(str(theFormat, "CAR"), is("I can lend you my CAR, since your {} is in workshop"));
        assertThat(str(theFormat, "CAR", "BIKE"), is("I can lend you my CAR, since your BIKE is in workshop"));
        assertThat(str(theFormat, "CAR", "BIKE", "BOAT"), is("I can lend you my CAR, since your BIKE is in workshop"));

        assertThat(str("Hello, PEI."), is("Hello, PEI."));
        assertThat(str("Hello, {} PEI."), is("Hello, {} PEI."));
        assertThat(str("Hello, '{''}' {}.", "PEI"), is("Hello, {} PEI."));
        assertThat(str("Hello, { } {}.", "PEI"), is("Hello, { } PEI."));

    }

    @Test
    public void testSafeStr() throws Exception {

        assertThat(safeStr(null), is(EMPTY));
        assertThat(safeStr(STR), is(STR));

    }

    @Test
    public void testQuoteString() throws Exception {
        assertThat(encloseString(STR, Encloser.BRACKETS), is("[STR]"));
        assertThat(encloseString(EMPTY, Encloser.BRACKETS), is("[]"));
        assertThat(encloseString(STR, Encloser.EMPTY), is(STR));

        assertThat(encloseString(null, Encloser.BRACKETS), nullValue());
        assertThat(encloseString(STR, null), is(STR));
    }

    @Test
    public void testListToString() throws Exception {

        assertThat(listToString(TEST_LIST_123, COMMA + SPACE, Encloser.PARENTHESES), is("(1), (2), (3)"));
        assertThat(listToString(TEST_LIST_123, null, null), is("123"));

        assertThat(listToString(TEST_LIST_123, SPACE, Encloser.DOUBLE), is("\"1\" \"2\" \"3\""));

        assertThat(listToString(null, WHATEVER, Encloser.PARENTHESES), nullValue());
        assertThat(listToString(Collections.emptyList(), WHATEVER, Encloser.PARENTHESES), is(EMPTY));

    }

    @Test
    public void testNumberToReadableString() throws Exception {

        assertThat(numberToReadable(1), is("1.0"));
        assertThat(numberToReadable(999), is("999.0"));
        assertThat(numberToReadable(1000), is("1 K"));
        assertThat(numberToReadable(5666), is("5.7 K"));
        assertThat(numberToReadable(9533), is("9.5 K"));
        assertThat(numberToReadable(10000), is("10 K"));
        assertThat(numberToReadable(123456), is("123.5 K"));
        assertThat(numberToReadable(999567), is("999.6 K"));
        assertThat(numberToReadable(999954), is("1 Mn"));
        assertThat(numberToReadable(1000000), is("1 Mn"));
        assertThat(numberToReadable(1043210), is("1 Mn"));
        assertThat(numberToReadable(1234578), is("1.2 Mn"));
        assertThat(numberToReadable(999543210), is("999.5 Mn"));
        assertThat(numberToReadable(999999999), is("1 Bn"));
        assertThat(numberToReadable(1000000000), is("1 Bn"));
        assertThat(numberToReadable(800000000000L), is("800 Bn"));
        assertThat(numberToReadable(LIGHT_YEAR_IN_KM), is("9460.8 Bn")); // 9460800000000
        assertThat(numberToReadable(Long.MAX_VALUE), is("9223372036.9 Bn")); // 9223372036854775807

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
        assertThat(intsSum(1, 2), is(3));
        assertThat(intsSum(1, 2, 3), is(6));
        assertThat(intsSum(new int[] { 1, 2, 3 }), is(6));

    }

    @Test
    public void testNanoStopWatch() throws Exception {

        assertThat(NanoStopWatch.stopAndGetMillis(), is(0L));
        assertThat(NanoStopWatch.getMillis(), is(0L));

        NanoStopWatch.begin();
        threadSleep(1);
        assertThat(NanoStopWatch.getMillis()).isGreaterThanOrEqualTo(1);
        threadSleep(1);
        assertThat(NanoStopWatch.stopAndGetMillis()).isGreaterThanOrEqualTo(2);

        NanoStopWatch.begin();
        threadSleep(1);
        assertThat(NanoStopWatch.getNanos()).isGreaterThanOrEqualTo(1000000);
        threadSleep(1);
        assertThat(NanoStopWatch.stopAndGetNanos()).isGreaterThanOrEqualTo(2000000);

        threadSleep(1);
        assertThat(NanoStopWatch.getNanos(), is(0L));
        assertThat(NanoStopWatch.stopAndGetNanos(), is(0L));

    }

    @Test
    public void testRunFlag() throws Exception {

        RunFlag.reset();
        assertThat(RunFlag.hasRun()).isFalse();
        assertThat(RunFlag.runTimes(), is(0));

        repeatRun(3, () -> RunFlag.run());
        assertThat(RunFlag.hasRun());
        assertThat(RunFlag.runTimes(), is(3));

    }

    @Test
    public void testIfNotNull() throws Exception {

        RunFlag.reset();
        ifNotNull(NULL_TEXT, () -> RunFlag.run());
        assertThat(RunFlag.hasRun());
        assertThat(RunFlag.runTimes(), is(1));

        RunFlag.reset();
        ifNotNull(null, () -> RunFlag.run());
        assertThat(RunFlag.hasRun()).isFalse();
        assertThat(RunFlag.runTimes(), is(0));

    }

    @Test
    public void testThreadUtils() throws Exception {

        Thread t = createAndStartThread(() -> threadSleep(1000));
        assertThat(t.isAlive());
        threadJoin(t);
        assertThat(t.isAlive()).isFalse();

    }

    @Test
    public void testOnlyFirstCapital() throws Exception {

        assertThat(onlyFirstCapital("HELLO!"), is("Hello!"));
        assertThat(onlyFirstCapital("hello!"), is("Hello!"));
        assertThat(onlyFirstCapital("Hello!"), is("Hello!"));
        assertThat(onlyFirstCapital("HE"), is("He"));
        assertThat(onlyFirstCapital("h!"), is("H!"));
        assertThat(onlyFirstCapital("h"), is("H"));
        assertThat(onlyFirstCapital("H"), is("H"));
        assertThat(onlyFirstCapital("!"), is("!"));
        assertThat(onlyFirstCapital(""), is(""));
        assertThat(onlyFirstCapital(null), nullValue());

    }

    @Test
    public void testRandomNumberOfDigits() throws Exception {

        assertThatThrownBy(() -> randomNumberOfDigit(-1)).isInstanceOf(IAE).hasMessageContaining("[1, 18]");
        assertThatThrownBy(() -> randomNumberOfDigit(0)).isInstanceOf(IAE).hasMessageContaining("[1, 18]");
        assertThatThrownBy(() -> randomNumberOfDigit(19)).isInstanceOf(IAE).hasMessageContaining("[1, 18]");

        assertThat(randomNumberOfDigit(1)).isGreaterThanOrEqualTo(0).isLessThanOrEqualTo(9);
        assertThat(randomNumberOfDigit(3)).isGreaterThanOrEqualTo(100).isLessThanOrEqualTo(999);
        assertThat(randomNumberOfDigit(18)).isGreaterThanOrEqualTo(100000000000000000L)
                .isLessThanOrEqualTo(999999999999999999L);

    }

    @Test
    public void testDivisibleBy() throws Exception {

        assertThat(isDivisibleBy(9, 3));
        assertThat(isDivisibleBy(0, 10));
        assertThat(isDivisibleBy(10, 3)).isFalse();

        assertThatThrownBy(() -> isDivisibleBy(10, 0)).isInstanceOf(ArithmeticException.class);
    }

    @Test
    public void testClearStringBuilder() throws Exception {

        StringBuilder sb = new StringBuilder(STR);

        assertThat(sb).hasSize(3);
        assertThat(sb.toString()).isEqualTo(STR);
        clearStringBuilder(sb);
        assertThat(sb).hasSize(0);
        assertThat(sb.toString()).isEqualTo(EMPTY);
        sb.append(STR);
        assertThat(sb.toString()).isEqualTo(STR);

    }
    
    @Test
    public void testGetFileLastLine() throws Exception {
        assertEquals(EMPTY, getLastLineOfFile(getResourceFile("emptyFile.txt")));
        assertEquals("This is a one line file.", getLastLineOfFile(getResourceFile("onelineFile.txt")));
        assertEquals(EMPTY, getLastLineOfFile(getResourceFile("secondlineEmptyFile.txt")));
        assertEquals("The 3rd line.", getLastLineOfFile(getResourceFile("threelinesFile.txt")));
    }

}

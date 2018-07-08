package com.github.peiatgithub.java.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.github.peiatgithub.java.utils.function.NonArgFunction;

import static com.github.peiatgithub.java.utils.Constants.*;

/**
 * Misc utility methods.
 * 
 * @author pei
 * @since 1.0
 */
public class Utils {

    /**
     * <pre>
     * This method is an easier alternative to the String.format(format, args...)
     * 
     * Usage:
     *   str([format with place holders], Objects... args)
     *   The place holder is "{}"
     *   Use "'{''}'" for a literal "{}"
     *   If a place holder cannot find corresponding value, the place holder will be present in the result
     * 
     * Example:
     *   str("hello {}!") 
     *   output: "hello {}!"
     *   
     *   str("hello {}, I have {} gift(s) for you!", "PEI", 100) 
     *   output: "hello PEI, I have 100 gift(s) for you!"
     *   
     *   str("I can lend you my {}, since your {} is in workshop", "CAR")
     *   output: "I can lend you my CAR, since your {} is in workshop"
     *
     *   str("I can lend you my {}, since your {} is in workshop", "CAR", "BIKE")
     *   output: "I can lend you my CAR, since your BIKE is in workshop"
     *   
     *   str("I can lend you my {}, since your {} is in workshop", "CAR", "BIKE", "BOAT")
     *   output: "I can lend you my CAR, since your BIKE is in workshop"
     * </pre>
     *
     * @since 5.0
     */
    public static String str(String format, Object... args) {

        String placeHolder = "{}";
        String escapedPlaceHolder = "'{''}'";

        if (args == null || args.length == 0) {
            return format;
        }

        int numOfPlaceHolder = StringUtils.countMatches(format, placeHolder);
        int numOfValues = args.length;

        for (int i = 0; i < Math.min(numOfPlaceHolder, numOfValues); i++) {
            format = StringUtils.replace(format, placeHolder, args[i].toString(), 1);
        }

        // if there are more place holders than values, fill the rest of placeholders
        // with the last value
        // result = StringUtils.replace(result, placeHolder, args[numOfValues
        // -1].toString());

        // escape
        format = StringUtils.replace(format, escapedPlaceHolder, placeHolder);

        return format;
    }

    /**
     * Similar to {@link #str}, with a system-dependent line separator at the end.
     */
    public static String strln(String format, Object... args) {
        return str(format, args) + System.lineSeparator();
    }

    /**
     * println the message with the thread name prefixed.
     */
    public static void printlnWithThreadName(String message) {
        strln("{}: {}", Thread.currentThread().getName(), message);
    }

    /**
     * Simplifies the code to repeatedly run a piece of code for N times.
     */
    public static void repeatRun(int times, NonArgFunction function) {
        IntStream.range(0, times).forEach(i -> function.doSth());
    }

    /**
     * <pre>
     * Simplify the code to repeatedly run a piece of code for N times.
     * Takes the iteration index as arg of the to-run code.
     * </pre>
     */
    public static void repeatRun(int times, Consumer<Integer> c) {
        IntStream.range(0, times).forEach(i -> c.accept(i));
    }

    /**
     * <pre>
     * Display the number of bytes in a human readable way.
     * Apache Commons FileUtils.byteCountToDisplaySize() rounds down to integer number.
     * This method keeps 1 digit after decimal point.
     * </pre>
     * 
     * @return the readable String
     */
    public static String bytesToReadable(double numOfBytes) {

        if (numOfBytes < 0) {
            throw new IllegalArgumentException("Number of bytes must bigger than or equal to 0.");
        }

        DecimalFormat df = new DecimalFormat("#.#");

        if (numOfBytes >= GB) {
            return df.format(numOfBytes / GB) + " GB";
        } else if (numOfBytes >= MB) {
            return df.format(numOfBytes / MB) + " MB";
        } else if (numOfBytes >= KB) {
            return df.format(numOfBytes / KB) + " KB";
        } else {
            return df.format(numOfBytes) + " Bytes";
        }
    }

    /**
     * returns the human readable String of a number with proper units
     */
    public static String numberToReadable(long num) {
        String result = EMPTY;
        double numAbs = Math.abs(num);

        DecimalFormat df = new DecimalFormat("#.#");

        if (numAbs >= BN) {
            result = df.format(numAbs / BN).toString() + " Bn";
        } else if (numAbs >= MN) {
            String r = df.format(numAbs / MN).toString();
            result = r.equals("1000") ? "1 Bn" : (r + " Mn");
        } else if (numAbs >= K) {
            String r = df.format(numAbs / K).toString();
            result = r.equals("1000") ? "1 Mn" : (r + " K");
        } else {
            result = String.valueOf(numAbs);
        }
        if (num < 0) {
            result = DASH + result;
        }
        return result;
    }

    /**
     * <pre>
     * Make the codes for Thread.sleep(millis) in one line by 
     * swallowing the InterruptedException 
     * (just call e.printStackTrace())
     * </pre>
     */
    public static void threadSleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * <pre>
     * Make the codes for thread.join() in one line by 
     * swallowing the InterruptedException 
     * (just call e.printStackTrace())
     * </pre>
     */
    public static void threadJoin(Thread thr) {
        try {
            thr.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Simplify this common practice to one line.
     * 
     * @param r to run in the newly created thread
     * @return the newly created thread
     */
    public static Thread createAndStartThread(Runnable r) {
        Thread t = new Thread(r);
        t.start();
        return t;
    }

    /**
     * <pre>
     * Get the sub String between the n1th and the n2th delimiter. 
     * Index n1, n2 begins from 0
     * E.g. 
     * getSubString("111,aaa,bbb,,ccc", ",", 1, 2) returns "bbb"
     * getSubString("111,aaa,bbb,,ccc", ",", -1, 1) returns "111,aaa"
     * getSubString("111,aaa,bbb,,ccc", ",", 2, 3) returns ""
     * getSubString("111,aaa,bbb,,ccc", ",", x, x) returns ""
     * getSubString("111,aaa,bbb,,ccc", ",", x, [a value bigger than the max occurrence number of delimiter]) returns ""
     * </pre>
     */
    public static String getSubString(String str, String delimiter, int n1, int n2) {
        if (n2 <= n1) {
            return EMPTY;
        }
        try {
            int resultBeginIdx = 0;
            int delimiterIdx = -1;
            for (int i = 0; i <= n2; i++) {
                delimiterIdx = str.indexOf(delimiter, delimiterIdx + 1);
                if (delimiterIdx == -1) {
                    return EMPTY;
                }
                if (i == n1) {// this happens at most once
                    resultBeginIdx = delimiterIdx + 1;
                }
            }
            return str.substring(resultBeginIdx, delimiterIdx);
        } catch (IndexOutOfBoundsException e) {
            return EMPTY;
        }
    }

    /**
     * <pre>
     * The java.lang.StringBuilder has no method to reset/clear its content simply and explicitly.
     * This method do so.
     * </pre>
     */
    public static void clearStringBuilder(StringBuilder sb) {
        sb.setLength(0);
    }

    /**
     * If the StringBuilder is not ended with the tail, then append the tail to the
     * StringBuilder. Both arguments must not be null, otherwise throw exception.
     */
    public static void ensureEndWith(StringBuilder sb, String tail) {
        Objects.requireNonNull(sb, ARGUMENT_MUST_NOT_BE_NULL);
        Objects.requireNonNull(tail, ARGUMENT_MUST_NOT_BE_NULL);

        if (!(sb.toString().endsWith(tail))) {
            sb.append(tail);
        }
    }

    /**
     * <pre>
     * Convert a String's first char to upper case AND all other chars to lower case.
     * Empty input returns empty. 
     * Null input returns null.
     * </pre>
     */
    public static String onlyFirstCapital(String str) {
        if (str == null) {
            return null;
        }
        if (str.length() <= 1) {
            return str.toUpperCase();
        } else {
            return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
        }
    }

    /**
     * <pre>
     * Join the array of Strings with the separator.
     * One difference from Apache Commons StringUtils.join() is
     * 
     * StringUtils.join(["abc"], ';') returns "abc;"
     * StringUtils.join(["abc", "def", "ghi"], ';') returns "abc;def;ghi"
     * 
     * this method 
     * join(["abc"], ';') returns "abc"
     * join(["abc", "def", "ghi"], ';') returns "abc;def;ghi" // same as StringUtils
     * 
     * Null or empty input array will return an empty String.
     * </pre>
     */
    public static String join(String[] arr, String separator) {
        if (ArrayUtils.isEmpty(arr)) {
            return EMPTY;
        }
        if (arr.length == 1) {
            return arr[0];
        } else { // arr.length > 1
            return StringUtils.join(arr, separator);
        }
    }

    /**
     * <pre>
     * Convert the list to a String representation with quotes and separators.
     * Example:
     * If a String List has "Tom" and "Jerry"
     * listToString(l, ";", Quote.SINGLE) will be "'Tom';'Jerry'"
     * 
     * null or empty list returns empty string "";
     * null separator/quote will be taken as empty("") separator/quote
     * </pre>
     */
    public static String listToString(List<? extends Object> l, String separator, Encloser encloser) {

        if (CollectionUtils.isEmpty(l)) {
            return EMPTY;
        }

        separator = safeStr(separator);
        if (encloser == null) {
            encloser = Encloser.EMPTY;
        }

        StringBuilder result = new StringBuilder();
        for (Object o : l) {
            result.append(encloser.begin()).append(o.toString()).append(encloser.end()).append(separator);
        }

        return StringUtils.removeEnd(result.toString(), separator);

    }

    /**
     * Similar to {@link #listToString(List, String, Encloser)}.
     */
    public static String arrayToString(String[] arr, String separator, Encloser encloser) {

        if (ArrayUtils.isEmpty(arr)) {
            return EMPTY;
        }

        separator = safeStr(separator);
        if (encloser == null) {
            encloser = Encloser.EMPTY;
        }

        StringBuilder result = new StringBuilder();
        for (String s : arr) {
            result.append(encloser.begin()).append(s.toString()).append(encloser.end()).append(separator);
        }

        return StringUtils.removeEnd(result.toString(), separator);

    }

    /**
     * <pre>
     * Returns a pair of two lists, 
     * one contains the input map keys, another contains values 
     * in corresponding order.
     * </pre>
     * @since 5.0
     */
    public static <K, V> Pair<List<K>, List<V>> mapToKvLists(Map<K, V> theMap) {
        List<K> kList = new ArrayList<>();
        List<V> vList = new ArrayList<>();
        Pair<List<K>, List<V>> result = Pair.of(kList, vList);
        for (K key : theMap.keySet()) {
            kList.add(key);
            vList.add(theMap.get(key));
        }
        return result;
    }

    /**
     * <pre>
     * Enclose the specified string. 
     * E.g. 
     *   encloseString("Tom", Encloser.PARENTHESES) outputs "(Tom)".
     *   
     * null input string will return a null.
     * null quote is taken as no quote
     * </pre>
     * @since 5.0
     */
    public static String encloseString(String str, Encloser quote) {
        if (str == null) {
            return null;
        } else if (quote == null) {
            return str;
        } else {
            return quote.begin() + str + quote.end();
        }
    }

    /**
     * If str is null, return empty string "", else, return str.
     */
    public static String safeStr(String str) {
        if (str == null) {
            return EMPTY;
        } else {
            return str;
        }
    }

    /**
     * <pre>
     * Creates a random positive integer number with specified number of digits.
     * (The first digit will not be 0 unless there is only 1 digit.)
     * (Digit number must be in range of [1, 18] because Long.MAX_VALUE is a 19 Digit number) 
     * E.g.
     * randomNumberOfDigit(3) creates a number in [100, 999]
     * randomNumberOfDigit(1) creates a number in [0, 9]
     * </pre>
     * 
     * @param digit must be in range [1, 18], otherwise throw exception.
     */
    public static long randomNumberOfDigit(int digit) {
        String result;
        if (digit <= 0 || digit >= 19) {
            throw new IllegalArgumentException("'digit' must be in range of [1, 18].");
        } else if (digit == 1) {
            result = RandomStringUtils.randomNumeric(1);
        } else { // digit >1
            String firstDigit = RandomStringUtils.random(1, "123456789");
            String otherDigits = RandomStringUtils.randomNumeric(digit - 1);
            result = firstDigit + otherDigits;
        }
        return Long.valueOf(result);
    }

    /**
     * <pre>
     * Tells you if long integer "a" is divisible by "b". 
     * (Judgment is made simply by division remainder)
     * </pre>
     * @since 5.0
     */
    public static boolean isDivisibleBy(long a, long b) {
        return (a % b == 0);
    }

    /**
     * <pre>
     * If obj is not null, run fnc.
     * 
     * Simplify the following code to one line:
     * if (sth != null){
     *     run some code;
     * }
     * </pre>
     */
    public static void ifNotNull(Object obj, NonArgFunction fnc) {
        ifThen((obj != null), fnc);
    }

    /**
     * <pre>
     * If obj is null, run fnc.
     * Simplify the following code to one line:
     * if (sth == null){
     *     run some code;
     * }
     * </pre>
     */
    public static void ifNull(Object obj, NonArgFunction fnc) {
        ifThen((obj == null), fnc);
    }

    /**
     * <pre>
     * If flag is true, run function.
     * Simplify the following code to 1 line: 
     * if(flag){
     *     fnc.doSth();
     * }
     * This is especially helpful when fnc is just a simple 1 line code.
     * </pre>
     */
    public static void ifThen(boolean flag, NonArgFunction fnc) {
        if (flag) {
            fnc.doSth();
        }
    }

    /**
     * <pre>
     * If flag is true, run function1, else, run function2
     * Simplify the following code to 1 line:
           if(flag) {
               fnc1.doSth();
           }else {
               fnc2.doSth();
           }
       This is especially helpful when fnc1 and fnc2 are just simple 1 line codes.
     * </pre>
     */
    public static void ifElse(boolean flag, NonArgFunction fnc1, NonArgFunction fnc2) {
        if (flag) {
            fnc1.doSth();
        } else {
            fnc2.doSth();
        }
    }

    /**
     * Get the sum value of an array of int numbers.
     */
    public static int intsSum(int... numbers) {
        int sum = 0;
        for (int num : numbers) {
            sum = sum + num;
        }
        return sum;
    }

}

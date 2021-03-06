package com.github.peiatgithub.java.utils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.peiatgithub.java.utils.collections.MapBuilder;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * <pre>
 * This class contains some commonly-used and general purpose constants,
 * some of which are especially for tests and demos
 * so that you don't always need to declare or create test data.
 * </pre>
 * @author pei
 * @since 1.0
 */
public class Constants {

	public final static String EMPTY = "";
	public final static String SPACE = " ";

	public final static String COMMA = ",";
    public final static String SEMICOLON = ";";
    public final static String DASH = "-";
    public final static String DOT = ".";
    public final static String UNDER_SCORE = "_";
    public final static String ASTERISK = "*";
    public final static String SLASH = "/";
    public final static String QUESTION_MARK = "?";
    public final static String PERCENTAGE = "%";
    public final static String NULL_TEXT = "null";

    /** Number of 1 K Bytes */
    public final static long KB = 1024L;
    /** Number of 1 M Bytes */
    public final static long MB = KB * KB;
    /** Number of 1 G Bytes */
    public final static long GB = MB * KB;

    /** 1 thousand */
    public final static long K = 1000L;
    /** 1 million */
    public final static long MN = K * K ;
    /** 1 billion */
    public final static long BN = MN * K;
    
    /** Number of 1 light year in Km */
    public final static long LIGHT_YEAR_IN_KM = 300000L * 60 * 60 * 24 * 365;
    
    /*
     * some test data 
     */
	public final static String STR = "STR";
	public final static String S1 = "S1";
	public final static String S2 = "S2";
	public final static String S3 = "S3";
	public final static String DEFAULT_STR = "DEFAULT_STR";
	
    public final static String FIRST_NAME = "FirstName";
    public final static String LAST_NAME = "LastName";
    
	public final static String HELLO_WORLD = "HELLO WORLD";
	public final static String WHATEVER = "WHATEVER";

	public final static String ARGUMENT_MUST_NOT_BE_NULL= "Argument must not be null.";
	public final static String ARGUMENT_MUST_NOT_BE_NULL_OR_EMPTY = "Argument must not be null or empty.";
    public final static String SHOULDv_THROWN_EXCEPTION = "Should'v thrown exception.";
    public final static String CODE_SHOULD_NOT_REACH_HERE = "Code should not reach here.";
    public final static String UNSUPPORTED_CASE = "Unsupported case.";
    public final static String NON_EXIST = "NON_EXIST";

    /** An immutable list as test data, contains integers 1, 2, 3 */
    public final static List<Integer> TEST_LIST_123;
    /** An immutable set as test data, contains integers 1, 2, 3 */
    public final static Set<Integer> TEST_SET_123;
    /** An immutable map as test data, contains entries S1=1, S2=2, S3=3 */
    public final static Map<String, Integer> TEST_MAP_123;
    /** An array as test data, contains int 1, 2, 3 */
    public final static int[] TEST_INT_ARRAY_123 = new int[] {1, 2, 3};

    static {  
    	TEST_LIST_123 = Collections.unmodifiableList(Lists.newArrayList(1, 2, 3));
    	TEST_SET_123 = Collections.unmodifiableSet(Sets.newHashSet(1, 2, 3));
    	TEST_MAP_123 = Collections.unmodifiableMap(MapBuilder.linkedHashMap(S1, 1).put(S2, 2).put(S3, 3).build());
    }
    
    public static final Class<NullPointerException> NPE = NullPointerException.class;
    public static final Class<IllegalStateException> ISE = IllegalStateException.class;
    public static final Class<UnsupportedOperationException> UOE = UnsupportedOperationException.class;
    public static final Class<IllegalArgumentException> IAE = IllegalArgumentException.class;
    public static final Class<IndexOutOfBoundsException> IOBE = IndexOutOfBoundsException.class;
    
    /*
     * some web resources
     */
    public final static String URL_WIKIPEDIA = "https://www.wikipedia.org/";
    
    /**
     * private constructor
     */
    private Constants() {
    	throw new RuntimeException();
    }

}

package com.github.peiatgithub.java.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author pei
 * @since 1.0
 */
public class Constants {

	/*
     *  Some general purpose commonly used constants 
     */
	public final static String EMPTY = "";
	public final static String SPACE = " ";

	public final static String COMMA = ",";
    public final static String SEMICOLON = ";";
    public final static String DASH = "-";
    public final static String UNDER_SCORE = "_";
    public final static String NULL_TEXT = "null";

    /** 1 K Bytes */
    public final static long KB = 1024L;
    /** 1 M Bytes */
    public final static long MB = KB * KB;
    /** 1 G Bytes */
    public final static long GB = MB * KB;

    /** 1 thousand */
    public final static long K = 1000L;
    /** 1 million */
    public final static long MN = K * K ;
    /** 1 billion */
    public final static long BN = MN * K;
    
    /** 1 light year in Km */
    public final static long LIGHT_YEAR_IN_KM = 300000L * 60 * 60 * 24 * 365;
    
    /*
     * some test data 
     */
	public final static String STR = "STR";
	public final static String DEFAULT_STR = "DEFAULT_STR";
	
    public final static String FIRST_NAME = "FirstName";
    public final static String LAST_NAME = "LastName";
    
	public final static String HELLO_WORLD = "HELLO WORLD";

    public final static String SHOULDv_THROWN_EXCEPTION = "Should'v thrown exception.";
    public final static String CODE_SHOULD_NOT_REACH_HERE = "Code should not reach here.";
    public final static String UNSUPPORTED_CASE = "Unsupported case.";
    
    public final static String S1 = "S1";
    public final static String S2 = "S2";
    public final static String S3 = "S3";

    /** an immutable list as test data, contains integers 1, 2, 3 */
    public final static List<Integer> TEST_LIST_123;
    /** an immutable set as test data, contains integers 1, 2, 3 */
    public final static Set<Integer> TEST_SET_123;
    /** an immutable map as test data, contains pairs S1=1, S2=2, S3=3 */
    public final static Map<String, Integer> TEST_MAP_123;
    /** an array as test data, contains int 1, 2, 3 */
    public final static int[] TEST_INT_ARRAY_123 = new int[] {1, 2, 3};

    private final static List<Integer> list123; 
    private final static Set<Integer> set123;
    private final static Map<String, Integer> map123;
    		
    static 
    { // initialization in the awkward way so that no need to introduce external libraries. 
    	list123 = new ArrayList<>();
    	list123.add(1);
    	list123.add(2);
    	list123.add(3);
    	TEST_LIST_123 = Collections.unmodifiableList(list123);
    	
    	set123 = new HashSet<>();
    	set123.add(1);
    	set123.add(2);
    	set123.add(3);
    	TEST_SET_123 = Collections.unmodifiableSet(set123);

    	map123 = new HashMap<>();
    	map123.put(S1, 1);
    	map123.put(S2, 2);
    	map123.put(S3, 3);
    	TEST_MAP_123 = Collections.unmodifiableMap(map123);
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

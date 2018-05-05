package com.github.peiatgithub.java.utils;

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
	public final static String STR = "STR";
	public final static String DEFAULT_STR = "DEFAULT_STR";
	
    public final static String FIRST_NAME = "FirstName";
    public final static String LAST_NAME = "LastName";
    
    public final static String SHOULDv_THROWN_EXCEPTION = "Should'v thrown exception.";
    
    public final static String S1 = "S1";
    public final static String S2 = "S2";
    public final static String S3 = "S3";

    public final static String COMMA = ",";
    public final static String SEMICOLON = ";";
    
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
    
    private Constants() {
    	throw new RuntimeException();
    }

}

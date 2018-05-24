package com.github.peiatgithub.java.utils.database;

/**
 * 
 * @author pei
 * @since 5.0
 */
public class AggregateFunction {
	
	public static String count(String column) {
		return String.format("COUNT(%s)", column);
	}
	public static String max(String column) {
		return String.format("MAX(%s)", column);
	}
	public static String min(String column) {
		return String.format("MIN(%s)", column);
	}
	public static String sum(String column) {
		return String.format("SUM(%s)", column);
	}
	public static String avg(String column) {
		return String.format("AVG(%s)", column);
	}

}

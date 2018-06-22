package com.github.peiatgithub.java.utils.database;

import static com.github.peiatgithub.java.utils.Utils.*;

/**
 * 
 * @author pei
 * @since 5.0
 */
public class AggregateFunction {
	
	public static String count(String column) {
		return str("COUNT({})", column);
	}
	public static String max(String column) {
		return str("MAX({})", column);
	}
	public static String min(String column) {
		return str("MIN({})", column);
	}
	public static String sum(String column) {
		return str("SUM({})", column);
	}
	public static String avg(String column) {
		return str("AVG({})", column);
	}

}

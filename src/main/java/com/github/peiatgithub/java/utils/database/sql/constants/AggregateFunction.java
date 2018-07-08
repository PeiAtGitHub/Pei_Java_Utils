package com.github.peiatgithub.java.utils.database.sql.constants;

import static com.github.peiatgithub.java.utils.Utils.*;

import org.apache.commons.lang3.StringUtils;

/**
 * This factory class helps generate SQL text for aggregate functions.
 * 
 * @author pei
 * @since 5.0
 */
public class AggregateFunction {

    public static String max(String column, String alias) {
        return getStatementText("MAX", column, alias);
    }

    public static String min(String column, String alias) {
        return getStatementText("MIN", column, alias);
    }

    public static String sum(String column, String alias) {
        return getStatementText("SUM", column, alias);
    }

    public static String avg(String column, String alias) {
        return getStatementText("AVG", column, alias);
    }

    public static String count(String column, String alias) {
        return getStatementText("COUNT", column, alias);
    }

    public static String count(String column) {
        return count(column, null);
    }

    public static String max(String column) {
        return max(column, null);
    }

    public static String min(String column) {
        return min(column, null);
    }

    public static String sum(String column) {
        return sum(column, null);
    }

    public static String avg(String column) {
        return avg(column, null);
    }

    /*
     * 
     */
    private static String getStatementText(String functionName, String column, String alias) {
        if (StringUtils.isBlank(alias)) {
            return str("{}({})", functionName, column);
        } else {
            return str("{}({}) AS {}", functionName, column, alias);
        }
    }

}
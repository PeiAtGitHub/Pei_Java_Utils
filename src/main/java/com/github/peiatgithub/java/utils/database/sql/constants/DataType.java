package com.github.peiatgithub.java.utils.database.sql.constants;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.commons.collections4.CollectionUtils;

import com.github.peiatgithub.java.utils.Encloser;

import lombok.Getter;

import static com.github.peiatgithub.java.utils.Constants.*;
import static com.github.peiatgithub.java.utils.Utils.*;

/**
 * <pre>
 * Represent the table column data type.
 * Use the factory methods to create instances of this class.
 * </pre>
 * @author pei
 * @since 5.0
 */
@Getter
public class DataType {

    private Type type;
    private final ArrayList<Number> args = new ArrayList<>();
    private final ArrayList<String> enumValues = new ArrayList<>(); // only for data type ENUM

    
    private DataType(Type type, Number...args) {
        this.type = type;
        Collections.addAll(this.args, args);
    }

    /**
     * Only for Type.ENUM
     */
    private DataType(String...enumValues) {
        this.type = Type.ENUM;
        Collections.addAll(this.enumValues, enumValues);
    }
    
    /*
     * factory methods
     */
    public static DataType ofChar(Number...args) {
        return new DataType(Type.CHAR, args);
    }
    public static DataType ofVarChar(Number...args) {
        return new DataType(Type.VARCHAR, args);
    }
    public static DataType ofInt(Number...args) {
        return new DataType(Type.INT, args);
    }
    public static DataType ofFloat(Number...args) {
        return new DataType(Type.FLOAT, args);
    }
    public static DataType ofDouble(Number...args) {
        return new DataType(Type.DOUBLE, args);
    }
    public static DataType ofDecimal(Number...args) {
        return new DataType(Type.DECIMAL, args);
    }
    public static DataType ofBlob(Number...args) {
        return new DataType(Type.BLOB, args);
    }
    public static DataType ofDate(Number...args) {
        return new DataType(Type.DATE, args);
    }
    public static DataType ofDateTime(Number...args) {
        return new DataType(Type.DATETIME, args);
    }
    public static DataType ofTimestamp(Number...args) {
        return new DataType(Type.TIMESTAMP, args);
    }
    public static DataType ofTime(Number...args) {
        return new DataType(Type.TIME, args);
    }
    public static DataType ofYear(Number...args) {
        return new DataType(Type.YEAR, args);
    }
    public static DataType ofEnum(String...enumValues) {
        return new DataType(enumValues);
    }
    
    /*
     * 
     */
    public String text() {

        if (this.type == null) {
            return EMPTY;
        }

        String result = this.type.text();

        if (this.type.equals(Type.ENUM)) {// e.g. ENUM('X','Y','Z')
            if (!CollectionUtils.isEmpty(enumValues)) {
                result += encloseString(listToString(this.enumValues, COMMA, Encloser.SINGLE), Encloser.PARENTHESES);
            }
        } else {// e.g. VARCHAR(32), DOUBLE(9,3)
            if (!CollectionUtils.isEmpty(args)) {
                result += encloseString(listToString(this.args, COMMA, null), Encloser.PARENTHESES);
            }
        }

        return result;
    }

    /**
     * Table columns data type
     * @author pei
     * @since 5.0
     */
    public enum Type {

        // @formatter:off
        CHAR("CHAR"), 
        VARCHAR("VARCHAR"), 
        INT("INT"), 
        FLOAT("FLOAT"), 
        DOUBLE("DOUBLE"), 
        DECIMAL("DECIMAL"), 
        BLOB("BLOB"), 
        ENUM("ENUM"), // e.g. ENUM('X','Y','Z')
        DATE("DATE()"), 
        DATETIME("DATETIME()"), 
        TIMESTAMP("TIMESTAMP()"),
        TIME("TIME()"),
        YEAR("YEAR()");
        // @formatter:on

        private String text;

        Type(String text) {
            this.text = text;
        }

        public String text() {
            return this.text;
        }

    }

}

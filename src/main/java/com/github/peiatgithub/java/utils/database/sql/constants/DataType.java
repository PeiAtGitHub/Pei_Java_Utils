package com.github.peiatgithub.java.utils.database.sql.constants;

import java.util.ArrayList;

import org.apache.commons.collections4.CollectionUtils;

import com.github.peiatgithub.java.utils.Encloser;

import lombok.Getter;

import static com.github.peiatgithub.java.utils.Constants.*;
import static com.github.peiatgithub.java.utils.Utils.*;

/**
 * Represent the DB columns data type
 * 
 * @author pei
 * @since 5.0
 */
@Getter
public class DataType {

    private Type type;
    private final ArrayList<Number> args = new ArrayList<>();
    private final ArrayList<String> enumValues = new ArrayList<>(); // only for data type ENUM

    DataType(Type type) {
        this.type = type;
    }

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
     * DB columns data type
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
        TIMESTAMP("TIMESTAMP()");
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

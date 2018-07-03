package com.github.peiatgithub.java.utils.database.sql;

import static com.github.peiatgithub.java.utils.Constants.*;
import static com.github.peiatgithub.java.utils.Utils.*;
import static com.github.peiatgithub.java.utils.database.sql.constants.SqlFamily.ORACLE;

import com.github.peiatgithub.java.utils.Encloser;
import com.github.peiatgithub.java.utils.database.sql.constants.SqlFamily;

import static com.github.peiatgithub.java.utils.Encloser.PARENTHESES;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author pei
 *
 * @since 5.0
 */
@NoArgsConstructor
public class SqlCondition {

    private StringBuilder condition = new StringBuilder();

    /**
     * <pre>
     * composite is false if this is a single condition, 
     * e.g. CustomerName = "Tom"
     * 
     * composite is true if this is a combined condition, 
     * e.g. CustomerName = "Tom" OR CustomerName = "Jerry"
     * e.g. NOT CustomerName = "Tom"
     * </pre>
     */
    @Getter
    @Setter
    private boolean isComposite = false;

    public SqlCondition(String operand1) {
        this.condition.append(operand1).append(SPACE);
    }

    public SqlCondition equals(String operand2) {
        return append(str("= '{}'", operand2));
    }

    public SqlCondition equal(Number operand2) {
        return append(str("= {}", operand2));
    }

    public SqlCondition notEqual(String operand2) {
        return append(str("<> '{}'", operand2));
    }

    public SqlCondition notEqual(Number operand2) {
        return append(str("<> {}", operand2));
    }

    public SqlCondition greaterThan(String operand2) {
        return append(str("> '{}'", operand2));
    }

    public SqlCondition greaterThan(Number operand2) {
        return append("> " + operand2);
    }

    public SqlCondition lessThan(String operand2) {
        return append(str("< '{}'", operand2));
    }

    public SqlCondition lessThan(Number operand2) {
        return append("< " + operand2);
    }

    public SqlCondition greaterThanOrEqual(String operand2) {
        return append(str(">= '{}'", operand2));
    }

    public SqlCondition greaterThanOrEqual(Number operand2) {
        return append(">= " + operand2);
    }

    public SqlCondition lessThanOrEqual(String operand2) {
        return append(str("<= '{}'", operand2));
    }

    public SqlCondition lessThanOrEqualTo(Number operand2) {
        return append("<= " + operand2);
    }

    public SqlCondition between(String from, String to) {
        return append(str("BETWEEN {} AND {} ", from, to));
    }
    public SqlCondition between(Number from, Number to) {
        return append(str("BETWEEN {} AND {} ", from, to));
    }

    public SqlCondition like(String pattern) {
        return append(str("LIKE '{}'", pattern));
    }

    public SqlCondition inValues(String... values) {
        return append(str("IN ({}) ", arrayToString(values, ", ", Encloser.SINGLE)));
    }

    public SqlCondition notInValues(String... values) {
        return append(str("NOT IN ({}) ", arrayToString(values, ", ", Encloser.SINGLE)));
    }

    public SqlCondition inSelectResults(String selectStatement) {
        return append(str("IN ({}) ", selectStatement));
    }

    /*
     * 
     */
    
    public SqlCondition and(SqlCondition condition2) {
        if (isComposite()) {
            encloseWithParentheses(this.condition);
        }
        append(str(" AND {}", getConditionString(condition2)));

        setComposite(true);

        return this;
    }

    public SqlCondition or(SqlCondition condition2) {

        if (isComposite()) {
            encloseWithParentheses(this.condition);
        }
        append(str(" OR {}", getConditionString(condition2)));

        setComposite(true);

        return this;

    }

    public SqlCondition not(SqlCondition condition) {
        append(str("NOT {}", getConditionString(condition)));

        setComposite(true);

        return this;

    }

    public SqlCondition isNull() {
        return append(str("IS NULL"));
    }

    public SqlCondition isNotNull() {
        return append(str("IS NOT NULL"));
    }

    //
    public String build() {
        return this.condition.toString();
    }

    /*
     * private utils
     */
    private void encloseWithParentheses(StringBuilder sb) {
        sb.insert(0, PARENTHESES.begin()).append(PARENTHESES.end());
    }

    /**
     * If the condition is composite, the result String will be enclosed with
     * parentheses
     */
    private String getConditionString(SqlCondition condition) {

        if (condition.isComposite()) {
            return encloseString(condition.build(), PARENTHESES);
        } else {
            return condition.build();
        }

    }
    

    /**
     * Append text to the condition.
     */
    private SqlCondition append(String text) {
        this.condition.append(text);
        return this;
    }

    /**
     * 
     * @author pei
     * @since 5.0
     */
    @NoArgsConstructor
    private class ValuePatternBuilder {

        private SqlFamily sqlFamily = ORACLE; // default
        private StringBuilder pattern = new StringBuilder(EMPTY);

        public ValuePatternBuilder(SqlFamily sqlFamily) {
            this.sqlFamily = sqlFamily;
        }

        /**
         * Append text to the value pattern.
         */
        public ValuePatternBuilder text(String text) {
            this.pattern.append(text);
            return this;
        }

        /**
         * <pre>
         * Set the SQL Family.
         * The wild-cards may depend on the SQL family.
         * </pre> 
         */
        public ValuePatternBuilder sqlFamily(SqlFamily sf) {
            this.sqlFamily = sf;
            return this;
        }
        
        /**
         * 1 any char
         */
        public ValuePatternBuilder oneAnyChar() {
            switch (sqlFamily) {
            case MS_ACCESS:
            case SQL_SERVER:
                return text(QUESTION_MARK);
            case ORACLE:
            case MY_SQL:
                return text(UNDER_SCORE);
            default:
                throw new RuntimeException(UNSUPPORTED_CASE);
            }
        }

        /**
         *  0, 1, or multiple chars
         */
        public ValuePatternBuilder anyTimesAnyChar() {
            return text(PERCENTAGE);
        }
        

        /**
         * returns the build result String 
         */
        public String build() {
            return this.pattern.toString();
        }
        
    }


}

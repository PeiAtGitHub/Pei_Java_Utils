package com.github.peiatgithub.java.utils.database.sql;

import static com.github.peiatgithub.java.utils.Constants.*;
import static com.github.peiatgithub.java.utils.Utils.*;
import static com.github.peiatgithub.java.utils.database.sql.constants.SqlFamily.ORACLE;

import com.github.peiatgithub.java.utils.database.sql.constants.SqlFamily;

import static com.github.peiatgithub.java.utils.Encloser.PARENTHESES;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author pei
 *
 * @since 5.0
 */
public class ConditionBuilder {

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

    public ConditionBuilder(String operand1) {
        this.condition.append(operand1);
    }

    public ConditionBuilder equal(String operand2) {
        return append(str(" = '{}'", operand2));
    }

    public ConditionBuilder equal(Number operand2) {
        return append(str(" = {}", operand2));
    }

    public ConditionBuilder notEqual(String operand2) {
        return append(str(" <> '{}'", operand2));
    }

    public ConditionBuilder notEqual(Number operand2) {
        return append(str(" <> {}", operand2));
    }

    public ConditionBuilder greaterThan(String operand2) {
        return append(str(" > '{}'", operand2));
    }

    public ConditionBuilder greaterThan(Number operand2) {
        return append(" > " + operand2);
    }

    public ConditionBuilder lessThan(String operand2) {
        return append(str(" < '{}'", operand2));
    }

    public ConditionBuilder lessThan(Number operand2) {
        return append(" < " + operand2);
    }

    public ConditionBuilder greaterThanOrEqual(String operand2) {
        return append(str(" >= '{}'", operand2));
    }

    public ConditionBuilder greaterThanOrEqual(Number operand2) {
        return append(" >= " + operand2);
    }

    public ConditionBuilder lessThanOrEqual(String operand2) {
        return append(str(" <= '{}'", operand2));
    }

    public ConditionBuilder lessThanOrEqualTo(Number operand2) {
        return append(" <= " + operand2);
    }

    public ConditionBuilder between(String from, String to) {
        return append(str(" BETWEEN {} AND {} ", from, to));
    }

    public ConditionBuilder like(ValuePatternBuilder vpb) {
        return append(" LIKE " + vpb.build());
    }

    public ConditionBuilder inValues(String... values) {
        return append(str("IN ({}) ", join(values, COMMA)));
    }

    public ConditionBuilder inSelectResults(String selectStatement) {
        return append(str("IN ({}) ", selectStatement));
    }

    /*
     * 
     */
    
    public ConditionBuilder and(ConditionBuilder condition2) {
        if (isComposite()) {
            encloseWithParentheses(this.condition);
        }
        append(str(" AND {}", getConditionString(condition2)));

        setComposite(true);

        return this;
    }

    public ConditionBuilder or(ConditionBuilder condition2) {

        if (isComposite()) {
            encloseWithParentheses(this.condition);
        }
        append(str(" OR {}", getConditionString(condition2)));

        setComposite(true);

        return this;

    }

    public ConditionBuilder not(ConditionBuilder condition) {
        append(str(" NOT {}", getConditionString(condition)));

        setComposite(true);

        return this;

    }

    public ConditionBuilder isNull() {
        return append(str(" IS NULL"));
    }

    public ConditionBuilder isNotNull() {
        return append(str(" IS NOT NULL"));
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
    private String getConditionString(ConditionBuilder condition) {

        if (condition.isComposite()) {
            return encloseString(condition.build(), PARENTHESES);
        } else {
            return condition.build();
        }

    }
    

    /**
     * Append text to the condition.
     */
    private ConditionBuilder append(String text) {
        this.condition.append(text);
        return this;
    }

    /**
     * 
     * @author pei
     * @since 5.0
     */
    private class ValuePatternBuilder {

        private SqlFamily sqlFamily = ORACLE; // default
        private StringBuilder pattern = new StringBuilder(EMPTY);

        public ValuePatternBuilder() {
        }
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

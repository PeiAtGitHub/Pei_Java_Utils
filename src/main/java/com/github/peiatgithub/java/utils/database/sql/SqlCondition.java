package com.github.peiatgithub.java.utils.database.sql;

import static com.github.peiatgithub.java.utils.Constants.SPACE;
import static com.github.peiatgithub.java.utils.Encloser.PARENTHESES;
import static com.github.peiatgithub.java.utils.Utils.arrayToString;
import static com.github.peiatgithub.java.utils.Utils.encloseString;
import static com.github.peiatgithub.java.utils.Utils.str;

import com.github.peiatgithub.java.utils.Encloser;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents the condition of the WHERE clause.
 * 
 * @author pei
 * @since 5.0
 */
@NoArgsConstructor
@Setter
@Getter
public class SqlCondition extends LastStep {

    private StringBuilder condition = new StringBuilder();

    /**
     * <pre>
     * this flag is false if this is a single condition, 
     * e.g. CustomerName = "Tom"
     * 
     * this flag is true if this is a combined condition, 
     * e.g. CustomerName = "Tom" OR CustomerName = "Jerry"
     * e.g. NOT CustomerName = "Tom"
     * </pre>
     */
    private boolean isComposite = false;

    public SqlCondition(String operand1) {
        this.condition.append(operand1 + SPACE);
    }

    public SqlCondition(String operand1, SqlBuilderContent sbc) {
        this.condition.append(operand1 + SPACE);
        super.setSbc(sbc);
    }

    public SqlCondition equalTo(String operand2) {
        if (operand2.contains(".")) {
            return append(str("= {}", operand2));
        } else {
            return append(str("= '{}'", operand2));
        }
    }

    public SqlCondition equalTo(Number operand2) {
        return append(str("= {}", operand2));
    }

    public SqlCondition notEqualTo(String operand2) {
        return append(str("<> '{}'", operand2));
    }

    public SqlCondition notEqualTo(Number operand2) {
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

    public SqlCondition greaterThanOrEqualTo(String operand2) {
        return append(str(">= '{}'", operand2));
    }

    public SqlCondition greaterThanOrEqualTo(Number operand2) {
        return append(">= " + operand2);
    }

    public SqlCondition lessThanOrEqualTo(String operand2) {
        return append(str("<= '{}'", operand2));
    }

    public SqlCondition lessThanOrEqualTo(Number operand2) {
        return append("<= " + operand2);
    }

    public SqlCondition between(String from, String to) {
        return append(str("BETWEEN {} AND {}", from, to));
    }

    public SqlCondition between(Number from, Number to) {
        return append(str("BETWEEN {} AND {}", from, to));
    }

    public SqlCondition like(String pattern) {
        return append(str("LIKE '{}'", pattern));
    }

    public SqlCondition inValues(String... values) {
        return append(str("IN ({})", arrayToString(values, ", ", Encloser.SINGLE)));
    }

    public SqlCondition notInValues(String... values) {
        return append(str("NOT IN ({})", arrayToString(values, ", ", Encloser.SINGLE)));
    }

    public SqlCondition inSelectResults(String selectStatement) {
        return append(str("IN ({})", selectStatement));
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

    /**
     * EXISTS ...
     */
    public SqlCondition exist(String sql) {
        return append("EXISTS " + encloseString(sql, Encloser.PARENTHESES));
    }

    /**
     * <pre>
     * returns this condition's text String.
     * to return the entire SQL String, call {@link #build()}
     * </pre>
     */
    public String buildConditionString() {
        return this.condition.toString();
    }

    /*
     * private utils
     */

    private void encloseWithParentheses(StringBuilder sb) {
        sb.insert(0, PARENTHESES.begin()).append(PARENTHESES.end());
    }

    /**
     * If the condition is composite, 
     * the result String will be enclosed with parentheses
     */
    private String getConditionString(SqlCondition condition) {
        if (condition.isComposite()) {
            return encloseString(condition.buildConditionString(), PARENTHESES);
        } else {
            return condition.buildConditionString();
        }
    }

    private SqlCondition append(String text) {
        this.condition.append(text);
        return this;
    }

}

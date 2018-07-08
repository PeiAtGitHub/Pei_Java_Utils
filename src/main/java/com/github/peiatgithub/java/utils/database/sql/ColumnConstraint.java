package com.github.peiatgithub.java.utils.database.sql;

import static com.github.peiatgithub.java.utils.Utils.*;
import com.github.peiatgithub.java.utils.database.sql.constants.Constraints;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * This class represents the Constraint on a table column.
 * The instance of this class will reside in a TableColumn instance.
 * Please use the static factory methods to create corresponding 
 * constraint instances.
 * </pre>
 * 
 * @author pei
 * @since 5.0
 */
@Getter
@Setter(AccessLevel.PRIVATE)
public class ColumnConstraint extends Constraints {

    /*
     * constructors
     */
    private ColumnConstraint() {
    }

    private ColumnConstraint(String type) {
        this.type = type;
    }

    /*
     * Factory methods.
     */

    /** create a NOT NULL constraint */
    static public ColumnConstraint notNull() {
        return new ColumnConstraint(NOT_NULL);
    }

    /** create a UNIQUE constraint */
    static public ColumnConstraint unique() {
        return new ColumnConstraint(UNIQUE);
    }

    /** create a PRIMARY KEY constraint */
    static public ColumnConstraint primaryKey() {
        return new ColumnConstraint(PRIMARY_KEY);
    }

    /** create a FOREIGN KEY constraint */
    static public ColumnConstraint foreignKey(String fkTable, String fkColumn) {
        ColumnConstraint c = new ColumnConstraint(FOREIGN_KEY);
        c.setFkTable(fkTable);
        c.setFkColumn(fkColumn);
        return c;
    }

    /** create a CHECK constraint */
    static public ColumnConstraint check(String condition) {
        ColumnConstraint c = new ColumnConstraint(CHECK);
        c.setCheckCondition(condition);
        return c;
    }

    /** create a DEFAULT constraint */
    static public ColumnConstraint defaultWithValue(Object defaultValue) {
        ColumnConstraint c = new ColumnConstraint(DEFAULT);
        c.setDefaultValue(defaultValue);
        return c;
    }

    /**
     * @returns the SQL string of the constraint
     */
    public String getText() {

        String type = getType();

        if (type.equals(FOREIGN_KEY)) {
            return str("{} REFERENCES {}({})", type, safeStr(getFkTable()), safeStr(getFkColumn()));
        } else if (type.equals(CHECK)) {
            return str("{} ({})", type, getCheckCondition());
        } else if (type.equals(DEFAULT)) {
            Object v = getDefaultValue();
            if (v instanceof Number) {
                return str("{} {}", type, v.toString());
            } else {
                return str("{} '{}'", type, v.toString());
            }
        } else {
            return type;
        }

    }

}

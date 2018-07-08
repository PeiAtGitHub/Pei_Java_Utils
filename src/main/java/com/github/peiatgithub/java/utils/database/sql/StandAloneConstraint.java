package com.github.peiatgithub.java.utils.database.sql;

import static com.github.peiatgithub.java.utils.Utils.*;
import com.github.peiatgithub.java.utils.database.sql.constants.Constraints;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * This class represents the Constraint defined separately after table column definitions.
 * Use the static factory methods to create corresponding constraint instances.
 * </pre>
 * 
 * @author pei
 * @since 5.0
 */
@Getter
@Setter(AccessLevel.PRIVATE)
public class StandAloneConstraint extends Constraints{

    /** the name of the constraint */
    private String name = "";

    /** the columns this constraint applies to */
    private String[] columns;

    /*
     * constructors
     */
    private StandAloneConstraint() {
    }

    private StandAloneConstraint(String type) {
        this.type = type;
    }

    private StandAloneConstraint(String type, String name, String... columns) {
        this.type = type;
        this.name = name;
        this.columns = columns;
    }

    /*
     * Factory methods.
     */

    /** create a UNIQUE constraint */
    static public StandAloneConstraint unique(String name, String... columns) {
        return new StandAloneConstraint(UNIQUE, name, columns);
    }

    /** create a PRIMARY KEY constraint */
    static public StandAloneConstraint primaryKey(String name, String... columns) {
        return new StandAloneConstraint(PRIMARY_KEY, name, columns);
    }

    /** create a FOREIGN KEY constraint */
    static public StandAloneConstraint foreignKey(String name, String fkTable, String fkColumn, String... columns) {
        StandAloneConstraint c = new StandAloneConstraint(FOREIGN_KEY, name, columns);
        c.setFkTable(fkTable);
        c.setFkColumn(fkColumn);
        return c;
    }

    /** create a CHECK constraint */
    static public StandAloneConstraint check(String name, String condition) {
        StandAloneConstraint c = new StandAloneConstraint(CHECK);
        c.setName(name);
        c.setCheckCondition(condition);
        return c;
    }

    /**
     * @return the SQL string of the constraint
     */
    public String getText() {

        String type = getType();

        if (type.equals(FOREIGN_KEY)) {
            return str("{} ({}) REFERENCES {}({})", type, arrayToString(columns, ", ", null), safeStr(getFkTable()),
                    safeStr(getFkColumn()));
        } else if (type.equals(CHECK)) {
            return str("{} ({})", type, getCheckCondition());
        } else {
            return str("{} ({})", type, arrayToString(columns, ", ", null));
        }

    }

}

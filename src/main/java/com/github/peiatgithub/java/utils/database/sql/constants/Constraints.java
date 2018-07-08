package com.github.peiatgithub.java.utils.database.sql.constants;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Table column constraints
 * 
 * @author pei
 * @since 5.0
 */
@Getter
@Setter(AccessLevel.PROTECTED)
public abstract class Constraints {
    
    public static final String DEFAULT = "DEFAULT";
    public static final String CHECK = "CHECK";
    public static final String FOREIGN_KEY = "FOREIGN KEY";
    public static final String PRIMARY_KEY = "PRIMARY KEY";
    public static final String UNIQUE = "UNIQUE";
    public static final String NOT_NULL = "NOT NULL";
    
    /** the type of the constraint, e.g. NOT NULL, PRIMARY KEY */
    protected String type;

    /** foreign key referenced table */
    protected String fkTable;
    /** foreign key referenced column */
    protected String fkColumn;

    /** only for DEFAULT constraint */
    protected Object defaultValue;
    /** only for CHECK constraint */
    protected String checkCondition;

    abstract public String getText();

}

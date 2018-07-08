package com.github.peiatgithub.java.utils.database.sql;

import com.github.peiatgithub.java.utils.database.sql.constants.DataType;

import lombok.Getter;

/**
 * Represents a table column
 * @author pei
 * @since 5.0
 */
@Getter
public class TableColumn {

    private String name;
    private DataType dataType;
    private ColumnConstraint[] constraints;

    public TableColumn(String name) {
        this.name = name;
    }

    public TableColumn(String name, DataType dataType) {
        this.name = name;
        this.dataType = dataType;
    }

    public TableColumn(String name, DataType dataType, ColumnConstraint... constraints) {
        this.name = name;
        this.dataType = dataType;
        this.constraints = constraints;
    }


}

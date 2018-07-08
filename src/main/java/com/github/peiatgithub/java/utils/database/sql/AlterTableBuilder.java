package com.github.peiatgithub.java.utils.database.sql;

import static com.github.peiatgithub.java.utils.Utils.*;

/**
 * This builder is the return type of SqlBuilder.alterTable() method.
 * 
 * @author pei
 * @since 5.0
 */
public class AlterTableBuilder extends LastStep {

    public AlterTableBuilder(SqlBuilderContent sbc) {
        super.setSbc(sbc);
    }

    /**
     * ADD ...
     */
    public SqlBuilderContent addColumn(TableColumn column) {
        sbc.getSqlSb().append(str("ADD {} {}", column.getName(), column.getDataType().text()));
        return sbc;
    }

    /**
     * <pre>
     * MODIFY COLUMN ...
     * This method only implemented the syntax for MY_SQL.
     * TODO: add other SQL family syntax.
     * </pre>
     */
    public SqlBuilderContent modifyColumn(TableColumn column) {
        sbc.getSqlSb().append(str("MODIFY COLUMN {} {}", column.getName(), column.getDataType().text()));
        return sbc;
    }

    /**
     * DROP COLUMN ...
     */
    public SqlBuilderContent dropColumn(String columnName) {
        sbc.getSqlSb().append("DROP COLUMN " + columnName);
        return sbc;
    }

}

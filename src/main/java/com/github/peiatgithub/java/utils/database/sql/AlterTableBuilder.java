package com.github.peiatgithub.java.utils.database.sql;

import static com.github.peiatgithub.java.utils.Utils.*;

/**
 * This builder is returned by the SqlBuilder.alterTable method.
 * 
 * @author pei
 * @since 5.0
 */
public class AlterTableBuilder extends LastStep {

    public AlterTableBuilder(SqlBuilderContent sbc) {
        super.setSbc(sbc);
    }

    public SqlBuilderContent addColumn(TableColumn column) {
        sbc.getSqlSb().append(str("ADD {} {}", column.getName(), column.getDataType().text()));
        return sbc;
    }

    public SqlBuilderContent modifyColumn(TableColumn column) {
        sbc.getSqlSb().append(str("MODIFY COLUMN {} {}", column.getName(), column.getDataType().text()));
        return sbc;
    }

    public SqlBuilderContent dropColumn(String columnName) {
        sbc.getSqlSb().append("DROP COLUMN " + columnName);
        return sbc;
    }

}

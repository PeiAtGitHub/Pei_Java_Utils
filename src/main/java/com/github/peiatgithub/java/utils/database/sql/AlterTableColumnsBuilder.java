package com.github.peiatgithub.java.utils.database.sql;

import static com.github.peiatgithub.java.utils.Constants.SPACE;

import com.github.peiatgithub.java.utils.database.sql.constants.DataType;

import lombok.AllArgsConstructor;

/**
 * @author pei
 * @since 5.0
 */
@AllArgsConstructor
public class AlterTableColumnsBuilder extends LastStep{

    public AlterTableColumnsBuilder(SqlBuilderContent sbc) {
        super.setSbc(sbc);
    }

    public SqlBuilderContent addColumn(String column, DataType dataType) {
        sbc.getSqlSb().append("ADD ").append(column + SPACE).append(dataType.text());
        return sbc;
    }

    public SqlBuilderContent modifyColumn(String column, DataType dataType) {
        sbc.getSqlSb().append("MODIFY COLUMN ").append(column + SPACE).append(dataType.text());
        return sbc;
    }

    public SqlBuilderContent dropColumn(String column) {
        sbc.getSqlSb().append("DROP COLUMN ").append(column);
        return sbc;
    }

}

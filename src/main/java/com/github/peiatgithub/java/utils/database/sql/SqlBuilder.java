package com.github.peiatgithub.java.utils.database.sql;

import static com.github.peiatgithub.java.utils.Utils.*;
import static com.github.peiatgithub.java.utils.Constants.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.github.peiatgithub.java.utils.Encloser;
import com.github.peiatgithub.java.utils.database.sql.constants.Constraints;
import com.github.peiatgithub.java.utils.database.sql.constants.JoinType;
import com.github.peiatgithub.java.utils.database.sql.constants.SqlFamily;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <pre>
 * This Builder class helps build BASIC sql statement Strings.
 * Considering the complexity/flexibility/diversity of SQL syntax, 
 * this builder's capability and feature have a lot space to improve.
 * 
 * The complexity of the sub-builders(E.g. FromBuilder, WhereBuilder, and etc.)
 * and their dependencies is only for improving the usability of SqlBuilder APIs.
 * </pre>
 * 
 * @author pei
 * @since 5.0
 */
@Getter
public class SqlBuilder {

    final SqlBuilderContent sbc = new SqlBuilderContent();

    /**
     * <pre>
     * Set the SQL family to one of the ENUMs of SqlFamily.
     * The default is "MY_SQL".
     * </pre>
     */
    public SqlBuilder sqlFamily(SqlFamily sqlFamily) {
        sbc.setSqlFamily(sqlFamily);
        return this;
    }

    /**
     * <pre>
     * SELECT ...
     * null input will be taken as empty in the result SQL String
     * </pre>
     */
    public FromBuilder select(String... columns) {
        if (columns == null) {
            columns = new String[] { EMPTY };
        }
        if (sbc.getSqlSb().toString().startsWith("SELECT")) {
            sbc.getSqlSb().append(", " + join(columns, ", ")).append(SPACE);
        } else {
            sbc.getSqlSb().append("SELECT ").append(join(columns, ", ")).append(SPACE);
        }

        return new FromBuilder(sbc);
    }

    /**
     * <pre>
     * SELECT DISTINCT ... null input will be taken as empty in the result SQL
     * String
     */
    public FromBuilder selectDistinct(String... columns) {
        if (columns == null) {
            columns = new String[] { EMPTY };
        }
        if (sbc.getSqlSb().toString().startsWith("SELECT")) {
            sbc.getSqlSb().append(", " + join(columns, ", ")).append(SPACE);
        } else {
            sbc.getSqlSb().append("SELECT DISTINCT ").append(join(columns, ", ")).append(SPACE);
        }
        return new FromBuilder(sbc);
    }

    /**
     * A convenient alternative to calling select("*").
     */
    public FromBuilder selectAll() {
        return select("*");
    }

    /**
     * A convenient alternative to calling selectDistinct("*").
     */
    public FromBuilder selectDistinctAll() {
        return selectDistinct("*");
    }

    /**
     * SELECT with limited number of result
     * 
     * @param maxRowNumber
     *            the max number of rows in the query result
     */
    public FromBuilder select(int maxRowNumber, String... columns) {
        select(columns);
        sbc.setMaxNumOfRows(maxRowNumber);
        return new FromBuilder(sbc);
    }

    /**
     * null input will be taken as empty in the result SQL String
     */
    public FromBuilder selectDistinct(int maxRowNumber, String... columns) {
        selectDistinct(columns);
        sbc.setMaxNumOfRows(maxRowNumber);
        return new FromBuilder(sbc);
    }

    /**
     * Alternative to calling select(maxRowNumber, "*")
     */
    public FromBuilder selectAll(int maxRowNumber) {
        return select(maxRowNumber, "*");
    }

    /**
     * Alternative to calling selectDistinct(maxRowNumber, "*")
     */
    public FromBuilder selectDistinctAll(int maxRowNumber) {
        return selectDistinct(maxRowNumber, "*");
    }

    /**
     * INSERT INTO ...
     */
    public SqlBuilderContent insertInto(String table, Map<String, String> columnsValues) {

        Pair<List<String>, List<String>> pcv = mapToKvLists(columnsValues);

        sbc.getSqlSb().append(str("INSERT INTO {} ({}) VALUES ({})", table, listToString(pcv.getLeft(), ", ", null),
                listToString(pcv.getRight(), ", ", Encloser.SINGLE)));

        return sbc;

    }

    /**
     * UPDATE ...
     */
    public SqlBuilderContent updateTable(String table, Map<String, String> columnsValues,
            SqlCondition conditionBuilder) {

        StringBuilder tmp = new StringBuilder();
        for (String column : columnsValues.keySet()) {
            tmp.append(str("{} = '{}', ", column, columnsValues.get(column)));
        }
        sbc.getSqlSb().append(str("UPDATE {} SET {} WHERE {}", table, StringUtils.removeEnd(tmp.toString(), ", "),
                conditionBuilder.buildConditionString()));

        return sbc;
    }

    /**
     * DELETE FROM ...
     */
    public WhereBuilder deleteRowsFrom(String table) {
        sbc.getSqlSb().append("DELETE FROM ").append(table + SPACE);
        return new WhereBuilder(sbc);
    }

    /**
     * set the max number of rows in the query result
     */
    public SqlBuilder limitOutputRows(int maxNumOfRows) {
        sbc.setMaxNumOfRows(maxNumOfRows);
        return this;
    }

    /**
     * <pre>
     * CREATE TABLE ...
     * Constraints can be defined both inside column instances
     * and separately AFTER defining columns,
     * it's up to the users to avoid duplicates.
     * </pre>
     */
    public SqlBuilderContent createTable(String table, ArrayList<TableColumn> columns,
            StandAloneConstraint... constraints) {

        sbc.getSqlSb().append("CREATE TABLE ").append(table).append(SPACE);

        String allColumnStr = EMPTY;
        if (sbc.getSqlFamily().equals(SqlFamily.MY_SQL)) {
            for (TableColumn clmn : columns) {
                allColumnStr += clmn.getName() + SPACE + clmn.getDataType().text();
                ColumnConstraint[] cntrs = clmn.getConstraints();
                if (ArrayUtils.isNotEmpty(cntrs)) {
                    for (ColumnConstraint c : cntrs) {
                        String type = c.getType();
                        if (type.equals(Constraints.NOT_NULL) || type.equals(Constraints.DEFAULT)) {
                            allColumnStr += SPACE + c.getText();
                        }
                    }
                }
                allColumnStr += ", ";
            }
            for (TableColumn clmn : columns) {
                ColumnConstraint[] cntrs = clmn.getConstraints();
                if (ArrayUtils.isNotEmpty(cntrs)) {
                    for (ColumnConstraint c : cntrs) {
                        String type = c.getType();
                        if (!(type.equals(Constraints.NOT_NULL) || type.equals(Constraints.DEFAULT))) {
                            if (type.equals(Constraints.FOREIGN_KEY)) {
                                allColumnStr += str("{} ({}) REFERENCES {}({})", Constraints.FOREIGN_KEY,
                                        clmn.getName(), c.getFkTable(), c.getFkColumn());
                            } else if (type.equals(Constraints.CHECK)) {
                                allColumnStr += c.getText();
                            } else {
                                allColumnStr += str("{} ({})", c.getText(), clmn.getName());
                            }
                            allColumnStr += ", ";
                        }
                    }
                }
            }
        } else {// SQL family not MY_SQL
            for (TableColumn clmn : columns) {
                allColumnStr += clmn.getName() + SPACE + clmn.getDataType().text();
                ColumnConstraint[] cntrs = clmn.getConstraints();
                if (ArrayUtils.isNotEmpty(cntrs)) {
                    for (ColumnConstraint c : cntrs) {
                        allColumnStr += SPACE + c.getText();
                    }
                }
                allColumnStr += ", ";
            }
        }

        /*
         * constraints definitions after column definitions
         */
        if (ArrayUtils.isNotEmpty(constraints)) {
            for (StandAloneConstraint c : constraints) {
                allColumnStr += str("CONSTRAINT {} {}", c.getName(), c.getText()) + ", ";
            }
        }
        allColumnStr = encloseString(StringUtils.removeEnd(allColumnStr, ", "), Encloser.PARENTHESES);
        sbc.getSqlSb().append(allColumnStr);

        return sbc;
    }

    /**
     * ALTER TABLE ...
     */
    public AlterTableBuilder alterTable(String table) {
        sbc.getSqlSb().append("ALTER TABLE ").append(table).append(SPACE);
        return new AlterTableBuilder(sbc);
    }

    /**
     * DROP TABLE ...
     */
    public SqlBuilderContent dropTable(String table) {
        sbc.getSqlSb().append("DROP TABLE ").append(table);
        return sbc;
    }

    /**
     * CREATE DATABASE ...
     */
    public SqlBuilderContent createDataBase(String dbName) {
        sbc.getSqlSb().append("CREATE DATABASE ").append(dbName);
        return sbc;
    }

    /**
     * DROP DATABASE ...
     */
    public SqlBuilderContent dropDataBase(String dbName) {
        sbc.getSqlSb().append("DROP DATABASE ").append(dbName);
        return sbc;
    }

    /**
     * @return the result SQL String
     */
    public String build() {
        return sbc.build();
    }

    /*
     * TODO: Alias is pretty complicated to handle, 
     * leave it for the future improvements ...
     */

    /**
     * An object of this class will be returned by SqlBuilder.select* methods.
     * 
     * @author pei
     * @since 5.0
     */
    @AllArgsConstructor
    public class FromBuilder {

        SqlBuilderContent sbc;

        /**
         * FROM ...
         */
        public WhereBuilder from(String table) {
            sbc.getSqlSb().append("FROM ").append(table).append(SPACE);
            return new WhereBuilder(sbc);
        }

        /**
         * FROM ... joined table on specified column
         */
        public WhereBuilder from(String table1, JoinType join, String table2, String column) {
            sbc.getSqlSb().append("FROM ").append(str("{} {} {} ", table1, join.text(), table2))
                    .append(str("ON {}.{} = {}.{}", table1, column, table2, column));
            return new WhereBuilder(sbc);
        }

    }

    /**
     * 
     * @author pei
     * @since 5.0
     */
    public class WhereBuilder extends LastStep {

        public WhereBuilder(SqlBuilderContent sbc) {
            super.setSbc(sbc);
        }

        public SqlBuilderContent where(SqlCondition conditionBuilder) {
            sbc.getSqlSb().append("WHERE ");
            sbc.setWhereCondition(conditionBuilder);
            return sbc;
        }

        public SqlCondition where(String operand1) {
            sbc.getSqlSb().append("WHERE ");
            SqlCondition sc = new SqlCondition(operand1, sbc);
            sbc.setWhereCondition(sc);
            return sc;
        }

    }

}

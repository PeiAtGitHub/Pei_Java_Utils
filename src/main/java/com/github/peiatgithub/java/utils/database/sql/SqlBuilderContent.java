package com.github.peiatgithub.java.utils.database.sql;

import static com.github.peiatgithub.java.utils.Constants.*;
import org.apache.commons.lang3.StringUtils;

import com.github.peiatgithub.java.utils.database.sql.constants.SqlFamily;

import lombok.Getter;
import lombok.Setter;
import lombok.AccessLevel;

/**
 * 
 * @author pei
 * @since 5.0
 */
@Getter(AccessLevel.PACKAGE)
@Setter(AccessLevel.PACKAGE)
public class SqlBuilderContent {

    private StringBuilder sqlSb;
    private SqlFamily sqlFamily;
    private SqlCondition selectConditionBuilder;
    private SqlCondition deleteConditionBuilder;
    private Integer maxNumOfRows;

    public SqlBuilderContent() {
        setSqlSb(new StringBuilder());
        setSqlFamily(SqlFamily.MY_SQL); // default
    }

    /**
     * Build the result sql String
     */
    public String build() {

        String result = sqlSb.toString().trim();

        if (maxNumOfRows != null) {// output only limited rows
            if (sqlFamily != null) {
                switch (sqlFamily) {
                case MY_SQL:
                    result += "LIMIT " + maxNumOfRows;
                    break;
                case ORACLE:
                    selectConditionBuilder.and(new SqlCondition("ROWNUM").lessThanOrEqualTo(maxNumOfRows));
                    break;
                case SQL_SERVER:
                case MS_ACCESS:
                    result = StringUtils.replace(result, "SELECT ", "SELECT TOP " + maxNumOfRows + SPACE, 1);
                    break;
                default:
                    throw new RuntimeException("Unsupported SQL Family: " + sqlFamily.toString());
                }
            }
        }

        if (selectConditionBuilder != null) {
            result = result.replace("WHERE", "WHERE " + selectConditionBuilder.build() + SPACE);
        } else if (deleteConditionBuilder != null) {
            result = result.replace("WHERE", "WHERE " + deleteConditionBuilder.build() + SPACE);
        }

        return result.trim();

    }

}

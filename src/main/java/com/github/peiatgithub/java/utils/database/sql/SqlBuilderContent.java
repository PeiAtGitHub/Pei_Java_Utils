package com.github.peiatgithub.java.utils.database.sql;

import static com.github.peiatgithub.java.utils.Constants.*;
import org.apache.commons.lang3.StringUtils;

import com.github.peiatgithub.java.utils.database.sql.constants.SqlFamily;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AccessLevel;

/**
 * <pre>
 * This class holds the content of the SQL String under construction.
 * And the outcome SQL String is created by calling build() method of this class.
 * </pre>
 * @author pei
 * @since 5.0
 */
@Getter(AccessLevel.PACKAGE)
@Setter(AccessLevel.PACKAGE)
@NoArgsConstructor
public class SqlBuilderContent {

    private StringBuilder sqlSb = new StringBuilder();
    private SqlFamily sqlFamily = SqlFamily.MY_SQL; // default
    private SqlCondition selectCondition = null;
    private SqlCondition deleteCondition = null;
    private Integer maxNumOfRows = null;


    /**
     * Build the result sql String
     */
    public String build() {

        String result = sqlSb.toString().trim();

        if (maxNumOfRows != null) {// output only limited rows
            if (sqlFamily != null) {
                switch (sqlFamily) {
                case MY_SQL:
                    result += " LIMIT " + maxNumOfRows;
                    break;
                case ORACLE:
                    selectCondition.and(new SqlCondition("ROWNUM").lessThanOrEqualTo(maxNumOfRows));
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

        if (selectCondition != null) {
            result = result.replaceFirst("WHERE", "WHERE " + selectCondition.buildConditionString());
        } else if (deleteCondition != null) {
            result = result.replaceFirst("WHERE", "WHERE " + deleteCondition.buildConditionString());
        }

        return result.trim();

    }

}

package com.github.peiatgithub.java.utils.database.sql;

import static com.github.peiatgithub.java.utils.Constants.*;
import org.apache.commons.lang3.StringUtils;

import com.github.peiatgithub.java.utils.database.sql.constants.SqlFamily;

import lombok.Getter;
import lombok.Setter;
import lombok.AccessLevel;

/**
 * <pre>
 * This class holds the content of the SQL String under construction.
 * The outcome SQL String is created by calling the build() method.
 * </pre>
 * @author pei
 * @since 5.0
 */
@Getter(AccessLevel.PACKAGE)
@Setter(AccessLevel.PACKAGE)
public class SqlBuilderContent {

    private StringBuilder sqlSb = new StringBuilder();
    private SqlFamily sqlFamily = SqlFamily.MY_SQL; // default
    private SqlCondition whereCondition = null;
    private Integer maxNumOfRows = null;


    /**
     * Build the result sql String
     */
    public String build() {

        String result = sqlSb.toString().trim();

        if (maxNumOfRows != null) {
            if (sqlFamily != null) {
                switch (sqlFamily) {
                case ORACLE:
                    whereCondition.and(new SqlCondition("ROWNUM").lessThanOrEqualTo(maxNumOfRows));
                    break;
                case SQL_SERVER:
                case MS_ACCESS:
                    result = StringUtils.replace(result, "SELECT ", "SELECT TOP " + maxNumOfRows + SPACE, 1);
                    break;
                case MY_SQL:
                default:
                    result += " LIMIT " + maxNumOfRows;
                    break;
                }
            }
        }

        if (whereCondition != null) {
            result = result.replaceFirst("WHERE", "WHERE " + whereCondition.buildConditionString());
        }

        return result.trim();

    }

}

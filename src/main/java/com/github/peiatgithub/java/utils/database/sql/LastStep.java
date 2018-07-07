package com.github.peiatgithub.java.utils.database.sql;

import static com.github.peiatgithub.java.utils.Constants.COMMA;
import static com.github.peiatgithub.java.utils.Constants.SPACE;
import static com.github.peiatgithub.java.utils.Utils.join;

import com.github.peiatgithub.java.utils.database.sql.constants.Order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * This is the builder where the SQL may be completed.
 * 
 * @author pei
 * @since 5.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LastStep {

    SqlBuilderContent sbc;

    /**
     *  
     */
    public LastStep orderBy(Order order, String... columns) {
        sbc.getSqlSb().append("ORDER BY " + join(columns, COMMA) + SPACE);
        if (order != Order.ASC) {
            sbc.getSqlSb().append(order.name());
        }
        return this;
    }

    /**
     * Use the default order ASC
     */
    public LastStep orderBy(String... columns) {
        orderBy(Order.ASC, columns);
        return this;
    }

    public LastStep groupBy(String... columns) {
        sbc.getSqlSb().append("GROUP BY " + join(columns, COMMA) + SPACE);
        return this;
    }

    public LastStep having(SqlCondition sc) {
        sbc.getSqlSb().append("HAVING " + sc.buildConditionString() + SPACE);
        return this;
    }

    public LastStep union(String sql) {
        sbc.getSqlSb().append("UNION ").append(sql + SPACE);
        return this;
    }
    public LastStep unionAll(String sql) {
        sbc.getSqlSb().append("UNION ALL ").append(sql + SPACE);
        return this;
    }

    public String build() {
        return sbc.build();
    }

}

package com.github.peiatgithub.java.utils.database.sql;

import static com.github.peiatgithub.java.utils.Constants.COMMA;
import static com.github.peiatgithub.java.utils.Constants.SPACE;
import static com.github.peiatgithub.java.utils.Utils.join;

import com.github.peiatgithub.java.utils.database.sql.constants.Order;

import lombok.AllArgsConstructor;

/**
 * 
 * @author pei
 * @since 5.0
 */
@AllArgsConstructor
public class ResultProcessor {
    
    SqlBuilderContent sbc;
    
    
    public SqlBuilderContent orderBy(Order order, String... columns) {
        sbc.getSqlSb().append("ORDER BY " + join(columns, COMMA) + SPACE + order.name());
        return sbc;
    }

    /**
     * Use the default order ASC
     */
    public SqlBuilderContent orderBy(String... columns) {
        return orderBy(Order.ASC, columns);
    }
  
    
    public String build() {
        return sbc.build();
    }

}

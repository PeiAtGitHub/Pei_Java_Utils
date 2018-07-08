package com.github.peiatgithub.java.utils.database.sql.constants;


/**
 * @author pei
 * @since 5.0
 */
public enum JoinType {
    
    INNER_JOIN("INNER JOIN"),
    LEFT_JOIN("LEFT JOIN"),
    RIGHT_JOIN("RIGHT JOIN"),
    FULL_OUTER_JOIN("FULL OUTER JOIN");
   
    private String text;

    JoinType(String text) {
        this.text = text;
    }

    public String text() {
        return this.text;
    }

}

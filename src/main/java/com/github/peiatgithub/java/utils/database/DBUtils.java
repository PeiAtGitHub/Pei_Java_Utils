package com.github.peiatgithub.java.utils.database;

/**
 * 
 * @author pei
 *
 * @since 5.0
 */
public class DBUtils {
	
	// SQL keywords
	public static final String SELECT = "SELECT";
	public static final String DISTINCT = "DISTINCT";
	public static final String FROM = "FROM";
	public static final String WHERE = "WHERE";
	
	// JDBC URLs
    public static final String JDBC_URL_DERBY = "jdbc:derby:DB_Derby;create=true";
    public static final String JDBC_URL_DERBY_SHUTDOWN = "jdbc:derby:;shutdown=true";
    
    // Hibernate
    public static final String HIBERNATE_CONFIG_FILE= "hibernate.cfg.xml";
    

}

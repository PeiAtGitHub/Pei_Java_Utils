package com.github.peiatgithub.java.utils.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.github.peiatgithub.java.utils.database.sql.SqlCondition;
import com.github.peiatgithub.java.utils.database.sql.StandAloneConstraint;
import com.github.peiatgithub.java.utils.database.sql.TableColumn;
import com.github.peiatgithub.java.utils.collections.MapBuilder;
import static com.github.peiatgithub.java.utils.database.sql.ColumnConstraint.*;

import com.github.peiatgithub.java.utils.database.sql.SqlBuilder;
import static com.github.peiatgithub.java.utils.database.sql.constants.AggregateFunction.*;
import com.github.peiatgithub.java.utils.database.sql.constants.JoinType;
import com.github.peiatgithub.java.utils.database.sql.constants.Order;
import com.github.peiatgithub.java.utils.database.sql.constants.SqlFamily;
import com.google.common.collect.Lists;
import com.github.peiatgithub.java.utils.database.sql.constants.DataType;

import static org.hamcrest.CoreMatchers.is;

/**
 * @author pei
 */
public class SqlBuilderTests {

    private final static String CUSTOMERS = "Customers";
    private final static String CITY = "City";
    private final static String COUNTRY = "Country";

    @Test
    public void testSelects() throws Exception {

        assertThat(getSqlBuilder().select("CustomerName", "City").from("Customers").build(),
                is("SELECT CustomerName, City FROM Customers"));

        assertThat(getSqlBuilder().selectDistinct(CITY).from("Customers").build(),
                is("SELECT DISTINCT City FROM Customers"));

        assertThat(getSqlBuilder().select(max("Price", "LargestPrice")).from("Products").build(),
                is("SELECT MAX(Price) AS LargestPrice FROM Products"));
        
        assertThat(getSqlBuilder().selectAll().from(CUSTOMERS).where("CustomerName").like("a%").build(),
                is("SELECT * FROM Customers WHERE CustomerName LIKE 'a%'"));

        assertThat(getSqlBuilder().selectAll().from(CUSTOMERS).where(COUNTRY).inValues("Germany", "France", "UK")
                .build(),
                is("SELECT * FROM Customers WHERE Country IN ('Germany', 'France', 'UK')"));

        assertThat(getSqlBuilder().selectAll().from(CUSTOMERS).where(COUNTRY).notInValues("Germany", "France", "UK")
                .build(), is("SELECT * FROM Customers WHERE Country NOT IN ('Germany', 'France', 'UK')"));

        assertThat(getSqlBuilder().selectAll(3).from(CUSTOMERS).where(COUNTRY).equalTo("Germany").build(),
                is("SELECT * FROM Customers WHERE Country = 'Germany' LIMIT 3"));

        assertThat(getSqlBuilder().selectAll().from(CUSTOMERS).where("CustomerID").equalTo(1).build(),
                is("SELECT * FROM Customers WHERE CustomerID = 1"));
        assertThat(getSqlBuilder().selectAll().from(CUSTOMERS).where(COUNTRY).equalTo("Mexico").build(),
                is("SELECT * FROM Customers WHERE Country = 'Mexico'"));

        assertThat(getSqlBuilder().selectAll().from(CUSTOMERS).where("CustomerID").between(10, 100).orderBy(COUNTRY)
                .build(),
                is("SELECT * FROM Customers WHERE CustomerID BETWEEN 10 AND 100 ORDER BY Country"));

        assertThat(getSqlBuilder().select(count("CustomerID"), COUNTRY).from(CUSTOMERS).groupBy(COUNTRY)
                        .orderBy(Order.DESC, count("CustomerID")).build(),
                is("SELECT COUNT(CustomerID), Country FROM Customers "
                        + "GROUP BY Country ORDER BY COUNT(CustomerID) DESC"));

        assertThat(getSqlBuilder().select(count("CustomerID"), COUNTRY).from(CUSTOMERS).groupBy(COUNTRY)
                        .having(new SqlCondition(count("CustomerID")).greaterThan(5))
                        .orderBy(Order.DESC, count("CustomerID")).build(),
                is("SELECT COUNT(CustomerID), Country FROM Customers GROUP BY Country HAVING COUNT(CustomerID) > 5 "
                        + "ORDER BY COUNT(CustomerID) DESC"));

        assertThat(getSqlBuilder().select("SupplierName").from("Suppliers").where(new SqlCondition().exist(
                new SqlBuilder().select("ProductName").from("Products").where("SupplierId")
                .equalTo("Suppliers.supplierId").and(new SqlCondition("Price").equalTo(22)).build())).build(),
                is("SELECT SupplierName FROM Suppliers WHERE EXISTS "
                        + "(SELECT ProductName FROM Products WHERE SupplierId = Suppliers.supplierId AND Price = 22)"));

        assertThat(getSqlBuilder().selectAll().from(CUSTOMERS).where(CITY).equalTo("Berlin")
                        .or(new SqlCondition(CITY).equalTo("Munich")).build(),
                is("SELECT * FROM Customers WHERE City = 'Berlin' OR City = 'Munich'"));

        assertThat(getSqlBuilder().selectAll().from(CUSTOMERS)
                        .where(new SqlCondition().not(new SqlCondition(COUNTRY).equalTo("Germany"))).build(),
                is("SELECT * FROM Customers WHERE NOT Country = 'Germany'"));

        assertThat(getSqlBuilder().selectAll().from(CUSTOMERS).where(COUNTRY).equalTo("Germany")
                .and(new SqlCondition(CITY).equalTo("Berlin").or(new SqlCondition(CITY)).equalTo("Munich")).build(),
                is("SELECT * FROM Customers WHERE Country = 'Germany' AND (City = 'Berlin' OR City = 'Munich')"));

        assertThat(getSqlBuilder().select("LastName", "FirstName", "Address").from("Persons").where("Address").isNull()
                .build(), is("SELECT LastName, FirstName, Address FROM Persons WHERE Address IS NULL"));

        assertThat(getSqlBuilder().select("Orders.OrderID", "Customers.CustomerName")
                        .from("Orders", JoinType.INNER_JOIN, "Customers", "CustomerID").build(),
                is("SELECT Orders.OrderID, Customers.CustomerName "
                        + "FROM Orders INNER JOIN Customers ON Orders.CustomerID = Customers.CustomerID"));

        assertThat(getSqlBuilder().select(CITY, COUNTRY).from(CUSTOMERS).where(COUNTRY).equalTo("Germany")
                        .union(getSqlBuilder().select(CITY, COUNTRY).from("Suppliers").where(COUNTRY)
                                .equalTo("Germany").build()).orderBy(CITY).build(),
                is("SELECT City, Country FROM Customers WHERE Country = 'Germany' UNION "
                        + "SELECT City, Country FROM Suppliers WHERE Country = 'Germany' ORDER BY City"));

    }
    
    @Test
    public void rowOperations() throws Exception {
        
        assertThat(getSqlBuilder().insertInto(CUSTOMERS, MapBuilder.linkedHashMap("CustomerName", "Cardinal")
                .put("ContactName", "Tom B. Erichsen").put("Address", "Skagen 21").put("City", "Stavanger")
                .put("PostalCode", "4006").put("Country", "Norway").build()).build(),
                is("INSERT INTO Customers (CustomerName, ContactName, Address, City, PostalCode, Country) "
                        + "VALUES ('Cardinal', 'Tom B. Erichsen', 'Skagen 21', 'Stavanger', '4006', 'Norway')"));

        assertThat(getSqlBuilder().updateTable(CUSTOMERS, MapBuilder.linkedHashMap("ContactName", "Alfred Schmidt")
                .put("City", "Frankfurt").build(), new SqlCondition("CustomerID").equalTo(1)).build(),
                is("UPDATE Customers SET ContactName = 'Alfred Schmidt', City = 'Frankfurt' WHERE CustomerID = 1"));
        
        assertThat(getSqlBuilder().deleteRowsFrom(CUSTOMERS).where("CustomerName").equalTo("Some One").build(),
                is("DELETE FROM Customers WHERE CustomerName = 'Some One'"));
        
    }

    @Test
    public void tableOperations() throws Exception {
        final String tablePersons = "Persons";
        final String columnDateOfBirth = "DateOfBirth";
        
        assertThat(getSqlBuilder().createTable(tablePersons, Lists.newArrayList(
                new TableColumn("PersonID", DataType.ofInt()),
                new TableColumn("LastName", DataType.ofVarChar(255)),
                new TableColumn("FirstName", DataType.ofVarChar(255)),
                new TableColumn("Address", DataType.ofVarChar(255)),
                new TableColumn("City", DataType.ofVarChar(255), defaultWithValue("Unknown"))))
                .build(),
                is("CREATE TABLE Persons (PersonID INT, "
                        + "LastName VARCHAR(255), FirstName VARCHAR(255), Address VARCHAR(255), "
                        + "City VARCHAR(255) DEFAULT 'Unknown')"));

        assertThat(getSqlBuilder().createTable(tablePersons, Lists.newArrayList(
                new TableColumn("ID", DataType.ofInt(), notNull(), unique(), primaryKey()),
                new TableColumn("LastName", DataType.ofVarChar(255)),
                new TableColumn("FirstName", DataType.ofVarChar(255), notNull()),
                new TableColumn("Age", DataType.ofInt(), check("Age>=18"))))
                .build(),
                is("CREATE TABLE Persons (ID INT NOT NULL, "
                        + "LastName VARCHAR(255), FirstName VARCHAR(255) NOT NULL, "
                        + "Age INT, "
                        + "UNIQUE (ID), "
                        + "PRIMARY KEY (ID), "
                        + "CHECK (Age>=18))"));

        assertThat(getSqlBuilder().createTable("Orders", Lists.newArrayList(
                new TableColumn("OrderID", DataType.ofInt(), notNull(), primaryKey()),
                new TableColumn("OrderNumber", DataType.ofInt(), notNull()),
                new TableColumn("PersonID", DataType.ofInt(), foreignKey("Persons", "PersonID"))))
                .build(),
                is("CREATE TABLE Orders (OrderID INT NOT NULL, OrderNumber INT NOT NULL, "
                        + "PersonID INT, "
                        + "PRIMARY KEY (OrderID), "
                        + "FOREIGN KEY (PersonID) REFERENCES Persons(PersonID))"));
        
        assertThat(getSqlBuilder().sqlFamily(SqlFamily.ORACLE).createTable("Orders", Lists.newArrayList(
                new TableColumn("OrderID", DataType.ofInt(), notNull(), primaryKey()),
                new TableColumn("OrderNumber", DataType.ofInt(), notNull()),
                new TableColumn("PersonID", DataType.ofInt(), foreignKey("Persons", "PersonID"))))
                .build(),
                is("CREATE TABLE Orders (OrderID INT NOT NULL PRIMARY KEY, "
                        + "OrderNumber INT NOT NULL, "
                        + "PersonID INT FOREIGN KEY REFERENCES Persons(PersonID))"));

        assertThat(getSqlBuilder().createTable("Persons", Lists.newArrayList(
                new TableColumn("ID", DataType.ofInt(), notNull()),
                new TableColumn("OrderID", DataType.ofInt(), notNull()),
                new TableColumn("LastName", DataType.ofVarChar(255), notNull()),
                new TableColumn("FirstName", DataType.ofVarChar(255)),
                new TableColumn("Age", DataType.ofInt())), 
                StandAloneConstraint.unique("UC_Person", "ID", "LastName"),
                StandAloneConstraint.primaryKey("PK_Person", "ID", "LastName"),
                StandAloneConstraint.foreignKey("FK_PersonOrder", "Orders", "ID", "OrderID"),
                StandAloneConstraint.check("CHK_Person", "Age>=16")
                ).build(),
                is("CREATE TABLE Persons (" 
                        + "ID INT NOT NULL, OrderID INT NOT NULL, " 
                        + "LastName VARCHAR(255) NOT NULL, FirstName VARCHAR(255), " 
                        + "Age INT, " 
                        + "CONSTRAINT UC_Person UNIQUE (ID, LastName), "
                        + "CONSTRAINT PK_Person PRIMARY KEY (ID, LastName), "
                        + "CONSTRAINT FK_PersonOrder FOREIGN KEY (OrderID) REFERENCES Orders(ID), "
                        + "CONSTRAINT CHK_Person CHECK (Age>=16))"));
        
        // ALTER table
        assertThat(getSqlBuilder().alterTable(tablePersons).addColumn(
                new TableColumn(columnDateOfBirth, DataType.ofDate())).build() , 
                is("ALTER TABLE Persons ADD DateOfBirth DATE()"));
        assertThat(getSqlBuilder().alterTable(tablePersons).modifyColumn(
                new TableColumn(columnDateOfBirth, DataType.ofYear())).build(), 
                is("ALTER TABLE Persons MODIFY COLUMN DateOfBirth YEAR()"));
        assertThat(getSqlBuilder().alterTable(tablePersons).dropColumn(columnDateOfBirth).build() , 
                is("ALTER TABLE Persons DROP COLUMN DateOfBirth"));
        
        // DROP table
        assertThat(getSqlBuilder().dropTable("Shippers").build(), is("DROP TABLE Shippers"));

    }

    @Test
    public void databaseOperations() throws Exception {
        assertThat(getSqlBuilder().createDataBase("testDB").build(), is("CREATE DATABASE testDB"));
        assertThat(getSqlBuilder().dropDataBase("testDB").build(), is("DROP DATABASE testDB"));
    }

    /*
     * 
     */
    
    private SqlBuilder getSqlBuilder() {
        return new SqlBuilder();
    }
}

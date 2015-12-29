/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdev.letsmeet.model.mysql.tables;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author HA999
 */
public class CategoryTable extends MySQLTable{
    
    private final String createString = 
        "CREATE TABLE IF NOT EXISTS " +
        "Category" +
        "(CAT_ID int NOT NULL AUTO_INCREMENT, " +
        "NAME varchar(40) NOT NULL, " +
            "PRIMARY KEY (CAT_ID))";

    private final String indexString = "CREATE UNIQUE INDEX category_index ON "
            + "Category (CAT_ID)";
    
    public CategoryTable(Connection conn) {
        createTable(conn, createString);
        defineIndexes(conn, indexString);
        insertCategories(conn);
    }
    
    @Override
    public void insert(Connection conn, Serializable bean) {
        String name = (String) bean;
        PreparedStatement pstmt;
                
        try{
            pstmt = conn.prepareStatement("INSERT INTO Category(NAME)"
                    + " VALUES('" + name + "')");
            pstmt.executeUpdate();
            }catch (SQLException ex) {
                System.out.println(ex);
            }
    }
    
    private void insertCategories(Connection conn){
        for (Category cat : Category.values()) {
            insert(conn, cat.toString());
        }
    }
    
    public enum Category {
        SPORTS,
        DEVELOPMENT,
        PETS,
        BOOKS,
        MOVIES,
        TV,
        ART,
        THEATER,
        RECREATION,
    }
}

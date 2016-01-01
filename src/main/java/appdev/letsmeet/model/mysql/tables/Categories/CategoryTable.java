/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdev.letsmeet.model.mysql.tables.Categories;

import appdev.letsmeet.model.mysql.tables.MySQLTable;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author HA999
 */
public class CategoryTable extends MySQLTable{
    
    private final String createString = 
        "CREATE TABLE IF NOT EXISTS " +
        "Category" +
        "(CAT_ID int NOT NULL AUTO_INCREMENT, " +
        "NAME varchar(40) NOT NULL UNIQUE, " +
            "PRIMARY KEY (CAT_ID))";

    private final String indexString = "CREATE UNIQUE INDEX category_index ON "
            + "Category (NAME)";
    
    public CategoryTable(Connection conn, List<String> categories) {
        createTable(conn, createString);
        defineIndexes(conn, indexString);
        insertCategories(conn, categories);
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
    
    private void insertCategories(Connection conn, List<String> categories){
        categories.forEach(c -> insert(conn, c));
    }
}

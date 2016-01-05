/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdev.letsmeet.model.mysql.tables.Categories;

import appdev.letsmeet.model.mysql.tables.MySQLDAO;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author HA999
 */
public class CategoryTable implements MySQLDAO {
    
    private final String initFile = "categories.txt";
    private final String tableName = "categories";
    
    private final String createString = 
        "CREATE TABLE IF NOT EXISTS " +
        tableName +
        "(CAT_ID int NOT NULL AUTO_INCREMENT, " +
        "NAME varchar(40) NOT NULL UNIQUE, " +
            "PRIMARY KEY (CAT_ID))";

    private final String indexString = "CREATE UNIQUE INDEX category_index ON "
            + tableName + " (NAME)";
    
    public CategoryTable(Connection conn) {
        createTable(conn, createString);
        defineIndexes(conn, indexString);
        insertFromFile(conn, initFile, tableName);
    }
    
    @Override
    public void insert(Connection conn, Serializable bean) {
        String name = (String) bean;
        PreparedStatement pstmt;
                
        try{
            pstmt = conn.prepareStatement("INSERT INTO " + tableName + " (NAME)"
                    + " VALUES('" + name + "')");
            pstmt.executeUpdate();
            }catch (SQLException ex) {
                System.out.println(ex);
            }
    }
    
}

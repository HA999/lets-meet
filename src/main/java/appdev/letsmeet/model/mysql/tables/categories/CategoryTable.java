/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdev.letsmeet.model.mysql.tables.categories;

import appdev.letsmeet.model.mysql.tables.mysqldao.MySQLDAO;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HA999
 */
public class CategoryTable implements MySQLDAO {
    
    private final String initFile = "categories.txt";
    private final String tableName = "categories";
    private final String categoryID_col = "CAT_ID";
    private final String name_col = "NAME";
    
    private final String createString = 
        "CREATE TABLE IF NOT EXISTS " +
        tableName +
        "(" + categoryID_col + " int NOT NULL AUTO_INCREMENT, " +
        name_col + " varchar(40) NOT NULL UNIQUE, " +
            "PRIMARY KEY (" + categoryID_col + "))";

    private final String indexString = "CREATE INDEX category_index ON "
            + tableName + " (" + name_col + ")";
    
    public CategoryTable(Connection conn, String filePath) {
        copyDataFileToMySQLFileDirectory(conn, filePath, initFile);
        createTable(conn, createString);
        defineIndexes(conn, indexString);
        insertFromFile(conn, initFile, tableName);
    }
    
    @Override
    public Serializable insert(Connection conn, Serializable bean) {
        String name = (String) bean;
        PreparedStatement pstmt;
                
        try{
            pstmt = conn.prepareStatement("INSERT INTO " + tableName + " (" + name_col + ")"
                    + " VALUES('" + name + "')");
            pstmt.executeUpdate();
            }catch (SQLException ex) {
                System.out.println(ex);
            }
        
        return name;
    }

    public List<String> getCategoryList(Connection conn) throws SQLException {
        List<String> resultList = new ArrayList<>();
        PreparedStatement pstmt = conn.prepareStatement("SELECT " + name_col + " FROM " + tableName);
        ResultSet rs = pstmt.executeQuery();
        
        while (rs.next()) {
            resultList.add(rs.getString(name_col));
        }
        return resultList;
    }
    
}

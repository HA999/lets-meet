/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdev.letsmeet.model.mysql.tables;

import appdev.letsmeet.control.utils.jsonBeans.SubCategoryBean;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author HA999
 */
public class SubCategoryTable implements MySQLDAO {
       
    private final String initFile = "sub-categories.txt";
    private final String tableName = "subcategory";
    
    private final String createString = 
        "CREATE TABLE IF NOT EXISTS " +
        tableName +
        "(SUB_CAT_ID int NOT NULL AUTO_INCREMENT, " +
        "CAT_NAME varchar(40) NOT NULL, " +
        "NAME varchar(40) NOT NULL, " 
            + "PRIMARY KEY (SUB_CAT_ID), " 
            + "FOREIGN KEY (CAT_NAME) REFERENCES categories(NAME))";
    
    private final String indexString = "CREATE UNIQUE INDEX sub_category_index ON "
            + tableName + "(SUB_CAT_ID)";
    
    public SubCategoryTable(Connection conn) {
        createTable(conn, createString);
        defineIndexes(conn, indexString);
//        insertFromFile(conn, initFile, tableName);
    }
    
    @Override
    public void insert(Connection conn, Serializable bean) {
        SubCategoryBean sCBean = (SubCategoryBean) bean;
        PreparedStatement pstmt;
        
        try{
            pstmt = conn.prepareStatement("INSERT INTO " +
                    tableName +
                    "(`SUB_CAT_ID`, "+
                    "`CAT_ID`, "+
                    "`NAME`)" +
                    "VALUES" +
                    "(<{SUB_CAT_ID: " + sCBean.subCatID + " }>, "+
                    "<{CAT_ID: " + sCBean.categoryID + "}>, "+
                    "<{NAME: " + sCBean.subCatname + "}>)");

            pstmt.executeUpdate();
        }catch (SQLException ex) {
            System.out.println(ex);
        }
    }    
}

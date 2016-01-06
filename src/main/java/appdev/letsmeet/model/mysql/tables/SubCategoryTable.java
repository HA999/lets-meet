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
        "NAME varchar(40) NOT NULL, " +
        "CAT_NAME varchar(40) NOT NULL, " 
            + "PRIMARY KEY (SUB_CAT_ID), " 
            + "FOREIGN KEY (CAT_NAME) REFERENCES categories(NAME))";
    
    private final String indexString = "CREATE UNIQUE INDEX sub_category_index ON "
            + tableName + "(SUB_CAT_ID)";
    
    public SubCategoryTable(Connection conn) {
        createTable(conn, createString);
        defineIndexes(conn, indexString);
        disableForeignKeyChecks(conn);
        insertFromFile(conn, initFile, tableName);
        enableForeignKeyChecks(conn);
    }
    
    @Override
    public void insert(Connection conn, Serializable bean) {
        SubCategoryBean sCBean = (SubCategoryBean) bean;
        PreparedStatement pstmt;
        
        try{
            pstmt = conn.prepareStatement("INSERT INTO " +
                    tableName +
                    "(`SUB_CAT_ID`, "+
                    "`NAME`, "+
                    "`CAT_NAME`)" +
                    "VALUES" +
                    "(<{SUB_CAT_ID: " + sCBean.subCatID + " }>, "+
                    "<{NAME: " + sCBean.subCatname + "}>, "+
                    "<{CAT_NAME: " + sCBean.catName + "}>)");

            pstmt.executeUpdate();
        }catch (SQLException ex) {
            System.out.println(ex);
        }
    }    
}

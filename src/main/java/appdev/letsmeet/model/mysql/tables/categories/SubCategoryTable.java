/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdev.letsmeet.model.mysql.tables.categories;

import appdev.letsmeet.control.utils.jsonBeans.SubCategoryBean;
import appdev.letsmeet.model.mysql.tables.MySQLDAO;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HA999
 */
public class SubCategoryTable implements MySQLDAO {
       
    private final String initFile = "sub-categories.txt";
    private final String tableName = "subcategory";
    private final String subCatID_Col = "SUB_CAT_ID";
    private final String name_Col = "NAME";
    private final String catName_Col = "CAT_NAME";
    
    private final String createString = 
        "CREATE TABLE IF NOT EXISTS " +
        tableName + "(" +
        subCatID_Col + " int NOT NULL AUTO_INCREMENT, " +
        name_Col + " varchar(40) NOT NULL, " +
        catName_Col + " varchar(40) NOT NULL, " 
            + "PRIMARY KEY (" + subCatID_Col + "), " 
            + "FOREIGN KEY (" + catName_Col + ") REFERENCES categories(NAME))";
    
    private final String indexString = "CREATE UNIQUE INDEX sub_category_index ON "
            + tableName + "(" + subCatID_Col + ")";
    
    public SubCategoryTable(Connection conn, String filePath) {
        copyDataFileToMySQLFileDirectory(conn, filePath, initFile);
        createTable(conn, createString);
        defineIndexes(conn, indexString);
        insertFromFile(conn, initFile, tableName);
    }
    
    @Override
    public Serializable insert(Connection conn, Serializable bean) {
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
        
        return sCBean;
    }    

    public SubCategoryBean getSubCategoryData(Connection conn, String subCatId) throws SQLException {
        SubCategoryBean subCategory = new SubCategoryBean();
        PreparedStatement pstmt;
        
        pstmt = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE " + subCatID_Col + " = ?");
        pstmt.setString(1, subCatId);
        ResultSet rs =  pstmt.executeQuery();
        
        if(rs.next()){
            subCategory.catName = rs.getString(catName_Col);
            subCategory.subCatname = rs.getString(name_Col);
            subCategory.subCatID = subCatId;
        }
        return subCategory;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdev.letsmeet.model.mysql.tables.categories;

import appdev.letsmeet.control.utils.jsonBeans.SubCategoryBean;
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
 * @author HANAN&OLYA
 */
public class SubCategoryTable implements MySQLDAO {

       
    private final String initFile = "sub-categories.txt";
    private final String tableName = "subcategory";
    private final String subCatID_Col = "SUB_CAT_ID";
    private final String name_Col = "NAME";
    private final String catName_Col = "CAT_NAME";
    
    private final String createString = 
        "CREATE TABLE IF NOT EXISTS " +
        tableName + " (" +
        subCatID_Col + " int NOT NULL AUTO_INCREMENT, " +
        catName_Col + " varchar(40) NOT NULL, " +
        name_Col + " varchar(40) NOT NULL, " 
            + "PRIMARY KEY (" + subCatID_Col + "), " 
            + "FOREIGN KEY (" + catName_Col + ") REFERENCES categories(NAME))";
    
    private final String indexString = "CREATE INDEX sub_category_index ON "
            + tableName + "(" + catName_Col + ")";
    
    public SubCategoryTable(Connection conn, String filePath) {
        copyDataFileToMySQLFileDirectory(conn, filePath, initFile);
        createTable(conn, createString);
        disableForeignKeyChecks(conn);
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

    public List<String> getSubCategoriesInCategoryList(Connection conn, String category) throws SQLException {
     
        List<String> resultList = new ArrayList<>();
        PreparedStatement pstmt = conn.prepareStatement("SELECT " + name_Col + " FROM " + tableName + " WHERE " + catName_Col + " = ?");
        pstmt.setString(1, category);
        ResultSet resultSet = pstmt.executeQuery();
     
        while (resultSet.next()) {
            resultList.add(resultSet.getString(name_Col));
        }
        return resultList;
    }

    public List<SubCategoryBean> getSubCategories(Connection conn) throws SQLException {
        
        List<SubCategoryBean> resultList = new ArrayList<>();
        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM " + tableName);
        ResultSet rs =  pstmt.executeQuery();
        
        SubCategoryBean currentBean = new SubCategoryBean();
        while (rs.next()){
            currentBean.catName = rs.getString(catName_Col);
            currentBean.subCatname = rs.getString(name_Col);
            currentBean.subCatID = rs.getString(subCatID_Col);
            
            resultList.add(currentBean);
        }
        
        return resultList;
    }
    
    public String convertSubcategoryIDToSubcategoryName(Connection conn, String subcategory) throws SQLException {
        String countryCode;
        PreparedStatement pstmt = conn.prepareStatement("SELECT " + subCatID_Col + " FROM " + tableName +" WHERE " + name_Col + " = ?");
        pstmt.setString(1, subcategory);
        ResultSet resultSet = pstmt.executeQuery();
        resultSet.next();
        countryCode = resultSet.getString(1);
        return countryCode;
    }
}

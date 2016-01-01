/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdev.letsmeet.model.mysql.tables.Categories;

import appdev.letsmeet.control.utils.jsonBeans.SubCategoryBean;
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
public class SubCategoryTable extends MySQLTable {
       
    private final String createString = 
        "CREATE TABLE IF NOT EXISTS " +
        "Sub_Category" +
        "(SUB_CAT_ID int NOT NULL AUTO_INCREMENT, " +
        "CAT_ID int NOT NULL, " +
        "NAME varchar(40) NOT NULL UNIQUE, " 
            + "PRIMARY KEY (SUB_CAT_ID), " 
            + "FOREIGN KEY (CAT_ID) REFERENCES Category(CAT_ID))";
    
    private final String indexString = "CREATE UNIQUE INDEX sub_category_index ON "
            + "Sub_Category (SUB_CAT_ID)";
    
    public SubCategoryTable(Connection conn, List<SubCategoryBean> beanList) {
        createTable(conn, createString);
        defineIndexes(conn, indexString);
        insertSubCategories(conn, beanList);
    }
    
    @Override
    public void insert(Connection conn, Serializable bean) {
        SubCategoryBean sCBean = (SubCategoryBean) bean;
        PreparedStatement pstmt;
        
        try{
            pstmt = conn.prepareStatement("INSERT INTO " +
                    "`lets_meet`.`sub_category`" +
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

    private void insertSubCategories(Connection conn, List<SubCategoryBean> beanList) {
        beanList.forEach(b -> insert(conn, b));
    }
}

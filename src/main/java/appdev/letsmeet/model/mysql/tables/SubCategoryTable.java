/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdev.letsmeet.model.mysql.tables;

import java.io.Serializable;
import java.sql.Connection;

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
        "NAME varchar(40) NOT NULL, " 
            + "PRIMARY KEY (SUB_CAT_ID), " 
            + "FOREIGN KEY (CAT_ID) REFERENCES Category(CAT_ID))";
    
    private final String indexString = "CREATE UNIQUE INDEX sub_category_index ON "
            + "Sub_Category (SUB_CAT_ID)";
    
    public SubCategoryTable(Connection conn) {
        createTable(conn, createString);
        defineIndexes(conn, indexString);
    }
    
    @Override
    public void insert(Connection conn, Serializable bean) {
//        SubCategoryBean scBean = (SubCategoryBean) bean;
//        PreparedStatement pstmt;
//        
//        try{
//            validateInput(scBean);
//            pstmt = conn.prepareStatement("INSERT INTO JoinRequests "
//                    + "CAT_ID, "
//                    + "NAME) VALUES("
//                    + "'" + scBean.categoryID + "'"
//                    + "'" + scBean.name + "')");
//            pstmt.executeUpdate();
//        }catch (SQLException ex) {
//            System.out.println(ex);
//        }
//        }finally {
//            if (conn != null) conn.close();
//        }
    }
    
}

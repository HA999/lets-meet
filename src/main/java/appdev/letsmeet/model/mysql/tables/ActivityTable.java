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
public class ActivityTable implements MySQLDAO{

    private final String createString =
        "CREATE TABLE IF NOT EXISTS " +
        "Activities" +
        "(ACT_ID int NOT NULL AUTO_INCREMENT, " +
        "NAME varchar(40) NOT NULL, " +
        "USER_ID int NOT NULL, " +
        "SUB_CAT_ID int NOT NULL, " +
        "CREATE_TIME timestamp, " +
        "DATE_TIME datetime NOT NULL, " +
        "COUNTRY_CODE char(3), " +
        "CITY_CODE char(20), " +
        "ABOUT longtext, " +
        "PHOTO blob, "
            +"PRIMARY KEY (ACT_ID), "
            +"FOREIGN KEY (USER_ID) REFERENCES Users(USER_ID), "
            +"FOREIGN KEY (SUB_CAT_ID) REFERENCES subcategory(SUB_CAT_ID))";
    
    private final String indexString = "CREATE UNIQUE INDEX user_index ON Users (USER_ID)";
    
    public ActivityTable(Connection conn) {
        createTable(conn, createString);
        defineIndexes(conn, indexString);
    }
    
    @Override
    public void insert(Connection conn, Serializable bean) {
//        ActivityBean aBean = (ActivityBean) bean;
//        PreparedStatement pstmt;
//        
//            try {
//                validateInput(aBean);
//                pstmt = conn.prepareStatement("INSERT INTO Activities "
//                        + "(NAME, "
//                        + "USER_ID, "
//                        + "SUB_CAT_ID, "
//                        + "DATE_TIME, "
//                        + "COUNTRY_CODE, "
//                        + "CITY_CODE, "
//                        + "ABOUT, "
//                        + "PHOTO) VALUES("
//                        + "'" + aBean.name + "', "
//                        + "'" + aBean.userID + "', "
//                        + "'" + aBean.subCatId + "', "
//                        + "'" + aBean.dareTime + "', "
//                        + "'" + aBean.country + "', "
//                        + "'" + aBean.city + "', "
//                        + "'" + aBean.about + "', "
//                        + "'" + aBean.photo + "')");
//
//                pstmt.executeUpdate();
//            }catch (SQLException ex) {
//                System.out.println(ex);
//            }
//            }finally {
//                if (conn != null) conn.close();
//            }
        }
    
    
}

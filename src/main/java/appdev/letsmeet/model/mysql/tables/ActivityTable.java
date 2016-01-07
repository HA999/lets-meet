/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdev.letsmeet.model.mysql.tables;

import appdev.letsmeet.control.utils.jsonBeans.ActivityBean;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 *
 * @author HA999
 */
public class ActivityTable implements MySQLDAO{

    private final String tableName = "activities";
    
    private final String createString =
        "CREATE TABLE IF NOT EXISTS " +
        tableName +
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
    public Serializable insert(Connection conn, Serializable bean) throws SQLException {
        ActivityBean aBean = (ActivityBean) bean;
        PreparedStatement pstmt;
        
        //validateInput(aBean);
        pstmt = conn.prepareStatement("INSERT INTO activities "
                + "(NAME, "
                + "USER_ID, "
                + "SUB_CAT_ID, "
                + "DATE_TIME, "
                + "COUNTRY_CODE, "
                + "CITY_CODE, "
                + "ABOUT, "
                + "PHOTO) VALUES("
                + "'" + aBean.name + "', "
                + "'" + aBean.userId + "', "
                + "'" + aBean.subCatId + "', "
                + "'" + aBean.dateTime + "', "
                + "'" + aBean.country + "', "
                + "'" + aBean.city + "', "
                + "'" + aBean.about + "', "
                + "'" + aBean.photo + "')", Statement.RETURN_GENERATED_KEYS);
        
        int affectedRows = pstmt.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Creating activity failed, no rows affected.");
        }
        
        try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                aBean.actId = Integer.toString(generatedKeys.getInt("ACT_ID"));
                aBean.createdTime = generatedKeys.getString("CREATE_TIME");
            }
            else {
                throw new SQLException("Creating activity failed, no ID obtained.");
            }
        }
        return aBean;
    }

    public List<ActivityBean> getUserActivities(Connection conn, String userID) throws SQLException{
        PreparedStatement pstmt = conn.prepareStatement("SHOW VARIABLES LIKE 'secure_file_priv'");
        ResultSet rs = pstmt.executeQuery();
        
        conn.prepareStatement("SELECT * FROM " + tableName + " WHERE USERNAME = ? AND PASSWORD = ?");
        return null;
    }
}

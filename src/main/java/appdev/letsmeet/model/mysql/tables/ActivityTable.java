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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HA999
 */
public class ActivityTable implements MySQLDAO{

    private final String tableName = "activities";
    private final String actID_Col = "ACT_ID";
    private final String name_Col = "NAME";
    private final String userID_Col = "USER_ID";
    private final String subCatID_Col = "SUB_CAT_ID";
    private final String createTime_Col = "CREATE_TIME";
    private final String dateTime_Col = "DATE_TIME";
    private final String countryCode_Col = "COUNTRY_CODE";
    private final String cityCode_Col = "CITY_CODE";
    private final String about_Col = "ABOUT";
    private final String photo_Col = "PHOTO";
    
    private final String createStatement =
        "CREATE TABLE IF NOT EXISTS " +
        tableName +
        "( "+ actID_Col + " int NOT NULL AUTO_INCREMENT, " +
        name_Col + " varchar(40) NOT NULL, " +
        userID_Col + " int NOT NULL, " +
        subCatID_Col + " int NOT NULL, " +
        createTime_Col +" timestamp, " +
        dateTime_Col + " datetime NOT NULL, " +
        countryCode_Col + " char(3), " +
        cityCode_Col + " char(20), " +
        about_Col +" longtext, " +
        photo_Col +" blob, "
            +"PRIMARY KEY (" + actID_Col + "), "
            +"FOREIGN KEY (" + userID_Col + ") REFERENCES Users(USER_ID), "
            +"FOREIGN KEY (" + subCatID_Col + ") REFERENCES subcategory(SUB_CAT_ID))";
    
    private final String indexString = "CREATE UNIQUE INDEX user_index ON Users (USER_ID)";
    
    public ActivityTable(Connection conn) {
        createTable(conn, createStatement);
        defineIndexes(conn, indexString);
    }
    
    @Override
    public Serializable insert(Connection conn, Serializable bean) throws SQLException {
        ActivityBean aBean = (ActivityBean) bean;
        PreparedStatement pstmt;
        
        //validateInput(aBean);
        pstmt = conn.prepareStatement("INSERT INTO " + tableName
                + "(" + name_Col + ", "
                + userID_Col + ", "
                + subCatID_Col + ", "
                + dateTime_Col + ", "
                + countryCode_Col +", "
                + cityCode_Col + ", "
                + about_Col + ", "
                + photo_Col + ") VALUES("
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
                aBean.actId = Integer.toString(generatedKeys.getInt(1));
            }
            else {
                throw new SQLException("Creating activity failed, no ID obtained.");
            }
        }
        return aBean;
    }

    public List<ActivityBean> getUserActivities(Connection conn, String userID) throws SQLException{
        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE " + userID_Col + " = ?");
        pstmt.setString(1, userID);
        ResultSet rs = pstmt.executeQuery();
        return getUserActivitiesListFromResultSet(rs);
    }
    
    private List<ActivityBean> getUserActivitiesListFromResultSet(ResultSet rs) throws SQLException {
        List<ActivityBean> resultList = new ArrayList<>();
        ActivityBean currBean = new ActivityBean();
        while(rs.next()) {
            currBean.actId = Integer.toString(rs.getInt(actID_Col));
            currBean.name = rs.getString(name_Col);
            currBean.userId = rs.getString(userID_Col);
            currBean.subCatId = Integer.toString(rs.getInt(subCatID_Col));
            currBean.dateTime = rs.getString(dateTime_Col);
            currBean.country = rs.getString(countryCode_Col);
            currBean.city = rs.getString(cityCode_Col);
            currBean.about = rs.getString(about_Col);
            currBean.photo = rs.getString(photo_Col);
            resultList.add(currBean);
        }
        return resultList;
    }

}

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
    
    public ActivityBean getActivity(Connection conn, String actID) throws SQLException{
        ActivityBean bean = new ActivityBean();
        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE " + actID_Col + " = ?");
        pstmt.setString(1, actID);
        ResultSet rs = pstmt.executeQuery();
        if(rs.next()){
            bean.actId = Integer.toString(rs.getInt(actID_Col));
            bean.name = rs.getString(name_Col);
            bean.userId = rs.getString(userID_Col);
            bean.subCatId = Integer.toString(rs.getInt(subCatID_Col));
            bean.dateTime = rs.getString(dateTime_Col);
            bean.country = rs.getString(countryCode_Col);
            bean.city = rs.getString(cityCode_Col);
            bean.about = rs.getString(about_Col);
            bean.photo = rs.getString(photo_Col);
        }
        else{
            throw new SQLException("Data can not be retrieved");
        }
        return bean;
    }
    
    public void updateActivity(Connection conn, ActivityBean toUpdate) throws SQLException{
        PreparedStatement pstmt = conn.prepareStatement("UPDATE " + tableName +
                " SET " + name_Col + " = ?, " +
                dateTime_Col + " = ?, " +
                countryCode_Col + " = ?, " +
                cityCode_Col + " = ?, " +
                about_Col + " = ?, " +
                "WHERE " + actID_Col + " = ?");
        pstmt.setString(1, toUpdate.name);
        pstmt.setString(2, toUpdate.dateTime);
        pstmt.setString(3, toUpdate.country);
        pstmt.setString(4, toUpdate.city);
        pstmt.setString(5, toUpdate.about);
        pstmt.setString(6, toUpdate.photo);
        pstmt.setString(7, toUpdate.actId);
        pstmt.executeUpdate();
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

    public void deleteActivity(Connection conn, String actId) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement("DELETE * FROM " + tableName + " WHERE " + actId + " = ?");
        pstmt.setString(1, actId);
        pstmt.executeUpdate();
    }

    public List<ActivityBean> getActivities(Connection conn, List<String> activityIDs) throws SQLException {
        StringBuilder pstmtBuilder = new StringBuilder();
        String header = "SELECT * FROM " + tableName + " WHERE " + actID_Col + " IN (";
        int index = 1;
        
        pstmtBuilder.append(header);
        for (int i = 0; i < activityIDs.size(); i++) {
            pstmtBuilder.append("?,");
        }
        pstmtBuilder.deleteCharAt(pstmtBuilder.length() - 1).append(")");
        
        PreparedStatement pstmt = conn.prepareStatement(pstmtBuilder.toString());
        for (String activityID : activityIDs) {
            pstmt.setString(index, activityID);
            index++;
        }
        ResultSet rs = pstmt.executeQuery();
        return getUserActivitiesListFromResultSet(rs);
    }

}

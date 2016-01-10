/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdev.letsmeet.model.mysql.tables;

import appdev.letsmeet.model.mysql.tables.mysqldao.MySQLDAO;
import appdev.letsmeet.control.utils.jsonBeans.ActivityRequestBean;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HA999
 */
public class JoinRequestsTable implements MySQLDAO{

    private final String initfile = "join_requests.txt";
    private final String tableName = "join_requests";
    private final String creatorID_col = "CREATOR_ID";
    private final String actID_col = "ACT_ID";
    private final String requestingUserID_col = "USER_ID";
    private final String accepted_col = "ACCEPTED";
    
    private final String createString =
        "CREATE TABLE IF NOT EXISTS " +
        tableName +
        " (" + creatorID_col + " int NOT NULL, " +
        actID_col + " int NOT NULL, " +
        requestingUserID_col + " int NOT NULL, " +
        accepted_col + " boolean DEFAULT FALSE, " 
            +"FOREIGN KEY ("+ creatorID_col +") REFERENCES users(USER_ID), "
            +"FOREIGN KEY ("+ actID_col +") REFERENCES activities(ACT_ID), "
            +"FOREIGN KEY ("+ requestingUserID_col +") REFERENCES users(USER_ID))";
    
    public JoinRequestsTable(Connection conn, String filePath) {
        copyDataFileToMySQLFileDirectory(conn, filePath, initfile);
        createTable(conn, createString);
        insertFromFile(conn, initfile, tableName);
    }
    

    @Override
    public Serializable insert(Connection conn, Serializable bean) throws SQLException {
        ActivityRequestBean arBean = (ActivityRequestBean) bean;
        PreparedStatement pstmt;
        
        validateInput(arBean);
        pstmt = conn.prepareStatement("INSERT INTO " + tableName
                + creatorID_col + ", "
                + actID_col + ", "
                + requestingUserID_col + ", "
                + accepted_col + ") VALUES(?,?,?,?)");
                pstmt.setString(1, arBean.creatorID);
                pstmt.setString(2,arBean.actID);
                pstmt.setString(3,arBean.requestingUser);
                pstmt.setString(4,arBean.accepted);
        pstmt.executeUpdate();
        return bean;
    }

    public List<ActivityRequestBean> getRequestsByActivityID(Connection conn, String actID) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE " + actID_col + " = ?");
        pstmt.setString(1, actID);
        ResultSet rs = pstmt.executeQuery();
        return getActivityRequestsListFromResultSet(rs);
    }
    
    public List<ActivityRequestBean> getRequestsByRequestingUserID(Connection conn, String requestingUserID) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE " + requestingUserID_col + " = ?");
        pstmt.setString(1, requestingUserID);
        ResultSet rs = pstmt.executeQuery();
        return getActivityRequestsListFromResultSet(rs);
    }
    
    public List<ActivityRequestBean> getRequestsByCreatorID(Connection conn, String creatorID) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE " + creatorID_col + " = ?");
        pstmt.setString(1, creatorID);
        ResultSet rs = pstmt.executeQuery();
        return getActivityRequestsListFromResultSet(rs);
    }
    
    private List<ActivityRequestBean> getActivityRequestsListFromResultSet(ResultSet rs) throws SQLException {
        List<ActivityRequestBean> resultList = new ArrayList<>();
        ActivityRequestBean currBean = new ActivityRequestBean();
        while(rs.next()) {
            currBean.creatorID = rs.getString(creatorID_col);
            currBean.actID = rs.getString(actID_col);
            currBean.requestingUser = rs.getString(requestingUserID_col);
            currBean.accepted = rs.getString(accepted_col);
            resultList.add(currBean);
        }
        return resultList;
    }

    public Boolean updateActivityRequest(Connection conn, ActivityRequestBean reqBean) throws SQLException{
        PreparedStatement pstmt = conn.prepareStatement("UPDATE " + tableName +
                " SET  " + accepted_col + " = ? WHERE " + actID_col + " = ? AND " + requestingUserID_col + " = ?");
        pstmt.setString(1, reqBean.accepted);
        pstmt.setString(2, reqBean.actID);
        pstmt.setString(3, reqBean.requestingUser);
        
        return pstmt.executeUpdate() > 0;
    }

    public void addRequest(Connection conn, ActivityRequestBean bean) throws SQLException {
        insert(conn, bean);
    }

    private void validateInput(ActivityRequestBean arBean) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

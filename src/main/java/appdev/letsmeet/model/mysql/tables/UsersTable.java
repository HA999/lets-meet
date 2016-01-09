/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdev.letsmeet.model.mysql.tables;

import appdev.letsmeet.control.utils.jsonBeans.LoginUserBean;
import appdev.letsmeet.control.utils.jsonBeans.RegistrationBean;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author HA999
 */
public class UsersTable implements MySQLDAO {
    
    private final String tableName = "users";
    private final String userID_col = "USER_ID";
    private final String userName_col = "USERNAME";
    private final String email_col = "EMAIL";
    private final String password_col = "PASSWORD";
    private final String createTime_col = "CREATE_TIME";
    private final String firstName_col = "FIRST_NAME";
    private final String lastName_col = "LAST_NAME";
    private final String country_col = "COUNTRY_CODE";
    private final String city_col = "CITY_CODE";
    private final String gender_col = "GENDER";
    private final String dateOfBirth_col = "DATE_OF_BIRTH";
    private final String phone_col = "PHONE";
    private final String about_col = "ABOUT";
    private final String photo_col = "PHOTO";
    
    private final String createString =
        "CREATE TABLE IF NOT EXISTS " +
        tableName +
        "(" + userID_col + " int NOT NULL AUTO_INCREMENT, " +
        userName_col + " varchar(40) NOT NULL UNIQUE, " +
        email_col + " varchar(255) NOT NULL UNIQUE, " +
        password_col + " varchar(255) NOT NULL, " +
        createTime_col + " timestamp, " +
        firstName_col + " varchar(40) NOT NULL, " +
        lastName_col + " varchar(40) NOT NULL, " +
        country_col + " char(52), " +
        city_col + " char(35), " +
        gender_col + " char(1), " +
        dateOfBirth_col + " date, " +
        phone_col + " varchar(40), " +
        about_col + " longtext, " +
        photo_col + " blob, " 
            + "PRIMARY KEY (" + userID_col + "), "
            +"FOREIGN KEY (" + country_col + ") REFERENCES country(NAME), "
            +"FOREIGN KEY (" + city_col + ") REFERENCES city(NAME), "
            + ")";
    
    private final String indexString = "CREATE UNIQUE INDEX user_index ON "
            + "Users (" + userID_col + ")";
    
    public UsersTable(Connection conn) {
        createTable(conn, createString);
        defineIndexes(conn, indexString);
    }
    
    @Override
    public Serializable insert(Connection conn, Serializable bean) throws SQLException {
        RegistrationBean rBean = (RegistrationBean) bean;
        PreparedStatement pstmt;
        
        validateInput(rBean);
        pstmt = conn.prepareStatement("INSERT INTO " + tableName 
                + "(" + userName_col + ", "
                + email_col + ", "
                + password_col + ", "
                + firstName_col + ", "
                + lastName_col + ", "
                + country_col + ", "
                + city_col + ", "
                + gender_col + ", "
                + dateOfBirth_col + ","
                + phone_col + ","
                + photo_col + ") VALUES("
                + "'" + rBean.username + "', "
                + "'" + rBean.email + "', "
                + "'" + rBean.password + "', "
                + "'" + rBean.firstname + "', "
                + "'" + rBean.lastname + "', "
                + "'" + rBean.country + "', "
                + "'" + rBean.city + "', "
                + "'" + rBean.gender + "', "
                + "'" + rBean.dateofbirth + "', "
                + "'" + rBean.phone + "', "
                + "'" + rBean.photo +"')");

        pstmt.executeUpdate();
        return rBean;
    }
    

    private void validateInput(RegistrationBean bean) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public LoginUserBean authenticateUser(Connection conn, String username, String password) throws SQLException {
        LoginUserBean user = new LoginUserBean();
        PreparedStatement authenticateUserStatement;
        authenticateUserStatement = conn.prepareStatement("SELECT * FROM " + tableName +" WHERE " + userName_col + " = ? AND " + password_col + "= ?");
        authenticateUserStatement.setString(1, username);
        authenticateUserStatement.setString(2, password);
        ResultSet resultSet = authenticateUserStatement.executeQuery();
        if (resultSet.next()) {
            user.password = resultSet.getString(password_col);
            user.username = resultSet.getString(userName_col);
            user.user_Id = Integer.toString(resultSet.getInt(userID_col));
        }
        else {
            user = null;
        }
        return user;
    }

}

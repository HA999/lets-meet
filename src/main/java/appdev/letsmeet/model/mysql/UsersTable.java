/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdev.letsmeet.model.mysql;

import appdev.letsmeet.control.utils.jsonBeans.RegistrationBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;

/**
 *
 * @author HA999
 */
public class UsersTable {
    
    public UsersTable(String dbName, Connection con) throws SQLException {
        createTable(dbName, con);
    }
    
    private void createTable(String dbName, Connection con) throws SQLException {
        String createString =
        "CREATE TABLE IF NOT EXISTS " +
        "users" +
        "(USER_ID int NOT NULL AUTO_INCREMENT, " +
        "USERNAME varchar(40) NOT NULL UNIQUE, " +
        "EMAIL varchar(255) NOT NULL UNIQUE, " +
        "PASSWORD varchar(40) NOT NULL, " +
        "CREATE_TIME timestamp, " +
        "FIRST_NAME varchar(40) NOT NULL, " +
        "LAST_NAME varchar(40) NOT NULL, " +
        "COUNTRY_CODE char(3), " +
        "CITY_CODE char(20), " +
        "GENDER char(1), " +
        "DATE_OF_BIRTH date, " +
        "PHONE varchar(40), " +
        "ABOUT longtext, " +
        "PHOTO blob, " +
        "PRIMARY KEY (USER_ID))";

        PreparedStatement stmt = con.prepareStatement(createString);
        try  {
            stmt.executeUpdate(createString);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public void addUser(Connection conn, RegistrationBean bean) throws SQLException {
        
        PreparedStatement pstmt;
        
        Calendar calendar = Calendar.getInstance();
        java.sql.Date createTime = new java.sql.Date(calendar.getTime().getTime());
        if(validateInput(bean)) {
            try {
                pstmt = conn.prepareStatement("INSERT INTO users "
                        + "(USERNAME, "
                        + "EMAIL, "
                        + "PASSWORD, "
                        + "FIRST_NAME, "
                        + "LAST_NAME, "
                        + "COUNTRY_CODE, "
                        + "CITY_CODE, "
                        + "GENDER, "
                        + "DATE_OF_BIRTH,"
                        + "PHONE) VALUES("
                        + "'" + bean.userName + "', "
                        + "'" + bean.email + "', "
                        + "'" + bean.password + "', "
                        + "'" + bean.firstName + "', "
                        + "'" + bean.lastName + "', "
                        + "'" + bean.country + "', "
                        + "'" + bean.city + "', "
                        + "'" + bean.gender + "', "
                        + "'" + bean.dateOfBirth + "', "
                        + "'" + bean.phone + "')");

                pstmt.executeUpdate();
            }catch (SQLException ex) {
                System.out.println(ex);
            }finally {
                if (conn != null) conn.close();
            }
        }
        else{
            //Throw Exception!!!
        }
    }

    private Boolean validateInput(RegistrationBean bean) {
        return true;
    }
}

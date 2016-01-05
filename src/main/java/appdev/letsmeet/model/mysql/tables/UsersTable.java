/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdev.letsmeet.model.mysql.tables;

import appdev.letsmeet.control.utils.jsonBeans.RegistrationBean;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author HA999
 */
public class UsersTable implements MySQLDAO{
    
    private final String createString =
        "CREATE TABLE IF NOT EXISTS " +
        "Users" +
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
        "PHOTO blob, " 
            + "PRIMARY KEY (USER_ID))";
    
    private final String indexString = "CREATE UNIQUE INDEX user_index ON "
            + "Users (USER_ID)";
    
    public UsersTable(Connection conn) {
        createTable(conn, createString);
        defineIndexes(conn, indexString);
    }
    
    @Override
    public void insert(Connection conn, Serializable bean) {
        RegistrationBean rBean = (RegistrationBean) bean;
        PreparedStatement pstmt;
        
            try {
                validateInput(rBean);
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
                        + "'" + rBean.userName + "', "
                        + "'" + rBean.email + "', "
                        + "'" + rBean.password + "', "
                        + "'" + rBean.firstName + "', "
                        + "'" + rBean.lastName + "', "
                        + "'" + rBean.country + "', "
                        + "'" + rBean.city + "', "
                        + "'" + rBean.gender + "', "
                        + "'" + rBean.dateOfBirth + "', "
                        + "'" + rBean.phone + "')");

                pstmt.executeUpdate();
            }catch (SQLException ex) {
                System.out.println(ex);
            }
        }
    

    private void validateInput(RegistrationBean bean) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

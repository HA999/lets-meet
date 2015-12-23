/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdev.letsmeet.model.mysql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author HA999
 */
public class UsersTable {
    
    public UsersTable(String dbName, Connection con) {
        createTable(dbName, con);
    }
    
    private void createTable(String dbName, Connection con) {
        String createString =
        "create table " + dbName +
        ".USERS " +
        "(USER_ID integer NOT NULL, " +
        "USERNAME varchar(40) NOT NULL, " +
        "EMAIL varchar(255) NOT NULL, " +
        "password varchar(40) NOT NULL, " +
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

        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate(createString);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public void addUser() {
        
    }
}

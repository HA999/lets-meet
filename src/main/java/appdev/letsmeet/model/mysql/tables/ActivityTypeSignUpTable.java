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
public class ActivityTypeSignUpTable extends MySQLTable{

    private final String createString = 
        "CREATE TABLE IF NOT EXISTS " +
        "Activity_Signup" +
        "(CAT_ID int NOT NULL, " +
        "SUB_CAT_ID int NOT NULL, " +
        "USER_ID int NOT NULL, " +
        "COUNTRY_CODE char(3), " +
        "CITY_CODE char(20), "/////KEYS!!!!!!!!!!!
            + ")";
    
    public ActivityTypeSignUpTable(Connection conn) {
        createTable(conn, createString);
        
    }
    
    @Override
    public void insert(Connection conn, Serializable bean) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

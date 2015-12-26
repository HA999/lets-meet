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
public class JoinRequestsTable extends MySQLTable{

    private final String createString =
        "CREATE TABLE IF NOT EXISTS " +
        "JoinRequests" +
        "(CREATOR_ID int NOT NULL, " +
        "ACT_ID INT NOT NULL, " +
        "USER_ID int NOT NULL, " +
        "ACCEPTED boolean DEFAULT FALSE, " 
            +"FOREIGN KEY (CREATOR_ID) REFERENCES Users(USER_ID), "
            +"FOREIGN KEY (ACT_ID) REFERENCES Activities(ACT_ID), "
            +"FOREIGN KEY (USER_ID) REFERENCES Users(USER_ID))";
    
    public JoinRequestsTable(Connection conn) {
        createTable(conn, createString);
    }
    

    @Override
    public void insert(Connection conn, Serializable bean) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

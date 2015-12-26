/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdev.letsmeet.model.mysql.tables;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author HA999
 */
public abstract class MySQLTable {
       
    public void createTable(Connection conn , String statement) {
        try  {
            PreparedStatement stmt = conn.prepareStatement(statement);
            stmt.executeUpdate(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void defineIndexes(Connection conn, String index) {
        try  {
            PreparedStatement stmt = conn.prepareStatement(index);
            stmt.executeUpdate(index);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public abstract void insert(Connection conn, Serializable bean);
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdev.letsmeet.model.mysql;

import appdev.letsmeet.control.utils.jsonBeans.RegistrationBean;
import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author Owner
 */
public class MySQLHandler {
    
    private static MySQLHandler instance;
    private static MysqlConnectionPoolDataSource dataSource = new MysqlConnectionPoolDataSource();
    public static UsersTable usersTable = null;
    
    
    private MySQLHandler() {
        System.out.println("MySQLHandler instance created.");
    };
    
    public static MySQLHandler getInstance() {
        
        if (instance == null) {
            synchronized(MySQLHandler.class) {
                if (instance == null) {
                    instance = new MySQLHandler();
                }
            }
        }
        return instance;
    }
    
    
    public Connection getConnection() {
        Connection con = null;
        try {
            
//            dataSource = (MysqlConnectionPoolDataSource) new InitialContext().lookup(MySQLProperties.dBName);
//            dataSource.setServerName(MySQLProperties.serverName);
//            dataSource.setDatabaseName(MySQLProperties.dBName);
//            dataSource.setUser(MySQLProperties.username);
//            dataSource.setPassword(MySQLProperties.password);
//            dataSource.setPort(MySQLProperties.port);
            Class.forName(MySQLProperties.driver);
            con = DriverManager.getConnection(MySQLProperties.url, MySQLProperties.username, MySQLProperties.password);
            
        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MySQLHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }
    
    
    public void createUsersTable() {
        try {
            usersTable = new UsersTable(MySQLProperties.dBName, getConnection());
        } catch (SQLException ex) {
            Logger.getLogger(MySQLHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void addUser(RegistrationBean rBean) {
        
        try {       
            usersTable.addUser(getConnection(), rBean);
        } catch (SQLException ex) {
            Logger.getLogger(MySQLHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

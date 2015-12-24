/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdev.letsmeet.model.mysql;

import appdev.letsmeet.control.utils.jsonBeans.RegistrationBean;
import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Owner
 */
public class MySQLHandler {
    
//    private static MySQLHandler instance;
    private static final MysqlConnectionPoolDataSource dataSource = new MysqlConnectionPoolDataSource();
    private static final String serverName = "localhost";
    private static final int port = 3306;
    private static final String username = "root";
    private static final String password = "MySQLP1ssw0rde";
    private static final UsersTable usersTable = null;
//    private static final String driver = "com.mysql.jdbc.Driver";
//    private static final String url = "jdbc:mysql://localhost:3306/lets_meet";
    
    
//    private MySQLHandler() {
//        System.out.println("MySQLHandler instance created.");
//    };
//    
//    public static MySQLHandler getInstance() {
//        
//        if (instance == null) {
//            synchronized(MySQLHandler.class) {
//                if (instance == null) {
//                    instance = new MySQLHandler();
//                }
//            }
//        }
//        return instance;
//    }
    
    
    public static Connection getConnection() {
        
        try {
            dataSource.setUser(username);
            dataSource.setPassword(password);
            dataSource.setServerName(serverName);
            dataSource.setPort(port);
            dataSource.setDatabaseName("lets_meet");
            
            return dataSource.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(MySQLHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    public void addUser(RegistrationBean rBean) {
        
        try {       
            usersTable.addUser(getConnection(), rBean);
        } catch (SQLException ex) {
            Logger.getLogger(MySQLHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

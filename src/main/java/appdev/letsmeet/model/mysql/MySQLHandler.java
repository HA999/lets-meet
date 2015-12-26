/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdev.letsmeet.model.mysql;

import appdev.letsmeet.model.mysql.tables.UsersTable;
import appdev.letsmeet.control.utils.jsonBeans.RegistrationBean;
import appdev.letsmeet.model.mysql.tables.ActivityTable;
import appdev.letsmeet.model.mysql.tables.ActivityTypeSignUpTable;
import appdev.letsmeet.model.mysql.tables.CategoryTable;
import appdev.letsmeet.model.mysql.tables.JoinRequestsTable;
import appdev.letsmeet.model.mysql.tables.SubCategoryTable;
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
    
//    private static MysqlConnectionPoolDataSource dataSource = new MysqlConnectionPoolDataSource();
    private static MySQLHandler instance;
    private UsersTable usersTable = null;
    private ActivityTable activityTable = null;
    private JoinRequestsTable joinRequests = null;
    private CategoryTable categoryTable = null;  
    private SubCategoryTable subCategoryTable = null;
    private ActivityTypeSignUpTable activityTypeSignUpTable = null;
    
    private MySQLHandler() {};
    
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
    
    
    public void createTables() {
        Connection conn = getConnection();
        usersTable = new UsersTable(conn);
        categoryTable = new CategoryTable(conn);
        subCategoryTable = new SubCategoryTable(conn);
        activityTable = new ActivityTable(conn);
        joinRequests = new JoinRequestsTable(conn);
        activityTypeSignUpTable = new ActivityTypeSignUpTable(conn);
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(MySQLHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void addUser(RegistrationBean rBean) {
        
        usersTable.insert(getConnection(), rBean);
//        try {       
//        } catch (SQLException ex) {
//            Logger.getLogger(MySQLHandler.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
    
}

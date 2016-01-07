/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdev.letsmeet.model.mysql;

import appdev.letsmeet.control.utils.jsonBeans.ActivityBean;
import appdev.letsmeet.model.mysql.tables.SubCategoryTable;
import appdev.letsmeet.model.mysql.tables.CategoryTable;
import appdev.letsmeet.control.utils.jsonBeans.LoginUserBean;
import appdev.letsmeet.model.mysql.tables.UsersTable;
import appdev.letsmeet.control.utils.jsonBeans.RegistrationBean;
import appdev.letsmeet.control.utils.jsonBeans.SubCategoryBean;
import appdev.letsmeet.model.mysql.tables.ActivityTable;
import appdev.letsmeet.model.mysql.tables.ActivityTypeSignUpTable;
import appdev.letsmeet.model.mysql.tables.JoinRequestsTable;
import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Owner
 */
public class MySQLHandler {
    
    private static MySQLHandler mysqlHandlerInstance;
    private static MysqlConnectionPoolDataSource dataSource = new MysqlConnectionPoolDataSource();//Redundant?!?!?
    private UsersTable usersTable = null;
    private CategoryTable categoryTable = null;
    private SubCategoryTable subCategoryTable = null;
    private ActivityTable activityTable = null;
    private JoinRequestsTable joinRequests = null;
    private ActivityTypeSignUpTable activityTypeSignUpTable = null;
    
    private MySQLHandler() {};
    
    public static MySQLHandler getInstance() {
        
        if (mysqlHandlerInstance == null) {
            synchronized(MySQLHandler.class) {
                if (mysqlHandlerInstance == null) {
                    mysqlHandlerInstance = new MySQLHandler();
                }
            }
        }
        return mysqlHandlerInstance;
    }
    
    
    public Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName(MySQLProperties.driver);
            conn = DriverManager.getConnection(MySQLProperties.url, 
                    MySQLProperties.username, MySQLProperties.password);
        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MySQLHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }
    
    public void closeConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(MySQLHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    public void createTables() {
        Connection conn = getConnection();
        usersTable = new UsersTable(conn);
        categoryTable = new CategoryTable(conn);
        subCategoryTable = new SubCategoryTable(conn);
        activityTable = new ActivityTable(conn);
        joinRequests = new JoinRequestsTable(conn);
        activityTypeSignUpTable = new ActivityTypeSignUpTable(conn);
        closeConnection(conn);
    }
    
    public LoginUserBean addUser(RegistrationBean rBean) {
        Connection conn = getConnection();
        try {
            //Validate rBean!!!!????
            usersTable.insert(conn , rBean);
            return authenticateUser(rBean.username, rBean.password);
        }catch (SQLException ex) {
            System.out.println("AHHHAHAHAHAHAHAHAHAHAH!!!!!!!!!");
            System.out.println(ex);
            return null;
        }finally {
            closeConnection(conn);
        }
    }

    public List<String> getCategoryList() {
        return null;
    }
    
    public LoginUserBean authenticateUser(String username, String password){
        LoginUserBean user = new LoginUserBean();
        PreparedStatement authenticateUserStatement;
        Connection conn = getConnection();
        
        try {
            authenticateUserStatement = conn.prepareStatement("SELECT * FROM Users WHERE USERNAME = ? AND PASSWORD = ?");
            authenticateUserStatement.setString(1, username);
            authenticateUserStatement.setString(2, password);
            ResultSet resultSet = authenticateUserStatement.executeQuery();
            if (resultSet.next()) {
                user.password = resultSet.getString("PASSWORD");
                user.username = resultSet.getString("USERNAME");
                user.user_Id = Integer.toString(resultSet.getInt("USER_ID"));
            }
            else {
                user = null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MySQLHandler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection(conn);
        }
        
        return user;
    }
    
    
//    public Connection getConnection() {
//        Connection con = null;
//        try {
//            dataSource = (MysqlConnectionPoolDataSource) new InitialContext().lookup(MySQLProperties.dBName);
//            dataSource.setServerName(MySQLProperties.serverName);
//            dataSource.setDatabaseName(MySQLProperties.dBName);
//            dataSource.setUser(MySQLProperties.username);
//            dataSource.setPassword(MySQLProperties.password);
//            dataSource.setPort(MySQLProperties.port);
//            
//            con = dataSource.getConnection();
//        } catch (SQLException ex) {
//            System.out.println(ex);
//        }
//        return con;
//    }

    public List<ActivityBean> getUserActivities(LoginUserBean user) {
        String userID = user.user_Id;
        Connection conn = getConnection();
        try {
            return activityTable.getUserActivities(conn, userID);
        } catch (SQLException ex) {
            Logger.getLogger(MySQLHandler.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            closeConnection(conn);
        }
    }
    
    public ActivityBean addActivity(ActivityBean bean) {
        Connection conn = getConnection();
        try{
            return (ActivityBean) activityTable.insert(conn, bean);
        }catch (SQLException ex){
            Logger.getLogger(MySQLHandler.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            closeConnection(conn);
        }
    }

    public SubCategoryBean getSubCategoryData(String subCatId) {
        Connection conn = getConnection();
        try{
            return subCategoryTable.getSubCategoryData(conn, subCatId);
        }catch (SQLException ex){
            Logger.getLogger(MySQLHandler.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            closeConnection(conn);
        }
    }
}

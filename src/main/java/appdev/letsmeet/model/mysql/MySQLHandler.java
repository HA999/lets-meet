/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdev.letsmeet.model.mysql;

import appdev.letsmeet.control.utils.jsonBeans.ActivityBean;
import appdev.letsmeet.control.utils.jsonBeans.ActivityRequestBean;
import appdev.letsmeet.model.mysql.tables.categories.SubCategoryTable;
import appdev.letsmeet.model.mysql.tables.categories.CategoryTable;
import appdev.letsmeet.control.utils.jsonBeans.LoginUserBean;
import appdev.letsmeet.model.mysql.tables.UsersTable;
import appdev.letsmeet.control.utils.jsonBeans.RegistrationBean;
import appdev.letsmeet.control.utils.jsonBeans.SubCategoryBean;
import appdev.letsmeet.model.mysql.tables.ActivityTable;
import appdev.letsmeet.model.mysql.tables.JoinRequestsTable;
import appdev.letsmeet.model.mysql.tables.location.CityTable;
import appdev.letsmeet.model.mysql.tables.location.CountryTable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author HANAN&OLYA
 */
public class MySQLHandler {
    
    private static MySQLHandler mysqlHandlerInstance;
    private UsersTable usersTable = null;
    private CategoryTable categoryTable = null;
    private SubCategoryTable subCategoryTable = null;
    private ActivityTable activityTable = null;
    private JoinRequestsTable joinRequests = null;
    private CountryTable countryTable = null;
    private CityTable cityTable = null;
    
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
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex);
        }
        return conn;
    }
    
    public void closeConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    
    
    public void createTables(String filePath) {
        Connection conn = getConnection();
        countryTable = new CountryTable(conn, filePath);
        cityTable = new CityTable(conn, filePath);
        usersTable = new UsersTable(conn, filePath);
        categoryTable = new CategoryTable(conn, filePath);
        subCategoryTable = new SubCategoryTable(conn, filePath);
        activityTable = new ActivityTable(conn, filePath);
        joinRequests = new JoinRequestsTable(conn, filePath);
        closeConnection(conn);
    }
    
    public LoginUserBean addUser(RegistrationBean rBean) {
        Connection conn = getConnection();
        try {
            usersTable.insert(conn , rBean);
            return authenticateUser(rBean.username, rBean.password);
        }catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }

    public List<String> getCategoryList() {
        Connection conn = getConnection();
        try {
            return categoryTable.getCategoryList(conn);
        } catch (SQLException ex) {
            return null;
        } finally {
            closeConnection(conn);
        }
    }
    
    public LoginUserBean authenticateUser(String username, String password){
        Connection conn = getConnection();
        try {
            return usersTable.authenticateUser(conn, username, password);
            
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        } finally {
            closeConnection(conn);
        }
    }
    
    public List<ActivityBean> getUserActivities(LoginUserBean user) {
        String userID = user.user_Id;
        Connection conn = getConnection();
        try {
            return activityTable.getUserActivities(conn, userID);
        } catch (SQLException ex) {
            System.out.println(ex);
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
            System.out.println(ex);
            return null;
        } finally {
            closeConnection(conn);
        }
    }
    
       public Boolean updateActivity(ActivityBean bean) {
           Connection conn = getConnection();
            try{
                activityTable.updateActivity(conn, bean);
                return true;
            }catch (SQLException ex){
                System.out.println(ex);
                return false;
            } finally {
                closeConnection(conn);
            }
       }

    public ActivityBean getActivityData(String actId) {
        Connection conn = getConnection();
        try{
            return (ActivityBean) activityTable.getActivity(conn, actId);
        }catch (SQLException ex){
            System.out.println(ex);
            return null;
        } finally {
            closeConnection(conn);
        }
    }
    
    public Boolean deleteActivity(String actId) {
        Connection conn = getConnection();
        try{
            joinRequests.deleteRequests(conn, actId);
            activityTable.deleteActivity(conn, actId);
            return true;
        }catch (SQLException ex){
            System.out.println(ex);
            return false;
        } finally {
            closeConnection(conn);
        }
    }

    public SubCategoryBean getSubCategoryData(String subCatId) {
        Connection conn = getConnection();
        try{
            return subCategoryTable.getSubCategoryData(conn, subCatId);
        }catch (SQLException ex){
            System.out.println(ex);
            return null;
        } finally {
            closeConnection(conn);
        }
    }

    public List<ActivityRequestBean> getRequestsByActivityID(String actID) {
        Connection conn = getConnection();
        try {
            return joinRequests.getRequestsByActivityID(conn, actID);
        } catch (SQLException ex){
            System.out.println(ex);
            return null;
        } finally {
            closeConnection(conn);
        }
    }

    public Boolean updateActivityRequest(ActivityRequestBean reqBean) {
        Connection conn = getConnection();
        try {
            return joinRequests.updateActivityRequest(conn, reqBean);
        } catch (SQLException ex){
            System.out.println(ex);
            return null;
        } finally {
            closeConnection(conn);
        }
    }

    public List<ActivityBean> getActivities(List<String> activityIDs) {
        Connection conn = getConnection();
        try {
            return activityTable.getActivities(conn, activityIDs);
        } catch (SQLException ex){
            System.out.println(ex);
            return null;
        }
    }
        
    public List<String> getCountryList() {
        Connection conn = getConnection();
        try {
            return countryTable.getCountryNames(conn);
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        } finally {
            closeConnection(conn);
        }
    }

    public List<String> getCitiesInCountyList(String countryName) {
        Connection conn = getConnection();
        try {
            String countryCode = countryTable.convertCountryNameToCode(conn, countryName);
            return cityTable.getCitiesInCountryList(conn, countryCode);
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        } finally {
            closeConnection(conn);
        }
    }

    public List<String> getSubCategoriesInCategoryList(String category) {
        Connection conn = getConnection();
        try {
            return subCategoryTable.getSubCategoriesInCategoryList(conn, category);
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        } finally {
            closeConnection(conn);
        }
    }

    public void addActivityRequest(ActivityRequestBean bean) {
        Connection conn = getConnection();
        try {
            joinRequests.addRequest(conn, bean);
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            closeConnection(conn);
        }
    }

    public List<ActivityBean> getActivities() {
        Connection conn = getConnection();
        try {
            return activityTable.getActivities(conn);
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        } finally {
            closeConnection(conn);
        }
    }

    public List<SubCategoryBean> getSubCategories() {
        Connection conn = getConnection();
        try {
            return subCategoryTable.getSubCategories(conn);
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        } finally {
            closeConnection(conn);
        }
    }

    public Boolean isUniqueUsername(String username) {
        Connection conn = getConnection();
        try {
            return usersTable.isUniqueUsername(conn, username);
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        } finally {
            closeConnection(conn);
        }
    }

    public String convertSubcategoryIDToSubcategoryName(String subcategory) {
        Connection conn = getConnection();
        try {
            return subCategoryTable.convertSubcategoryIDToSubcategoryName(conn, subcategory);
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        } finally {
            closeConnection(conn);
        }
    }
}

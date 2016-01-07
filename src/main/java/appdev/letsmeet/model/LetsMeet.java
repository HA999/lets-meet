/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdev.letsmeet.model;

import appdev.letsmeet.control.utils.jsonBeans.ActivityBean;
import appdev.letsmeet.control.utils.jsonBeans.LoginUserBean;
import appdev.letsmeet.control.utils.jsonBeans.RegistrationBean;
import appdev.letsmeet.model.mysql.MySQLHandler;
import appdev.letsmeet.model.redis.RedisHandler;
import java.util.List;

/**
 *
 * @author HA999
 */
public class LetsMeet {
    
    private static LetsMeet instance;
    private final MySQLHandler mysqlHandler = MySQLHandler.getInstance();
    private final RedisHandler redisHandler = RedisHandler.getInstance();
    
    private LetsMeet () {};
    
    public static LetsMeet getInstance() {
        
        if (instance == null) {
            synchronized(LetsMeet.class) {
                if (instance == null) {
                    instance = new LetsMeet();
                }
            }
        }
        return instance;
    }
    
    public LoginUserBean addUser(RegistrationBean rbean) {
        return mysqlHandler.addUser(rbean);
    }

    public String getUserName(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<String> getCategoryList() {
//        return mysqlHandler.getCategoryList();
        return redisHandler.getCategoryList();
    }
    
    public LoginUserBean authenticateUser(String username, String password){
        return mysqlHandler.authenticateUser(username, password);
    }
//    public List<String> getCategoryList() {
////        return mysqlHandler.getCategoryList();
//        return redisHandler.getCategoryList();
//    }

    public void addLoggedInUser(LoginUserBean user) {
        redisHandler.addLoggedInUser(user.user_Id);
    }
    
    public Boolean isLoggedInUser(LoginUserBean user) {
        return redisHandler.isLoggedInUser(user.user_Id);
    }
    
    public void removeLoggedInUser(LoginUserBean user) {
        redisHandler.removeLoggedInUser(user.user_Id);
    }

    public ActivityBean addNewActivity(ActivityBean bean) {
        ActivityBean updatedBean = mysqlHandler.addActivity(bean);
        
        if(updatedBean != null){
            if(redisHandler.addActivity(updatedBean)){
                return updatedBean;
            }
        }
        return null;
    }
}

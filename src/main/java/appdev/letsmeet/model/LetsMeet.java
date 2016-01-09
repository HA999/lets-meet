/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdev.letsmeet.model;

import appdev.letsmeet.control.utils.jsonBeans.ActivityBean;
import appdev.letsmeet.control.utils.jsonBeans.ActivityRequestBean;
import appdev.letsmeet.control.utils.jsonBeans.LoginUserBean;
import appdev.letsmeet.control.utils.jsonBeans.RegistrationBean;
import appdev.letsmeet.control.utils.jsonBeans.SubCategoryBean;
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

    public List<ActivityBean> getUserActivities(LoginUserBean user) {
        return mysqlHandler.getUserActivities(user);
    }
    
    public ActivityBean addNewActivity(ActivityBean bean) {
        ActivityBean updatedBean = mysqlHandler.addActivity(bean);
        SubCategoryBean subCategory = mysqlHandler.getSubCategoryData(bean.subCatId);
        if(updatedBean != null){
            redisHandler.addActivity(subCategory, updatedBean.city, updatedBean.actId);
            return updatedBean;
        }
        return null;
    }
    
    public Boolean updateActivity(ActivityBean bean) {
        ActivityBean oldActivityBean= mysqlHandler.getActivityData(bean.actId);
        Boolean isUpdated = mysqlHandler.updateActivity(bean);
        
        if(isUpdated && oldActivityBean != null){
            redisHandler.updateActivity(bean.city, oldActivityBean.city, bean.actId);
            return true;
        }
        return false;
    }
    
    public Boolean deleteActivity(String actId) {
        Boolean isDeleted = mysqlHandler.deleteActivity(actId);
        ActivityBean bean = mysqlHandler.getActivityData(actId);
        SubCategoryBean subCategory = mysqlHandler.getSubCategoryData(bean.subCatId);
        
        if(isDeleted && bean != null && subCategory != null){
            redisHandler.deleteActivity(subCategory, bean.city, actId);
            return true;
        }
        return false;
    }

    public List<ActivityRequestBean> getRequestsByActivityID(String actID) {
        return mysqlHandler.getRequestsByActivityID(actID);
    }
    
    public Boolean updateActivityRequest(ActivityRequestBean reqBean) {
        if (reqBean != null) {
            return mysqlHandler.updateActivityRequest(reqBean);
        }
        else {
            return false;
        }
    }

    public List<ActivityBean> searchActivities(String category, String subcategory, String city) {
        List<String> activityIDs = redisHandler.searchActivities(category, subcategory, city);
        return mysqlHandler.getActivities(activityIDs);
    }

    public List<ActivityBean> getRecentAddedActivities() {
        return mysqlHandler.getActivities(redisHandler.getTopActivities());
    }
}

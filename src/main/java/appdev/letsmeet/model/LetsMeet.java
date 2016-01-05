/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdev.letsmeet.model;

import appdev.letsmeet.control.utils.jsonBeans.RegistrationBean;
import appdev.letsmeet.model.mysql.MySQLHandler;
import appdev.letsmeet.model.redis.RedisHandler;
import java.util.List;

/**
 *
 * @author HA999
 */
public class LetsMeet {
    
    private final MySQLHandler mysqlHandler = MySQLHandler.getInstance();
    private final RedisHandler redisHandler = RedisHandler.getInstance();
    
    public void addUser(RegistrationBean rbean) {
        mysqlHandler.addUser(rbean);
    }

    public String getUserName(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

//    public List<String> getCategoryList() {
////        return mysqlHandler.getCategoryList();
//        return redisHandler.getCategoryList();
//    }
}

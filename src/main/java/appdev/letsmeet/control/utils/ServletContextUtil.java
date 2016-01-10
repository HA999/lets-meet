/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdev.letsmeet.control.utils;

import appdev.letsmeet.model.LetsMeet;
import appdev.letsmeet.model.mysql.MySQLHandler;
import appdev.letsmeet.model.redis.RedisHandler;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 *
 * @author HANAN&OLYA
 */
@WebListener
public class ServletContextUtil implements ServletContextListener{

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LetsMeet model = LetsMeet.getInstance();
        MySQLHandler mysql = MySQLHandler.getInstance();
        RedisHandler redis = RedisHandler.getInstance();
        mysql.createTables(sce.getServletContext().getRealPath("/WEB-INF/classes/"));
        model.initActivitiesInRedis();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        
    }
    
}

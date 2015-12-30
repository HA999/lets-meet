/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdev.letsmeet.control.utils;

import appdev.letsmeet.model.mysql.MySQLHandler;
import appdev.letsmeet.model.redis.RedisHandler;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 *
 * @author HA999
 */
@WebListener
public class ServletContextUtil implements ServletContextListener{

    private final MySQLHandler mysql = MySQLHandler.getInstance();
    private final RedisHandler redis = RedisHandler.getInstance();
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        mysql.createTables(sce.getServletContext().getRealPath("/"));
        redis.createCategoryList(mysql.getCategoryList());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdev.letsmeet.control.utils;

import appdev.letsmeet.model.mysql.MySQLHandler;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 *
 * @author HA999
 */
@WebListener
public class ServletContextUtil implements ServletContextListener{

    @Override
    public void contextInitialized(ServletContextEvent sce) {
       MySQLHandler.getInstance().createUsersTable();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        
    }
    
}
